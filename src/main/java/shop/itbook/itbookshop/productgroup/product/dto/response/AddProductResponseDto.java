package shop.itbook.itbookshop.productgroup.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Getter;

/**
 * 상품을 등록한 후 Pk 값인 상품 번호를 반환하기 위한 ResponseDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public class AddProductResponseDto {

    @JsonProperty("product_no")
    private Long productNo;

    private AddProductResponseDto(Long productNo) {
        if (Objects.isNull(productNo)) {
            throw new IllegalArgumentException("productNo is null");
        }
        this.productNo = productNo;
    }

    public static AddProductResponseDto getInstance(Long productNo) {
        return new AddProductResponseDto(productNo);
    }
}
