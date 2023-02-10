package shop.itbook.itbookshop.paymentgroup.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCanceledRequestDto {
    private Long orderNo;
    private String cancelReason;
}
