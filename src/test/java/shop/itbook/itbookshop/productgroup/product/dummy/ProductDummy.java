package shop.itbook.itbookshop.productgroup.product.dummy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

public class ProductDummy {

    public static ProductAddRequestDto getProductRequest() {

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(4);

        return ProductAddRequestDto.builder()
            .productName("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .isSelled(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .stock(1)
            .categoryNoList(categoryList)
            .increasePointPercent(1)
            .rawPrice(12000L)
            .fixedPrice(20000L)
            .discountPercent(10.0)
            .isPointApplyingBasedSellingPrice(true)
            .isPointApplying(true)
            .isSubscription(false)
            .build();
    }

    public static ProductModifyRequestDto getProductModifyRequest() {

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(4);

        return ProductModifyRequestDto.builder()
            .productName("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .stock(1)
            .categoryNoList(categoryList)
            .increasePointPercent(1)
            .rawPrice(12000L)
            .fixedPrice(20000L)
            .discountPercent(10.0)
            .isPointApplyingBasedSellingPrice(true)
            .isPointApplying(true)
            .isSubscription(false)
            .build();
    }

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
