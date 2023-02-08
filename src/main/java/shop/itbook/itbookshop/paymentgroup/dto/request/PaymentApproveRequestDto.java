package shop.itbook.itbookshop.paymentgroup.dto.request;

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
public class PaymentApproveRequestDto {
    private String paymentKey;
    private String orderId;
    private Long amount;
}
