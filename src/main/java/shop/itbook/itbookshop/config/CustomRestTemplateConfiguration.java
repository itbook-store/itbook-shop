package shop.itbook.itbookshop.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * HTTPS에 대해 RestTemplate으로 통신하기 위한 Configuration 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "itbook.cert")
public class CustomRestTemplateConfiguration {

    private String file;
    private String password;

    /**
     * NHN Secure Key Manager에 대해 RestTemplate으로 HTTPS에 대해 통신하기 위한 설정입니다.
     *
     * @return the rest template
     * @throws CertificateException      the certificate exception
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws KeyStoreException         the key store exception
     * @throws IOException               the io exception
     * @throws KeyManagementException    the key management exception
     * @throws UnrecoverableKeyException the unrecoverable key exception
     * @author 강명관 *
     */
    @Bean
    public RestTemplate restTemplate()
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException,
        KeyManagementException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        ClassPathResource resource = new ClassPathResource(file);
        keyStore.load(new FileInputStream(resource.getFile()), password.toCharArray());

        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        sslContextBuilder.setProtocol("TLS");
        sslContextBuilder.loadKeyMaterial(keyStore, password.toCharArray());
        sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

        SSLConnectionSocketFactory sslConnectionSocketFactory =
            new SSLConnectionSocketFactory(sslContextBuilder.build());

        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(sslConnectionSocketFactory)
            .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }
}
