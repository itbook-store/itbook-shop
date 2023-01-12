package shop.itbook.itbookshop.deliverygroup.delivery.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DummyServerDeliveryRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.DeliveryService;

/**
 * DeliveryService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    @Qualifier("DefaultRestTemplate")
    private final RestTemplate restTemplate;
//    @Value("itbook-server-url.deliveryUrl")
//    private final String deliveryUrl;

    @Override
    public DeliveryResponseDto addDelivery(DummyServerDeliveryRequestDto deliveryRequestDto)
        throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DummyServerDeliveryRequestDto> http =
            new HttpEntity<>(deliveryRequestDto, headers);

        ResponseEntity<DeliveryResponseDto> responseEntity =
            restTemplate.exchange("http://localhost:8083/api/deliveries", HttpMethod.POST, http,
                DeliveryResponseDto.class);

        return responseEntity.getBody();
    }
}
