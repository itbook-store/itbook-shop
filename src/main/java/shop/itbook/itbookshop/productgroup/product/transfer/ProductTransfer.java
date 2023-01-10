package shop.itbook.itbookshop.productgroup.product.transfer;

import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
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
    public static Product dtoToEntityAdd(AddProductRequestDto requestDto) {
        return Product.builder().name(requestDto.getName())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription()).stock(requestDto.getStock())
            .isSelled(requestDto.isSelled()).isDeleted(requestDto.isDeleted())
            .thumbnailUrl(requestDto.getThumbnailUrl())
            .fixedPrice(requestDto.getFixedPrice())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent()).rawPrice(requestDto.getRawPrice())
            .build();
    }

}
