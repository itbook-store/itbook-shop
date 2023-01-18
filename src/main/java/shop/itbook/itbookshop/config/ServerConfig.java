package shop.itbook.itbookshop.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * properties에서 rest api 서버들의 주소를 받아오기 위한 설정파일입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Setter
@Configuration
@PropertySource("application-prod.properties")
@ConfigurationProperties(prefix = "itbook-server-url")
public class ServerConfig {

    private String batchUrl;
    private String deliveryUrl;
    private String deliveryPostPath;

    public String getBatchUrl() {
        return batchUrl;
    }

    public String getDeliveryUrl() {
        return deliveryUrl;
    }

    public String getDeliveryPostPath() {
        return deliveryPostPath;
    }
}
