package shop.itbook.itbookshop.ordergroup.order.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
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
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(OrderAdminController.class)
class OrderAdminControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderCrudService mockOrderCrudService;

    @Test
    void orderListAdmin() throws Exception {
        Order order = OrderDummy.getOrder();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(order), pageable, 10);

        given(mockOrderCrudService.findOrderListAdmin(any(Pageable.class)))
            .willReturn(page);

        mockMvc.perform(get("/api/admin/orders/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_LIST_OF_ADMIN_FIND_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    void orderSubscriptionListByAdmin() throws Exception {
        Order order = OrderDummy.getOrder();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(order), pageable, 10);

        given(mockOrderCrudService.findAllSubscriptionOrderListByAdmin(any(Pageable.class)))
            .willReturn(page);

        mockMvc.perform(get("/api/admin/orders/list/subscription"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderResultMessageEnum.ORDER_SUBSCRIPTION_LIST_OF_ADMIN_SUCCESS_MESSAGE.getResultMessage())));
    }
}