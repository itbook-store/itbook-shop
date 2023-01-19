package shop.itbook.itbookshop.productgroup.product.fileservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.service.adminapi.impl.BookAdminServiceImpl;


/**
 * @author 이하늬
 * @since 1.0
 */
@Slf4j
@Component
public class FileService {
    @Value("${object.storage.storage-url}")
    private String storageUrl;
    @Value("${object.storage.token-id}")
    private String tokenId;
    @Value("${object.storage.container-name}")
    private String containerName;


    public String uploadFile(MultipartFile multipartFile,
                             String folderPath) {
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
