package shop.itbook.itbookshop.productgroup.product.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

public class ProductDummy {

    public static Product getProductSuccess() {

        return Product.builder()
            .name("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .stock(1)
            .isSelled(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .thumbnailUrl("testUrl")
            .fixedPrice(20000L)
            .increasePointPercent(1)
            .discountPercent(10.0)
            .rawPrice(12000L)
            .productCreatedAt(LocalDateTime.now())
            .isPointApplyingBasedSellingPrice(false)
            .isPointApplying(true)
            .isSubscription(false)
            .build();
    }

    public static Product getProductFailure() {

        return Product.builder()
            .name("객체지향의 사실과 오해")
            .simpleDescription(null)
            .detailsDescription("상세 설명")
            .productCreatedAt(LocalDateTime.now())
            .isSelled(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .thumbnailUrl("testUrl")
            .stock(1)
            .fixedPrice(20000L)
            .increasePointPercent(1)
            .discountPercent(10.0)
            .rawPrice(12000L)
            .isPointApplyingBasedSellingPrice(false)
            .isPointApplying(true)
            .isSubscription(false)
            .build();
    }

    public static ProductDetailsResponseDto getProductDetailsResponseDto() {
        return ProductDetailsResponseDto.builder()
            .productName("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .stock(1)
            .isSelled(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .fileThumbnailsUrl("testUrl")
            .fixedPrice(20000L)
            .increasePointPercent(1)
            .discountPercent(10.0)
            .rawPrice(12000L)
            .bookCreatedAt(LocalDateTime.now())
            .isPointApplyingBasedSellingPrice(false)
            .isPointApplying(true)
            .isSubscription(false)
            .isbn("isbn")
            .pageCount(10)
            .bookCreatedAt(LocalDateTime.now())
            .isEbook(false)
            .fileEbookUrl(null)
            .publisherName("출판사")
            .authorName("작가")
            .build();
    }
}
