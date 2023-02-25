package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi.nonmember;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
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
import shop.itbook.itbookshop.ordergroup.order.controller.serviceapi.OrderController;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.order.service.nonmember.OrderNonMemberService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(OrderNonMemberController.class)
class OrderNonMemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderNonMemberService mockOrderNonmemberService;

    @Test
    void orderNonMemberDetails() throws Exception {
        OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();

        given(mockOrderNonmemberService.findNonMemberOrderDetails(anyLong(), anyString()))
            .willReturn(orderDetailsResponseDto);

        mockMvc.perform(get("/api/orders/details/1/non-member?orderCode=123"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_DETAILS_OF_NON_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    void orderNonMemberDetailsSubscription() throws Exception {
        OrderSubscriptionDetailsResponseDto orderDetailsResponseDto =
            new OrderSubscriptionDetailsResponseDto();

        given(
            mockOrderNonmemberService.findNonMemberSubscriptionOrderDetails(anyLong(), anyString()))
            .willReturn(List.of(orderDetailsResponseDto));

        mockMvc.perform(get("/api/orders/details/1/non-member/subscription?orderCode=123"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_DETAILS_OF_NON_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage())));
    }
}