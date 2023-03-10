package shop.itbook.itbookshop.ordergroup.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcouponapply.repository.ProductCouponApplyRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudService;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudServiceImpl;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.paymentgroup.card.repository.CardRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.payment.repository.PaymentRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

/**
 * @author ?????????
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderCrudServiceImpl.class)
class OrderServiceTest {

    @Autowired
    OrderCrudService orderService;

    @MockBean
    DeliveryRepository deliveryRepository;
    @MockBean
    CouponService couponService;

    @MockBean
    CardRepository cardRepository;
    @MockBean
    EasypayRepository easypayRepository;
    @MockBean
    OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;

    @MockBean
    OrderSubscriptionRepository orderSubscriptionRepository;
    @MockBean
    CouponIssueService couponIssueService;
    @MockBean
    ProductService productService;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    OrderMemberRepository orderMemberRepository;
    @MockBean
    OrderNonMemberRepository orderNonMemberRepository;
    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    OrderProductService orderProductService;
    @MockBean
    OrderStatusHistoryService orderStatusHistoryService;
    @MockBean
    DeliveryService deliveryService;
    @MockBean
    MemberService memberService;
    @MockBean
    OrderStatusService orderStatusService;
    @MockBean
    CategoryCouponRepository categoryCouponRepository;
    @MockBean
    ProductCouponRepository productCouponRepository;
    @MockBean
    ProductCategoryRepository productCategoryRepository;
    @MockBean
    OrderTotalCouponRepository orderTotalCouponRepository;
    @MockBean
    CategoryCouponApplyRepository categoryCouponApplyRepository;
    @MockBean
    ProductCouponApplyRepository productCouponApplyRepository;
    @MockBean
    OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy;
    @MockBean
    OrderCancelIncreasePointHistoryService orderCancelIncreasePointHistoryService;
    @MockBean
    CouponIssueRepository couponIssueRepository;
    @MockBean
    OrderTotalCouponApplyService orderTotalCouponApplyService;

    @MockBean
    RedisTemplate redisTemplate;

    @MockBean
    ObjectMapper objectMapper;


    @Test
    @DisplayName("???????????? ?????? ????????? ?????? ????????? ?????? ?????? ??????")
    void findOrderListOfMemberWithStatus() {

        // given
        List<OrderListMemberViewResponseDto> orderListViewResponseDtoListMember = new ArrayList<>();

        OrderListMemberViewResponseDto
            orderListMemberViewResponseDto = new OrderListMemberViewResponseDto();
        ReflectionTestUtils.setField(orderListMemberViewResponseDto, "orderNo", 1L);
        ReflectionTestUtils.setField(orderListMemberViewResponseDto, "recipientName", "????????? ??????");
        ReflectionTestUtils.setField(orderListMemberViewResponseDto, "recipientPhoneNumber",
            "010-xxx-test");
        ReflectionTestUtils.setField(orderListMemberViewResponseDto, "trackingNo", "1234567");

        orderListViewResponseDtoListMember.add(orderListMemberViewResponseDto);
        Page<OrderListMemberViewResponseDto> page = new PageImpl<>(
            orderListViewResponseDtoListMember);

        given(orderRepository.getOrderListOfMemberWithStatus(any(), any())).willReturn(page);

        PageRequest pageable = PageRequest.of(0, 10);

        OrderListMemberViewResponseDto resultDto =
            orderService.findOrderListOfMemberWithStatus(pageable, 1L).getContent().get(0);

        // then
        assertThat(resultDto.getOrderNo()).isEqualTo(orderListMemberViewResponseDto.getOrderNo());
        assertThat(resultDto.getRecipientName()).isEqualTo(
            orderListMemberViewResponseDto.getRecipientName());
        assertThat(resultDto.getRecipientPhoneNumber()).isEqualTo(
            orderListMemberViewResponseDto.getRecipientPhoneNumber());
        assertThat(resultDto.getTrackingNo()).isEqualTo(
            orderListMemberViewResponseDto.getTrackingNo());
    }

    @Test
    @DisplayName("?????? ?????? Dto ?????? ??????")
    void findOrderDetailsSuccessTest() {

    }
}