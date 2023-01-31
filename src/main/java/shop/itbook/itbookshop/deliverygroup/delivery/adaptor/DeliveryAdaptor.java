package shop.itbook.itbookshop.deliverygroup.delivery.adaptor;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;

/**
 * 배송 더미 서버와 Rest template 을 이용한 통신을 하기 위한 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class DeliveryAdaptor<T extends DeliveryDetailResponseDto> {

    @Qualifier("DefaultRestTemplate")
    private final RestTemplate restTemplate;

    /**
     * rest template 을 이용해 배송 정보 리스트를 배송 서버에 요청보내고 받아옵니다.
     *
     * @param uri  배송 서버 Url.
     * @param http 요청할 정보가 담긴 http entity.
     * @return 배송 더미 서버로부터 받은 배송 등록 정보.
     * @author 정재원
     */
    public List<T> postDeliveryList(
        URI uri, HttpEntity<List<DeliveryServerRequestDto>> http) {

        ResponseEntity<CommonResponseBody<List<T>>> exchange =
            restTemplate.exchange(uri, HttpMethod.POST, http,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }
}
