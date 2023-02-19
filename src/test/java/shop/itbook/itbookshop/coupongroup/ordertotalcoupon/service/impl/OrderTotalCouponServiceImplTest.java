package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

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
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.request.OrderTotalCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service.OrderTotalCouponService;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderTotalCouponServiceImpl.class)
class OrderTotalCouponServiceImplTest {

    @Autowired
    OrderTotalCouponService orderTotalCouponService;

    @MockBean
    OrderTotalCouponRepository orderTotalCouponRepository;

    @MockBean
    CouponService couponService;

    @Test
    void addOrderTotalCoupon() {
        //given
        Long couponNo = 1L;
        given(couponService.addCoupon(any())).willReturn(couponNo);
        given(orderTotalCouponRepository.save(any())).willReturn(new OrderTotalCoupon(couponNo));
        //when
        Long result = orderTotalCouponService.addOrderTotalCoupon(new OrderTotalCouponRequestDto(new CouponRequestDto()));
        //then
        assertThat(result).isEqualTo(couponNo);
    }

    @Test
    void findTotalCouponPageList() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto()), pageRequest, 10);

        given(orderTotalCouponRepository.findTotalCouponPageList(any())).willReturn(page);

        Page<AdminCouponListResponseDto> results = orderTotalCouponService.findTotalCouponPageList(pageRequest.previousOrFirst());

        assertThat(results).hasSize(page.getContent().size());
    }
}