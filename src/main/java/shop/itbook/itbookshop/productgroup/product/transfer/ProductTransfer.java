package shop.itbook.itbookshop.productgroup.product.transfer;

import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductTransfer {
    private ProductTransfer() {
    }

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
