package shop.itbook.itbookshop.ordergroup.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderServiceImpl;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.repository.OrderProductHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.paymentgroup.payment.repository.PaymentRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 정재원
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderServiceImpl.class)
class OrderServiceTest {


    @Autowired
    OrderService orderService;

    @MockBean
    ProductService productService;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    OrderProductRepository orderProductRepository;
    @MockBean
    OrderProductHistoryRepository orderProductHistoryRepository;
    @MockBean
    OrderMemberRepository orderMemberRepository;
    @MockBean
    OrderNonMemberRepository orderNonMemberRepository;
    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    DeliveryService deliveryService;
    @MockBean
    MemberService memberService;
    @MockBean
    OrderStatusService orderStatusService;

    @Test
    @DisplayName("사용자의 주문 목록을 여러 정보와 함께 조회 성공")
    void findOrderListOfMemberWithStatus() {

        // given
        List<OrderListMemberViewResponseDto> orderListViewResponseDtoListMember = new ArrayList<>();

        OrderListMemberViewResponseDto
            orderListMemberViewResponseDto = new OrderListMemberViewResponseDto();
        ReflectionTestUtils.setField(orderListMemberViewResponseDto, "orderNo", 1L);
        ReflectionTestUtils.setField(orderListMemberViewResponseDto, "recipientName", "테스트 이름");
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
    @DisplayName("주문 상세 Dto 조회 성공")
    void findOrderDetailsSuccessTest() {
        
    }
}