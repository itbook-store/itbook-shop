package shop.itbook.itbookshop.paymentgroup.payment.service.impl;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookshop.paymentgroup.InvalidPaymentException;
import shop.itbook.itbookshop.paymentgroup.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.dto.response.PaymentResponseDto;
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
    private final String TOSS_REQUEST_URL =
        "https://api.tosspayments.com/v1/payments/confirm";
    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public PaymentResponseDto requestApprovePayment(
        PaymentApproveRequestDto paymentApproveRequestDto) {

        String encodedSecretKey =
            Base64.getEncoder().encodeToString((TEST_SECRET_KEY + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(encodedSecretKey);

        HttpEntity<PaymentApproveRequestDto> httpEntity =
            new HttpEntity<>(paymentApproveRequestDto, headers);

        ResponseEntity<PaymentResponseDto> response = null;

        try {
            response = restTemplate.exchange(TOSS_REQUEST_URL, HttpMethod.POST, httpEntity,
                PaymentResponseDto.class);
        } catch (HttpClientErrorException e) {
            log.info(e.getMessage());
            throw new InvalidPaymentException();
        }

        return response.getBody();
    }

}
