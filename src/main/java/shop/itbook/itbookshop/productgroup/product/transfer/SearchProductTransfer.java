package shop.itbook.itbookshop.productgroup.product.transfer;

import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoArgsConstructor
public class SearchProductTransfer {
    public static SearchProduct entityToDocument(Product product){
        return SearchProduct.builder()
            .productNo(product.getProductNo())
            .productCreatedAt(product.getProductCreatedAt())
            .dailyHits(product.getDailyHits())
            .detailsDescription(product.getDetailsDescription())
            .discountPercent(product.getDiscountPercent())
            .fixedPrice(product.getFixedPrice())
            .simpleDescription(product.getSimpleDescription())
            .increasePointPercent(product.getIncreasePointPercent())
            .isDeleted(product.getIsDeleted())
            .isSelled(product.getIsSelled())
            .name(product.getName())
            .rawPrice(product.getRawPrice())
            .stock(product.getStock())
            .thumbnailUrl(product.getThumbnailUrl())
            .build();
    }

    public static ProductSearchResponseDto documentToDto(SearchProduct searchProduct){
        return ProductSearchResponseDto.builder()
            .productNo(searchProduct.getProductNo())
            .name(searchProduct.getName())
            .simpleDescription(searchProduct.getSimpleDescription())
            .detailsDescription(searchProduct.getDetailsDescription())
            .rawPrice(searchProduct.getRawPrice())
            .stock(searchProduct.getStock())
            .isSelled(searchProduct.getIsSelled())
            .thumbnailUrl(searchProduct.getThumbnailUrl())
            .isDeleted(searchProduct.getIsDeleted())
            .fixedPrice(searchProduct.getFixedPrice())
            .increasePointPercent(searchProduct.getIncreasePointPercent())
            .discountPercent(searchProduct.getDiscountPercent())
            .build();
    }
}
