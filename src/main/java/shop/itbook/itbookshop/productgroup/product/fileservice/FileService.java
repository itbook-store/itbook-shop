package shop.itbook.itbookshop.productgroup.product.fileservice;

import java.io.InputStream;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.TokenInterceptor;


/**
 * 지정한 컨테이너에 오브젝트를 업로드하는 서비스 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class FileService {
    @Value("${object.storage.storage-url}")
    private String storageUrl;
    @Value("${object.storage.container-name}")
    private String containerName;

    private final TokenInterceptor tokenInterceptor;

    /**
     * 파일 업로드 기능을 담당하는 메서드입니다.
     *
     * @param multipartFile 업로드할 파일입니다.
     * @param folderPath    업로드할 폴더 경로입니다.
     * @return 업로드 된 파일의 url입니다.
     * @author 이하늬
     */
    public String uploadFile(MultipartFile multipartFile,
                             String folderPath) {
        String tokenId = tokenInterceptor.getTokenFields("tokenId");
        ObjectService objectService = new ObjectService(storageUrl, tokenId);
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String savedName = UUID.randomUUID() + "." + ext;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            return objectService.uploadObject(containerName, folderPath, savedName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile.getOriginalFilename();
    }

}
