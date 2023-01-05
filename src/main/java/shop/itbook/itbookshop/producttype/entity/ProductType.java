package shop.itbook.itbookshop.producttype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품유형에 대한 엔티티 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_type")
@Entity
public class ProductType {
    @Id
    @Column(name = "product_type_no", nullable = false)
    private Integer productTypeNo;

    @Column(name = "product_type_name", nullable = false)
    private String productTypeName;
}
