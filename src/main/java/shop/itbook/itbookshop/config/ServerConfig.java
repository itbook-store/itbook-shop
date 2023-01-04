package shop.itbook.itbookshop.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @since 1.0
 */
@Setter
@Configuration
@ConfigurationProperties(prefix = "itbook-server-url")
public class ServerConfig {
    private String batchUrl;
    private String deliveryUrl;

    public String getBatchUrl() {
        return batchUrl;
    }

    public String getDeliveryUrl() {
        return deliveryUrl;
    }
}
