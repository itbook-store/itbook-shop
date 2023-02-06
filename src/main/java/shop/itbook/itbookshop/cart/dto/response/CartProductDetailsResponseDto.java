package shop.itbook.itbookshop.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDetailsResponseDto {
    private Integer productCount;

    private ProductDetailsResponseDto productDetailsResponseDto;
}
