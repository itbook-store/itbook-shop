package shop.itbook.itbookshop.productgroup.product.transfer;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품에 대한 엔티티와 dto 간의 변환을 담당하는 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
public class ProductTransfer {
    private ProductTransfer() {
    }

    /**
     * dto로 넘어온 값을 상품 엔티티로 변환하는 기능을 하는 메서드입니다.
     *
     * @param requestDto 엔티티에 담을 dto입니다.
     * @return 엔티티로 변환된 상품 엔티티입니다.
     * @author
     */
    public static Product dtoToEntityAdd(ProductRequestDto requestDto) {
        return Product.builder()
            .name(requestDto.getProductName())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription())
            .stock(requestDto.getStock())
            .isSelled(requestDto.getIsSelled())
            .isForceSoldOut(requestDto.getIsForceSoldOut())
            .thumbnailUrl(requestDto.getFileThumbnailsUrl())
            .fixedPrice(requestDto.getFixedPrice())
            .productCreatedAt(LocalDateTime.now())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent())
            .rawPrice(requestDto.getRawPrice())
            .build();
    }

    public static Product dtoToEntityAdd(ProductBookRequestDto requestDto) {

        return Product.builder().name(requestDto.getProductName())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription())
            .stock(requestDto.getStock())
            .isSelled(requestDto.getIsSelled())
            .isForceSoldOut(requestDto.getIsForceSoldOut())
            .thumbnailUrl(requestDto.getFileThumbnailsUrl())
            .fixedPrice(requestDto.getFixedPrice())
            .productCreatedAt(LocalDateTime.now())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent()).rawPrice(requestDto.getRawPrice())
            .build();
    }


}
