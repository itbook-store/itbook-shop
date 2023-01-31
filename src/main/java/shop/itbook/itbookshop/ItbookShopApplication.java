package shop.itbook.itbookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import shop.itbook.itbookshop.config.RedisConfig;
//import shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository.ProductSearchRepository;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ItbookShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItbookShopApplication.class, args);
    }
}
