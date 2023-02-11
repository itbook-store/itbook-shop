package shop.itbook.itbookshop.coupongroup.productcouponapply.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.productcouponapply.repository.ProductCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.productcouponapply.service.ProductCouponApplyService;
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
@Import(ProductCouponApplyServiceImpl.class)
class ProductCouponApplyServiceImplTest {

    @Autowired
    ProductCouponApplyService productCouponApplyService;

    @MockBean
    ProductCouponApplyRepository productCouponApplyRepository;
    @MockBean
    CouponIssueService couponIssueService;

    OrderProduct orderProduct;
    Product product;
    Order order;

    @BeforeEach
    void setUp(){
        product = ProductDummy.getProductSuccess();
        order = OrderDummy.getOrder();
        orderProduct = OrderProductDummy.createOrderProduct(order, product);
    }

    @Test
    void saveProductCouponApplyAndChangeCouponIssue() {
        //given
        Long couponIssueNo = 1L;
        given(couponIssueService.usingCouponIssue(anyLong())).willReturn(CouponIssueDummy.getCouponIssue());
        //when
        productCouponApplyService.saveProductCouponApplyAndChangeCouponIssue(couponIssueNo, orderProduct);
        //then
        verify(productCouponApplyRepository).save(any());
    }

    @Test
    void cancelProductCouponApplyAndChangeCouponIssue() {
        //given
        Long couponIssueNo = 1L;
        given(couponIssueService.cancelCouponIssue(anyLong())).willReturn(CouponIssueDummy.getCouponIssue());
        //when
        productCouponApplyService.cancelProductCouponApplyAndChangeCouponIssue(couponIssueNo);
        //then
        verify(productCouponApplyRepository).deleteById(any());
    }
}