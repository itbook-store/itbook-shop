package shop.itbook.itbookshop.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.file.dto.ItBookObjectStorageToken;
import shop.itbook.itbookshop.file.exception.InvalidTokenException;
import shop.itbook.itbookshop.file.exception.ObjectStroageFileUploadException;


/**
 * 지정한 컨테이너에 오브젝트를 업로드하는 서비스 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class FileService {
    @Value("${object.storage.storage-url}")
    private String storageUrl;
    @Value("${object.storage.container-name}")
    private String containerName;

    private final TokenService tokenService;

    private String getUrl(@NonNull String containerName, @NonNull String folderPath,
                          @NonNull String objectName) {
        return this.storageUrl + "/" + containerName + "/" + folderPath + "/" + objectName;
    }

    /**
     * 파일 업로드 기능을 담당하는 메서드입니다.
     *
     * @param multipartFile 업로드할 파일입니다.
     * @param folderPath    업로드할 폴더 경로입니다.
     * @return 업로드 된 파일의 url입니다.
     * @author 이하늬
     */
    public String uploadFile(MultipartFile multipartFile, String folderPath) {
        ItBookObjectStorageToken.Access.Token token;

        try {
            token = tokenService.getToken();
        } catch (InvalidTokenException e) {
            token = tokenService.requestToken();
        }
        String tokenId = token.getId();

        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String savedName = UUID.randomUUID() + "." + ext;

        try {
            InputStream inputStream = multipartFile.getInputStream();
            return this.uploadObject(tokenId, containerName, folderPath, savedName, inputStream);
        } catch (HttpClientErrorException | IOException e) {
            throw new ObjectStroageFileUploadException();
        }

    }

    /**
     * 파일 삭제 기능을 담당하는 메서드입니다.
     *
     * @param url 삭제할 파일이 오브젝트 스토리지에 저장된 url입니다.
     * @author 이하늬
     */
    public void deleteFile(String url) {

        ItBookObjectStorageToken.Access.Token token = tokenService.requestToken();
        String tokenId = token.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", tokenId);
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, requestHttpEntity, String.class);
        } catch (HttpClientErrorException e) {
            throw new ObjectStroageFileUploadException();
        }
    }


    /**
     * rest template으로 오브젝트 업로드 기능을 담당하는 메서드입니다.
     *
     * @param containerName 업로드 할 컨테이너 이름입니다.
     * @param folderPath    업로드 할 폴더 경로입니다.
     * @param objectName    업로드 할 오브젝트 이름입니다.
     * @param inputStream   input stream
     * @return 업로드 된 파일의 url입니다.
     * @author 이하늬
     */
    private String uploadObject(String tokenId, String containerName, String folderPath,
                                String objectName, final InputStream inputStream) {
        String url = this.getUrl(containerName, folderPath, objectName);

        final RequestCallback requestCallback = request -> {
            request.getHeaders().add("X-Auth-Token", tokenId);
            IOUtils.copy(inputStream, request.getBody());
        };

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
            = new HttpMessageConverterExtractor<>(String.class,
            restTemplate.getMessageConverters());

        try {
            restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
        } catch (HttpClientErrorException e) {
            throw new ObjectStroageFileUploadException();
        }

        return url;
    }


}
