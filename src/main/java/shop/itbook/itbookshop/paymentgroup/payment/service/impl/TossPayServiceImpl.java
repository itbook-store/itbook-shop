package shop.itbook.itbookshop.paymentgroup.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.CancelReason;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentCancelException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentException;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.service.PayService;

/**
 * 토스페이 서비스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TossPayServiceImpl implements PayService {

    @Value("${toss.payment.secret.key}")
    private String TEST_SECRET_KEY;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PaymentResponseDto.PaymentDataResponseDto requestApprovePayment(
        PaymentApproveRequestDto paymentApproveRequestDto) {

        String TOSS_REQUEST_PAYMENT_URL =
            "https://api.tosspayments.com/v1/payments/confirm";

        HttpHeaders headers = getRequestHttpHeaders();

        HttpEntity<PaymentApproveRequestDto> httpEntity =
            new HttpEntity<>(paymentApproveRequestDto, headers);

        ResponseEntity<PaymentResponseDto.PaymentDataResponseDto> response;

        try {
            response = restTemplate.exchange(TOSS_REQUEST_PAYMENT_URL, HttpMethod.POST, httpEntity,
                PaymentResponseDto.PaymentDataResponseDto.class);

        } catch (HttpClientErrorException e) {
            throw new InvalidPaymentException(e.getMessage());
        }
        return response.getBody();

    }

    @Override
    public PaymentResponseDto.PaymentDataResponseDto requestCanceledPayment(
        PaymentCanceledRequestDto paymentCanceledRequestDto, String paymentKey) {

        String requestPaymentCanceledUrl = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.tosspayments.com")
            .path("/v1/payments/{paymentKey}/cancel")
            .build(paymentKey).toString();

        HttpHeaders headers = getRequestHttpHeaders();

        CancelReason cancelReason = new CancelReason(paymentCanceledRequestDto.getCancelReason());

        HttpEntity<CancelReason> httpEntity = new HttpEntity<>(cancelReason, headers);

        ResponseEntity<PaymentResponseDto.PaymentDataResponseDto> response;

        try {
            response =
                restTemplate.exchange(requestPaymentCanceledUrl, HttpMethod.POST, httpEntity,
                    PaymentResponseDto.PaymentDataResponseDto.class);

        } catch (HttpClientErrorException e) {
            throw new InvalidPaymentCancelException(e.getMessage());
        }
        return response.getBody();
    }

    private HttpHeaders getRequestHttpHeaders() {
        String encodedSecretKey =
            Base64.getEncoder().encodeToString((TEST_SECRET_KEY + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(encodedSecretKey);
        return headers;
    }

}
