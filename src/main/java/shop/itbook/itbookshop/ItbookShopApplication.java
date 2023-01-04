package shop.itbook.itbookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import shop.itbook.itbookshop.config.RedisConfig;

/**
 *
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class ItbookShopApplication {

    public static void main(String[] args) {
        System.out.println("젠킨스 테스트");
        System.out.println("젠킨스 연동 테스트");
        SpringApplication.run(ItbookShopApplication.class, args);
    }
}
