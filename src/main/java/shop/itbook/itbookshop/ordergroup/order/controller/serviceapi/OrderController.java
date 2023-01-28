package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.memberservice.entity.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaperResponseDto;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;

/**
 * 주문과 관련된 요청을 처리하는 컨트롤러 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final ProductAdminService productAdminService;
    private final MemberDestinationRepository memberDestinationRepository;

    /**
     * 회원의 개별 주문서 작성 처리 로직을 담당하는 컨트롤러입니다.
     *
     * @param productNo 주문하려는 제품의 번호
     * @param memberNo  회원의 번호
     * @return 주문서 작성을 위한 정보가 담긴 Dto
     * @author 정재원 *
     */
    @GetMapping("/sheet")
    public ResponseEntity<CommonResponseBody<OrderPaperResponseDto>> orderWrite(
        @RequestParam("ProductNo") Long productNo, @RequestParam("memberNo") Long memberNo) {

        // TODO: 2023/01/27 제품 정보 가져오기
        Product product = productAdminService.findProduct(productNo);

        // TODO: 2023/01/27 회원 정보를 바탕으로 회원의 배송지 정보 가져오기
        List<MemberDestination> memberDestinationList =
            memberDestinationRepository.findMemberDestinationsByMember_MemberNo(memberNo);

        return null;
    }
}