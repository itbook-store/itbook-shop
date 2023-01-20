package shop.itbook.itbookshop.productgroup.product.fileservice;

import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.ItBookObjectStorageToken;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.TokenInterceptor;


/**
 * @author 이하늬
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileService {
    @Value("${object.storage.storage-url}")
    private String storageUrl;
    @Value("${object.storage.container-name}")
    private String containerName;

    private final TokenInterceptor tokenInterceptor;


    public String uploadFile(MultipartFile multipartFile,
                             String folderPath) {
        String tokenId = tokenInterceptor.getTokenFields("tokenId");
        ObjectService objectService = new ObjectService(storageUrl, tokenId);
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String savedName = UUID.randomUUID() + "." + ext;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String fileStorageUrl =
                objectService.uploadObject(containerName, folderPath, savedName, inputStream);
            return fileStorageUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile.getOriginalFilename();
    }

}
