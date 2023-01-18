package shop.itbook.itbookshop.deliverygroup.delivery.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.resultmessageenum.DeliveryResultMessageEnum;
import shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.DeliveryAdminService;

/**
 * 관리자의 배송 관련 요청을 담당하는 컨트롤러 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/deliveries")
public class DeliveryAdminController {

    private final DeliveryAdminService deliveryService;

    /**
     * 개별 배송을 배송 서버에 요청합니다.
     *
     * @param deliveryRequestDto 배송 정보를 담은 요청 Dto
     * @return 배송 등록 성공한 배송 정보가 담긴 응답 Dto
     * @author 정재원
     */
    @PostMapping("/one")
    public DeliveryDetailResponseDto addDelivery(
        @RequestBody DeliveryServerRequestDto deliveryRequestDto) {

        return deliveryService.sendDelivery(deliveryRequestDto);
    }

    /**
     * 모든 배송 정보를 최신 배송 상태와 함께 리스트로 반환합니다.
     *
     * @return 최신 상태가 포함된 배송 정보
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<List<DeliveryWithStatusResponseDto>>> getDeliveryListWithStatus() {

        CommonResponseBody<List<DeliveryWithStatusResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                deliveryService.findDeliveryListWithStatus()
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 배송 상태가 배송대기인 배송 정보를 반환합니다.
     *
     * @return 상태가 배송대기인 배송 정보 리스트
     */
    @GetMapping("/wait")
    public ResponseEntity<CommonResponseBody<List<DeliveryWithStatusResponseDto>>> getDeliveryListWithStatusWait() {

        CommonResponseBody<List<DeliveryWithStatusResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                deliveryService.findDeliveryListWithStatusWait()
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 배송 대기 상태의 배송 정보들을 리스트 형태로 배송 서버에 요청보냅니다.
     *
     * @return 배송 등록 성공한 배송 정보들의 리스트
     */
    @PostMapping("/post")
    public ResponseEntity<CommonResponseBody<List<DeliveryDetailResponseDto>>> addDeliveryListWithStatusWait() {

        CommonResponseBody<List<DeliveryDetailResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                deliveryService.sendDeliveryListWithStatusWait()
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
