package shop.itbook.itbookshop.coupongroup.categorycoupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryCouponServiceImpl implements CategoryCouponService {

    private final CategoryCouponRepository categoryCouponRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long addCategoryCoupon(CategoryCouponRequestDto couponRequestDto) {

        Category category = categoryRepository.findById(couponRequestDto.getCategoryNo())
            .orElseThrow(CategoryNotFoundException::new);
        CategoryCoupon categoryCoupon =
            new CategoryCoupon(couponRequestDto.getCouponNo(), category);
        return categoryCouponRepository.save(categoryCoupon).getCouponNo();
    }

    @Override
    @Transactional
    public void deleteCategoryCoupon(Long couponNo) {

        categoryCouponRepository.deleteById(couponNo);
    }

    @Override
    public Page<CouponListResponseDto> findCategoryCouponList(Pageable pageable) {

        return categoryCouponRepository.findCategoryCouponList(pageable);
    }

}
