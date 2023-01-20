package shop.itbook.itbookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ItbookShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItbookShopApplication.class, args);
    }
}
