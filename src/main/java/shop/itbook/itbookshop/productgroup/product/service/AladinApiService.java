package shop.itbook.itbookshop.productgroup.product.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookshop.productgroup.product.dto.response.BookResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.Item;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Service
public class AladinApiService {

    private final String TTBKEY = "ttb1096222126001";
    private final RestTemplate restTemplate = new RestTemplate();

    public Item getBookDetails(String isbn) {
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
        headers.add("Accept", "application/json");
        headers.add("Content-Length", "5000");

        ResponseEntity<BookResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET,
            new HttpEntity<>(headers), BookResponse.class);

        BookResponse bookResponse = response.getBody();

        if (bookResponse.getItem().size() == 0) {
            return null;
        }

        return bookResponse.getItem().get(0);
    }
}
