package shop.itbook.itbookshop.inquirytype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.inquirytypeenum.InquiryTypeEnum;

/**
 * 문의유형에 대한 엔티티입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inquiry_type")
@Entity
public class InquiryType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_type_no", nullable = false)
    private Integer inquiryTypeNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "inquiry_type_no")
    private InquiryTypeEnum inquiryType;
}
