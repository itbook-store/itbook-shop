package shop.itbook.itbookshop.coupongroup.productcoupon.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.service.ProductCouponService;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(ProductCouponServiceImpl.class)
class ProductCouponServiceImplTest {

    @Autowired
    ProductCouponService productCouponService;

    @MockBean
    ProductCouponRepository productCouponRepository;
    @MockBean
    CouponService couponService;
    @MockBean
    ProductService productService;

    @Test
    void addProductCoupon() {
        //given
        Long couponNo = 1L;
        given(productService.findProductEntity(anyLong())).willReturn(ProductDummy.getProductSuccess());
        given(couponService.addCoupon(any())).willReturn(couponNo);
        given(productCouponRepository.save(any())).willReturn(new ProductCoupon(couponNo, ProductDummy.getProductSuccess()));
        //when
        Long result = productCouponService.addProductCoupon(new ProductCouponRequestDto(1L, new CouponRequestDto()));
        //then
        assertThat(result).isEqualTo(couponNo);
    }

    @Test
    void findProductCouponPageList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto()), pageRequest, 10);

        given(productCouponRepository.findProductCouponPageList(any())).willReturn(page);

        Page<AdminCouponListResponseDto> results = productCouponService.findProductCouponPageList(pageRequest.previousOrFirst());

        assertThat(results).hasSize(page.getContent().size());

    }

    @Test
    void findByProductCoupon() {
        Long couponNo = 1L;
        given(productCouponRepository.findByProductCouponByCouponNo(couponNo)).willReturn(new ProductCoupon(couponNo, new Product()));

        ProductCoupon result = productCouponService.findByProductCoupon(couponNo);

        assertThat(result.getCouponNo()).isEqualTo(couponNo);
    }
}