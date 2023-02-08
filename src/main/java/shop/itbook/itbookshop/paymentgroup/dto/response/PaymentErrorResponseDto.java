package shop.itbook.itbookshop.paymentgroup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class PaymentErrorResponseDto {
    private String code;
    private String message;
}
