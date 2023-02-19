package shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.CategoryCouponApplyService;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CategoryCouponApplyServiceImpl.class)
class CategoryCouponApplyServiceImplTest {


    @Autowired
    CategoryCouponApplyService categoryCouponApplyService;

    @MockBean
    CategoryCouponApplyRepository categoryCouponApplyRepository;

    Order order;

    Product product;

    OrderProduct orderProduct;

    @BeforeEach
    void setup(){
        order = OrderDummy.getOrder();
        product = ProductDummy.getProductSuccess();
        orderProduct = OrderProductDummy.createOrderProduct(order, product);
    }
    @Test
    void saveCategoryCouponApplyAndChangeCouponIssues() {
        //given
        Long couponIssueNo = 1L;
        //when
        categoryCouponApplyService.saveCategoryCouponApplyAndChangeCouponIssues(couponIssueNo, orderProduct);
        //then
        verify(categoryCouponApplyRepository).save(any());
    }

    @Test
    void cancelCategoryCouponApplyAndChangeCouponIssues_success() {
        Long couponIssueNo = 1L;
        given(categoryCouponApplyRepository.existsById(anyLong())).willReturn(true);
        //when
        categoryCouponApplyService.cancelCategoryCouponApplyAndChangeCouponIssues(couponIssueNo);
        //then
        verify(categoryCouponApplyRepository).deleteById(any());
    }

    @Test
    void cancelCategoryCouponApplyAndChangeCouponIssues_fail() {
        Long couponIssueNo = 1L;
        given(categoryCouponApplyRepository.existsById(anyLong())).willReturn(false);
        //when
        categoryCouponApplyService.cancelCategoryCouponApplyAndChangeCouponIssues(couponIssueNo);
        //then
        verify(categoryCouponApplyRepository, never()).deleteById(any());
    }
}