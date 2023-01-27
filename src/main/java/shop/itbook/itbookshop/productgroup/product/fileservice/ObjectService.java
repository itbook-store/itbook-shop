package shop.itbook.itbookshop.productgroup.product.fileservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import lombok.Data;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

/**
 * 오브젝트 서비스 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Data
public class ObjectService {
    private String tokenId;
    private String storageUrl;

    private RestTemplate restTemplate;

    /**
     * 오브젝트 서비스 생성자입니다.
     *
     * @param storageUrl 스토리지 url입니다.
     * @param tokenId    토큰 Id입니다.
     * @author 이하늬
     */
    public ObjectService(String storageUrl, String tokenId) {
        this.setStorageUrl(storageUrl);
        this.setTokenId(tokenId);

        this.restTemplate = new RestTemplate();
    }

    private String getUrl(@NonNull String containerName, @NonNull String folderPath,
                          @NonNull String objectName) {
        return this.storageUrl + "/" + containerName + "/" + folderPath + "/" + objectName;
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
    public String uploadObject(String containerName, String folderPath, String objectName,
                               final InputStream inputStream) {
        String url = this.getUrl(containerName, folderPath, objectName);

        final RequestCallback requestCallback = new RequestCallback() {
            public void doWithRequest(final ClientHttpRequest request) throws IOException {
                request.getHeaders().add("X-Auth-Token", tokenId);
                IOUtils.copy(inputStream, request.getBody());
            }
        };

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
            = new HttpMessageConverterExtractor<>(String.class,
            restTemplate.getMessageConverters());

        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        return url;
    }

}
