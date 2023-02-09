package shop.itbook.itbookshop.paymentgroup.paymentstatus.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.impl.PaymentStatusEnumConverter;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * 결제상태에 대한 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_status")
@Entity
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_status_no", nullable = false)
    private Integer paymentStatusNo;

    @Convert(converter = PaymentStatusEnumConverter.class)
    @Column(name = "payment_status_name", unique = true, nullable = false)
    private PaymentStatusEnum paymentStatusEnum;
}
