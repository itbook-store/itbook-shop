package shop.itbook.itbookshop.productgroup.product.transfer;

import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * 엘라스틱 서치의 도큐먼트와 상품의 엔터티와 DTO 간의 변환을 담당하는 클래스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class SearchProductTransfer {

    private SearchProductTransfer() {

    }

    /**
     * 상품 엔티티를 엘라스틱 서치의 상품 도큐먼트로 변환하는 기능을 하는 메서드입니다.
     *
     * @param product 상품 엔터티
     * @return 엘라스틱 서치의 상품 도큐먼트
     */
    public static SearchProduct entityToDocument(Product product) {
        return SearchProduct.builder()
            .productNo(product.getProductNo())
            .productCreatedAt(product.getProductCreatedAt())
            .dailyHits(product.getDailyHits())
            .detailsDescription(product.getDetailsDescription())
            .discountPercent(product.getDiscountPercent())
            .fixedPrice(product.getFixedPrice())
            .simpleDescription(product.getSimpleDescription())
            .increasePointPercent(product.getIncreasePointPercent())
            .isDeleted(product.getIsForceSoldOut())
            .isForceSoldOut(product.getIsExposed())
            .name(product.getName())
            .rawPrice(product.getRawPrice())
            .stock(product.getStock())
            .thumbnailUrl(product.getThumbnailUrl())
            .build();
    }

    /**
     * 엘라스틱 서치의 도큐먼트를 DTO로 변환하는 기능을 담당하는 메소드입니다.
     *
     * @param searchProduct 엘라스틱 서치 상품 도큐먼트
     * @return 엘라스틱 서치 도큐먼트를 담을 DTO입니다.
     */
    public static ProductSearchResponseDto documentToDto(SearchProduct searchProduct) {
        return ProductSearchResponseDto.builder()
            .productNo(searchProduct.getProductNo())
            .name(searchProduct.getName())
            .simpleDescription(searchProduct.getSimpleDescription())
            .detailsDescription(searchProduct.getDetailsDescription())
            .rawPrice(searchProduct.getRawPrice())
            .stock(searchProduct.getStock())
            .isForceSoldOut(searchProduct.getIsForceSoldOut())
            .thumbnailUrl(searchProduct.getThumbnailUrl())
            .isDeleted(searchProduct.getIsDeleted())
            .fixedPrice(searchProduct.getFixedPrice())
            .increasePointPercent(searchProduct.getIncreasePointPercent())
            .discountPercent(searchProduct.getDiscountPercent())
            .selledPrice((long) (searchProduct.getFixedPrice() *
                ((100 - searchProduct.getDiscountPercent()) * 0.01)))
            .build();
    }
}
