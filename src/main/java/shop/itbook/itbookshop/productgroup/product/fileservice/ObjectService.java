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
 * @author 이하늬
 * @since 1.0
 */
@Data
public class ObjectService {
    private String tokenId;
    private String storageUrl;

    private RestTemplate restTemplate;

    public ObjectService(String storageUrl, String tokenId) {
        this.setStorageUrl(storageUrl);
        this.setTokenId(tokenId);

        this.restTemplate = new RestTemplate();
    }

    private String getUrl(@NonNull String containerName, @NonNull String folderPath,
                          @NonNull String objectName) {
        return this.storageUrl + "/" + containerName + "/" + folderPath + "/" + objectName;
    }

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
            = new HttpMessageConverterExtractor<String>(String.class,
            restTemplate.getMessageConverters());

        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        return url;
    }

    public InputStream downloadObject(String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", tokenId);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<byte[]> response
            = this.restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);

        return new ByteArrayInputStream(response.getBody());
    }

}
