package shop.itbook.itbookshop.elastic.transfer;

import shop.itbook.itbookshop.elastic.document.SearchProductBook;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

public class SearchProductBookTransfer {

    private SearchProductBookTransfer() {

    }

    /**
     * 상품 엔티티를 엘라스틱 서치의 상품 도큐먼트로 변환하는 기능을 하는 메서드입니다.
     *
     * @param product 상품 엔터티
     * @return 엘라스틱 서치의 상품 도큐먼트
     */
    public static SearchProductBook entityToDocument(Product product) {
        return SearchProductBook.builder()
            .productNo(product.getProductNo())
            .discountPercent(product.getDiscountPercent())
            .fixedPrice(product.getFixedPrice())
            .simpleDescription(product.getSimpleDescription())
            .name(product.getName())
            .rawPrice(product.getRawPrice())
            .thumbnailUrl(product.getThumbnailUrl())
            .authorName(product.getBook().getAuthorName())
            .isbn(product.getBook().getIsbn())
            .isEbook(product.getBook().getIsEbook())
            .publisherName(product.getBook().getPublisherName())
            .build();
    }
//
//    /**
//     * 엘라스틱 서치의 도큐먼트를 DTO로 변환하는 기능을 담당하는 메소드입니다.
//     *
//     * @param searchProduct 엘라스틱 서치 상품 도큐먼트
//     * @return 엘라스틱 서치 도큐먼트를 담을 DTO입니다.
//     */
//    public static ProductSearchResponseDto documentToDto(SearchProduct searchProduct) {
//        return ProductSearchResponseDto.builder()
//            .productNo(searchProduct.getProductNo())
//            .name(searchProduct.getName())
//            .simpleDescription(searchProduct.getSimpleDescription())
//            .detailsDescription(searchProduct.getDetailsDescription())
//            .rawPrice(searchProduct.getRawPrice())
//            .stock(searchProduct.getStock())
//            .isSelled(searchProduct.getIsSelled())
//            .thumbnailUrl(searchProduct.getThumbnailUrl())
//            .isForceSoldOut(searchProduct.getIsForceSoldOut())
//            .fixedPrice(searchProduct.getFixedPrice())
//            .increasePointPercent(searchProduct.getIncreasePointPercent())
//            .discountPercent(searchProduct.getDiscountPercent())
//            .selledPrice((long) (searchProduct.getFixedPrice() *
//                ((100 - searchProduct.getDiscountPercent()) * 0.01)))
//            .build();
//    }
}
