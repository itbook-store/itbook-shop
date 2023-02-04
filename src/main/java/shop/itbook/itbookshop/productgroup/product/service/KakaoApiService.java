package shop.itbook.itbookshop.productgroup.product.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Service
public class KakaoApiService {

    @Data
    public static class BookResponse {

        private List<Item> item = new ArrayList<>();

        @Data
        public static class Item {
            private String title;
            private String author;
            private String pubDate;
            private String description;
            private Long priceStandard;
            private String publisher;
            private BookInfo subInfo;
        }

        @Data
        public static class BookInfo {
            private String itemPage;
        }
    }

    private final String TTBKEY = "ttb1096222126001";
    private final RestTemplate restTemplate = new RestTemplate();

    public BookResponse getBookDetails(String isbn) {
        String requestUrl = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemSearch.aspx")
            .queryParam("ttbkey", TTBKEY)
            .queryParam("Query", isbn)
            .queryParam("Output", "js")
            .queryParam("OptResult", "fileFormatList")
            .queryParam("version", "20131101")
            .build(true).toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        ResponseEntity<BookResponse> response
            = restTemplate.exchange(requestUrl, HttpMethod.GET, new HttpEntity<>(headers),
            BookResponse.class);

        return response.getBody();
    }
}
