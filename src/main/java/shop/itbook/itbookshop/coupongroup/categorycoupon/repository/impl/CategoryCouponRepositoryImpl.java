package shop.itbook.itbookshop.coupongroup.categorycoupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response.CategoryCouponListDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.QCategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CustomCategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CategoryCouponRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCategoryCouponRepository {
    public CategoryCouponRepositoryImpl() {
        super(CategoryCoupon.class);
    }


}
