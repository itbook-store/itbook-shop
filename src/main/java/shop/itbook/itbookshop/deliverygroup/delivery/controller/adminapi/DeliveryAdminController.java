package shop.itbook.itbookshop.deliverygroup.delivery.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
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
     * 모든 배송 정보를 최신 배송 상태와 함께 리스트로 반환합니다.
     *
     * @return 최신 상태가 포함된 배송 정보
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<DeliveryWithStatusResponseDto>>> getDeliveryListWithStatus(
        @PageableDefault Pageable pageable) {

        Page<DeliveryWithStatusResponseDto> deliveryList =
            deliveryService.findDeliveryListWithStatus(pageable);

        PageResponse<DeliveryWithStatusResponseDto> pageResponse = new PageResponse<>(deliveryList);

        CommonResponseBody<PageResponse<DeliveryWithStatusResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getResultMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 배송 상태가 배송대기인 배송 정보를 반환합니다.
     *
     * @return 상태가 배송대기인 배송 정보 리스트
     */
    @GetMapping("/wait-list")
    public ResponseEntity<CommonResponseBody<PageResponse<DeliveryWithStatusResponseDto>>> getDeliveryListWithStatusWait(
        @PageableDefault Pageable pageable) {

        Page<DeliveryWithStatusResponseDto> deliveryWaitList =
            deliveryService.findDeliveryListWithStatusWait(pageable);

        PageResponse<DeliveryWithStatusResponseDto> pageResponse =
            new PageResponse<>(deliveryWaitList);

        CommonResponseBody<PageResponse<DeliveryWithStatusResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getResultMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 배송 대기 상태의 배송 정보들을 리스트 형태로 배송 서버에 요청보냅니다.
     *
     * @return 배송 등록 성공한 배송 정보들의 리스트
     */
    @PostMapping("/registration")
    public ResponseEntity<CommonResponseBody<List<DeliveryDetailResponseDto>>> addDeliveryListWithStatusWait() {

        CommonResponseBody<List<DeliveryDetailResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getResultMessage()),
                deliveryService.sendDeliveryListWithStatusWait()
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
