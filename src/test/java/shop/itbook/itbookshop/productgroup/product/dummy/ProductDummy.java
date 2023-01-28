package shop.itbook.itbookshop.productgroup.product.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

public class ProductDummy {

    public static Product getProductSuccess() {

        return Product.builder()
            .name("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .stock(1)
            .isExposed(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .thumbnailUrl("testUrl")
            .fixedPrice(20000L)
            .increasePointPercent(1)
            .discountPercent(10.0)
            .rawPrice(12000L)
            .productCreatedAt(LocalDateTime.now())
            .build();
    }

    public static Product getProductFailure() {

        return Product.builder()
            .name("객체지향의 사실과 오해")
            .simpleDescription(null)
            .detailsDescription("상세 설명")
            .stock(1).isExposed(Boolean.TRUE)
            .isForceSoldOut(Boolean.FALSE)
            .thumbnailUrl("testUrl")
            .fixedPrice(20000L)
            .increasePointPercent(1)
            .discountPercent(10.0)
            .rawPrice(12000L)
            .productCreatedAt(LocalDateTime.now())
            .build();
    }


}