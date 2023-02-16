package shop.itbook.itbookshop.ordergroup.order.dummy;

import java.util.ArrayList;
import java.util.List;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;

/**
 * @author 정재원
 * @since 1.0
 */
public class ProductDetailsDtoDummy {

    public static ProductDetailsDto getDummy() {
        ProductDetailsDto productDetailsDto = new ProductDetailsDto();

        productDetailsDto.setProductNo(123L);
        productDetailsDto.setProductCnt(2);
        productDetailsDto.setCouponIssueNo(999L);

        return productDetailsDto;
    }

    public static List<ProductDetailsDto> getDummyList() {

        List<ProductDetailsDto> productDetailsDtoList = new ArrayList<>();

        ProductDetailsDto productDetailsDto = getDummy();

        productDetailsDtoList.add(productDetailsDto);
        productDetailsDto.setProductNo(234L);
        productDetailsDto.setCouponIssueNo(1234L);
        productDetailsDtoList.add(productDetailsDto);

        return productDetailsDtoList;
    }
}
