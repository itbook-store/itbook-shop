package shop.itbook.itbookshop.productgroup.producttype.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
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
import shop.itbook.itbookshop.productgroup.producttype.converter.impl.ProductTypeEnumConverter;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_no", nullable = false)
    private Integer productTypeNo;

    @Convert(converter = ProductTypeEnumConverter.class)
    @Column(name = "product_type_name", nullable = false, columnDefinition = "varchar(255)", unique = true)
    private ProductTypeEnum productTypeEnum;
}
