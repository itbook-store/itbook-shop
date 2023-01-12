package shop.itbook.itbookshop.deliverygroup.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DummyServerDeliveryRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.service.DeliveryService;

/**
 * @author 정재원
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class TestController {

    private final DeliveryService deliveryService;

    @PostMapping
    public DeliveryResponseDto addDelivery() throws JsonProcessingException {
        DummyServerDeliveryRequestDto deliveryRequestDto = DummyServerDeliveryRequestDto.builder()
            .orderNo(1L)
            .receiverName("테스트 수령인")
            .receiverAddress("테스트 주소")
            .receiverDetailAddress("테스트 주소")
            .receiverDetailAddress("테스트 상세 주소")
            .receiverPhoneNumber("030-111-222")
            .build();

        return deliveryService.addDelivery(deliveryRequestDto);
    }
}
