package shop.itbook.itbookshop.coupongroup.couponissue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.CategoryCouponApplyService;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CategoryCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CouponIssueListByGroupResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.OrderTotalCouponIssueResponseListDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.ProductCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.impl.CouponIssueServiceImpl;
import shop.itbook.itbookshop.coupongroup.productcoupon.service.ProductCouponService;
import shop.itbook.itbookshop.coupongroup.productcouponapply.service.ProductCouponApplyService;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.CouponIncreasePointHistoryService;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CouponIssueServiceImpl.class)
class CouponIssueServiceTest {

    @Autowired
    CouponIssueService couponIssueService;

    @MockBean
    CouponIssueRepository couponIssueRepository;

    @MockBean
    UsageStatusService usageStatusService;

    @MockBean
    MemberService memberService;

    @MockBean
    CouponService couponService;

    @MockBean
    CouponIncreasePointHistoryService couponIncreasePointHistoryService;

    @MockBean
    CategoryCouponApplyService categoryCouponApplyService;
    @MockBean
    ProductCouponService productCouponService;
    @MockBean
    ProductCouponApplyService productCouponApplyService;
    @MockBean
    OrderProductRepository orderProductRepository;

    @Test
    @DisplayName("유저가 사용 가능한 모든 쿠폰을 불러오는지 테스트")
    void findMemberAvailableCouponIssuesList() {

        //given
        given(couponIssueRepository.findAvailableProductCouponIssueByMemberNo(anyLong()))
            .willReturn(List.of(new ProductCouponIssueListResponseDto(), new ProductCouponIssueListResponseDto()));
        given(couponIssueRepository.findAvailableOrderTotalCouponIssueByMemberNo(anyLong()))
            .willReturn(List.of(new OrderTotalCouponIssueResponseListDto(), new OrderTotalCouponIssueResponseListDto()));
        given(couponIssueRepository.findAvailableCategoryCouponIssueByMemberNo(anyLong()))
            .willReturn(List.of(new CategoryCouponIssueListResponseDto(), new CategoryCouponIssueListResponseDto()));

        //when
        CouponIssueListByGroupResponseDto actual = couponIssueService.findMemberAvailableCouponIssuesList(anyLong());

        //then
        assertThat(actual.getCategoryCouponList()).hasSize(2);
        assertThat(actual.getProductCouponList()).hasSize(2);
        assertThat(actual.getOrderTotalCouponList()).hasSize(2);
    }
}