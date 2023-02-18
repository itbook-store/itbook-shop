package shop.itbook.itbookshop.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 송다혜
 * @since 1.0
 */
//@Configuration
//public class ElasticSearch {
////    spring.elasticsearch.uris
//
//
//    @Bean
//    public ElasticsearchClient client() {
//        RestClient httpClient = RestClient.builder(
//            new HttpHost("http://133.186.210.108:9200")
//        ).build();
//        RestHighLevelClient hlrc = new RestHighLevelClientBuilder(httpClient)
//            .setApiCompatibilityMode(true)
//            .build();
//        ElasticsearchTransport transport = new RestClientTransport(
//            httpClient, new JacksonJsonpMapper());
//
//        return new ElasticsearchClient(transport);
//    }
//}
