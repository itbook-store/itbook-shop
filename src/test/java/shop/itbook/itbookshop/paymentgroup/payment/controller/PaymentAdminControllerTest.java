package shop.itbook.itbookshop.paymentgroup.payment.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.OrderResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.service.PaymentService;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;
import shop.itbook.itbookshop.productgroup.productrelationgroup.controller.ProductRelationGroupController;
import shop.itbook.itbookshop.productgroup.productrelationgroup.service.ProductRelationGroupService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(PaymentAdminController.class)
class PaymentAdminControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PaymentAdminController paymentAdminController;
    @MockBean
    PaymentService paymentService;

    @Test
    void requestPayment() throws Exception {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        given(paymentService.requestPayment(any(PaymentApproveRequestDto.class), anyString(),
            anyLong(), any()))
            .willReturn(orderResponseDto);

        PaymentApproveRequestDto paymentApproveRequestDto =
            new PaymentApproveRequestDto(null, null, null);

        // when, then
        mvc.perform(post("/api/admin/payment/request-pay/비회원주문/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentApproveRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(PaymentStatusEnum.DONE.getPaymentStatus())));
    }

    @Test
    void cancelPayment() throws Exception {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        given(paymentService.cancelPayment(any(PaymentCanceledRequestDto.class), anyString()))
            .willReturn(orderResponseDto);

        PaymentCanceledRequestDto paymentCanceledRequestDto =
            new PaymentCanceledRequestDto(null, null);

        // when, then
        mvc.perform(post("/api/admin/payment/request-cancel")
                .param("orderType", "비회원주문")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentCanceledRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(PaymentStatusEnum.CANCELED.getPaymentStatus())));
    }
}