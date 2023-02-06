package shop.itbook.itbookshop.ordergroup.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderServiceImpl;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
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

    @Test
    void getOrderListOfMemberWithStatus() {

        // given
        List<OrderListViewResponseDto> orderListViewResponseDtoList = new ArrayList<>();

        OrderListViewResponseDto orderListViewResponseDto = new OrderListViewResponseDto();
        ReflectionTestUtils.setField(orderListViewResponseDto, "orderNo", 1L);
        ReflectionTestUtils.setField(orderListViewResponseDto, "orderStatusCreatedAt",
            LocalDateTime.now());
        ReflectionTestUtils.setField(orderListViewResponseDto, "recipientName", "테스트 이름");
        ReflectionTestUtils.setField(orderListViewResponseDto, "recipientPhoneNumber",
            "010-xxx-test");
        ReflectionTestUtils.setField(orderListViewResponseDto, "trackingNo", "1234567");

        orderListViewResponseDtoList.add(orderListViewResponseDto);
        Page<OrderListViewResponseDto> page = new PageImpl<>(orderListViewResponseDtoList);

        given(orderRepository.getOrderListOfMemberWithStatus(any(), any())).willReturn(page);

        PageRequest pageable = PageRequest.of(0, 10);

        OrderListViewResponseDto resultDto =
            orderService.getOrderListOfMemberWithStatus(pageable, 1L).getContent().get(0);

        // then
        assertThat(resultDto.getOrderNo()).isEqualTo(orderListViewResponseDto.getOrderNo());
        assertThat(resultDto.getOrderStatusCreatedAt()).isEqualTo(
            orderListViewResponseDto.getOrderStatusCreatedAt());
        assertThat(resultDto.getRecipientName()).isEqualTo(
            orderListViewResponseDto.getRecipientName());
        assertThat(resultDto.getRecipientPhoneNumber()).isEqualTo(
            orderListViewResponseDto.getRecipientPhoneNumber());
        assertThat(resultDto.getTrackingNo()).isEqualTo(orderListViewResponseDto.getTrackingNo());
    }
}