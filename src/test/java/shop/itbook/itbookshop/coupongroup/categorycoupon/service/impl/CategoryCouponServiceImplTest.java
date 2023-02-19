package shop.itbook.itbookshop.coupongroup.categorycoupon.service.impl;

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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.impl.OrderTotalCouponApplyServiceImpl;

/**
 * @author 송다혜
 * @since 1.0
 */

@ExtendWith(SpringExtension.class)
@Import(CategoryCouponServiceImpl.class)
class CategoryCouponServiceImplTest {

    @Autowired
    CategoryCouponService categoryCouponService;

    @MockBean
    CategoryCouponRepository categoryCouponRepository;
    @MockBean
    CategoryService categoryService;
    @MockBean
    CouponService couponService;

    @Test
    void addCategoryCoupon() {
        //given
        Long couponNo = 1L;
        given(categoryService.findCategoryEntity(anyInt())).willReturn(CategoryDummy.getCategoryNoHiddenBook());
        given(couponService.addCoupon(any())).willReturn(couponNo);
        given(categoryCouponRepository.save(any())).willReturn(new CategoryCoupon(couponNo, CategoryDummy.getCategoryNoHiddenBook()));
        //when
        Long result = categoryCouponService.addCategoryCoupon(new CategoryCouponRequestDto(new CouponRequestDto(), 1));
        //then
        assertThat(result).isEqualTo(couponNo);
    }

    @Test
    void deleteCategoryCoupon() {
        Long couponNo = 1L;

        categoryCouponService.deleteCategoryCoupon(couponNo);
        verify(categoryCouponRepository).deleteById(anyLong());
    }

    @Test
    void findCategoryCouponList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto()), pageRequest, 10);

        given(categoryCouponRepository.findCategoryCouponList(any())).willReturn(page);

        Page<AdminCouponListResponseDto> results = categoryCouponService.findCategoryCouponList(pageRequest.previousOrFirst());

        assertThat(results).hasSize(page.getContent().size());

    }
}