package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudService;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService mockOrderService;

    @MockBean
    OrderCrudService mockOrderCrudService;


    @Test
    void orderMemberListFind() throws Exception {
        Order order = OrderDummy.getOrder();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(order), pageable, 10);

        given(mockOrderCrudService.findOrderListOfMemberWithStatus(any(Pageable.class), anyLong()))
            .willReturn(page);

        mockMvc.perform(get("/api/orders/list/3"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_LIST_OF_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    void orderAddBeforePayment() {
    }

    @Test
    void subscriptionOrderBeforePayment() {
    }

    @Test
    void orderDetails() throws Exception {
        OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();


        given(mockOrderCrudService.findOrderDetails(anyLong()))
            .willReturn(orderDetailsResponseDto);

        mockMvc.perform(get("/api/orders/details/3"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    void orderSubscriptionDetails() throws Exception {
        OrderSubscriptionDetailsResponseDto orderDetailsResponseDto =
            new OrderSubscriptionDetailsResponseDto();


        given(mockOrderCrudService.findOrderSubscriptionDetailsResponseDto(anyLong()))
            .willReturn(List.of(orderDetailsResponseDto));

        mockMvc.perform(get("/api/orders/details-sub/3"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_SUBSCRIPTION_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    void orderStatusChangePurchaseComplete() {
    }

    @Test
    void orderSubscriptionListByMember() throws Exception {
        OrderSubscriptionListDto orderSubscriptionListDto =
            new OrderSubscriptionListDto();


        given(mockOrderCrudService.findAllSubscriptionOrderListByMember(any(Pageable.class),
            anyLong()))
            .willReturn(new PageImpl<>(List.of(orderSubscriptionListDto)));

        mockMvc.perform(get("/api/orders/list/subscription/3"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_SUBSCRIPTION_LIST_OF_ADMIN_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    void orderDeleteAndStockRollBack() throws Exception {
        Long orderNo = 1L;
        mockOrderCrudService.deleteOrderAndRollBackStock(orderNo);

        mockMvc.perform(delete("/api/orders/1/with-stock-rollback"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_DELETE_AND_STOCK_ROLL_BACK_SUCCESS_MESSAGE.getResultMessage())));
    }
}