package shop.itbook.itbookshop.deliverygroup.delivery.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.deliverygroup.delivery.resultmessageenum.DeliveryResultMessageEnum;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;

/**
 * 배송 관련 요청을 받아 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송을 완료 상태로 처리합니다.
     *
     * @param deliveryNo 배송 번호
     * @return 배송 완료가 되었을 경우 ok.
     * @author 정재원 *
     */
    @PostMapping("/completion/{deliveryNo}")
    public ResponseEntity<CommonResponseBody<Void>> deliveryCompletion(
        @PathVariable Long deliveryNo) {

        deliveryService.completeDelivery(deliveryNo);

        CommonResponseBody<Void> responseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                DeliveryResultMessageEnum.DELIVERY_COMPLETION_SUCCESS_MESSAGE.getResultMessage()),
                null);

        return ResponseEntity.ok().body(responseBody);
    }
}
