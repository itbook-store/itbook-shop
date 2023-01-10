package shop.itbook.itbookshop.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import shop.itbook.itbookshop.config.dto.response.SecureKeyResponseDto;

/**
 * DB를 Secure Key Manager로 보호하기 위한 Configuration 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "itbook")
public class DataSourceConfig {

    private static final String BASE_URL =
        "https://api-keymanager.nhncloudservice.com/keymanager/v1.0/appkey/";

    private String appKey;
    private String passwordSecrets;
    private String urlSecrets;

    private final CustomRestTemplateConfiguration customRestTemplate;


    /**
     * Apache Common DBCP2 를 연결하기 위한 설정입니다.
     *
     * @return the data source
     * @throws UnrecoverableKeyException the unrecoverable key exception
     * @throws CertificateException      the certificate exception
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws KeyStoreException         the key store exception
     * @throws IOException               the io exception
     * @throws KeyManagementException    the key management exception
     * @author 강명관 *
     */
    @Bean
    public DataSource dataSource()
        throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
        KeyStoreException, IOException, KeyManagementException {

        String url = getDataBySecureKeyManager(urlSecrets);
        String password = getDataBySecureKeyManager(passwordSecrets);

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername("itbook");
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setInitialSize(2);
        basicDataSource.setMaxTotal(10);

        return basicDataSource;
    }


    /**
     * restTemplate을 통해 Secure Key Manager와 통신하기 위한 메서드입니다.
     *
     * @param secretsKey
     * @return
     * @throws UnrecoverableKeyException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws KeyManagementException
     * @author 강명관
     */
    private String getDataBySecureKeyManager(String secretsKey)
        throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
        KeyStoreException, IOException, KeyManagementException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<SecureKeyResponseDto> exchange = customRestTemplate.restTemplate().exchange(
            BASE_URL + appKey + "/secrets/" + secretsKey,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            SecureKeyResponseDto.class
        );

        return Objects.requireNonNull(exchange.getBody()).getBody().getSecret();
    }

}
