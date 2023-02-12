package shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.impl;

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
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderTotalCouponApplyServiceImpl.class)
class OrderTotalCouponApplyServiceImplTest {

    @Autowired
    OrderTotalCouponApplyService orderTotalCouponApplyService;

    @MockBean
    OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy;
    @MockBean
    CouponIssueService couponIssueService;

    Order order;

    @BeforeEach
    void setUp(){
        order = OrderDummy.getOrder();
    }
    @Test
    void saveOrderTotalCouponApplyAndChangeCouponIssue() {
        //given
        Long couponIssueNo = 1L;
        given(couponIssueService.usingCouponIssue(anyLong())).willReturn(CouponIssueDummy.getCouponIssue());
        //when
        orderTotalCouponApplyService.saveOrderTotalCouponApplyAndChangeCouponIssue(couponIssueNo, order);
        //then
        verify(orderTotalCouponApplyRepositoy).save(any());
    }

    @Test
    void cancelOrderTotalCouponApplyAndChangeCouponIssue() {
        //given
        Long couponIssueNo = 1L;
        given(couponIssueService.cancelCouponIssue(anyLong())).willReturn(CouponIssueDummy.getCouponIssue());
        //when
        orderTotalCouponApplyService.cancelOrderTotalCouponApplyAndChangeCouponIssue(couponIssueNo);
        //then
        verify(orderTotalCouponApplyRepositoy).deleteById(any());
    }
}