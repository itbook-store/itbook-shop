package shop.itbook.itbookshop.productgroup.product.service;

import java.util.Optional;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.productgroup.product.dto.response.BookResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.Item;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Service
public class AladinApiService {

    private static final String TTBKEY = "ttb1096222126001";
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

        BookResponse bookResponse = Optional.ofNullable(response.getBody())
            .orElseThrow(BookNotFoundException::new);

        if (bookResponse.getItem().isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookResponse.getItem().get(0);
    }
}
