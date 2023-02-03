package shop.itbook.itbookshop.coupongroup.coupontype.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.service.CouponTypeService;
import shop.itbook.itbookshop.coupongroup.coupontype.exception.CouponTypeNotFoundException;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponTypeServiceImpl implements CouponTypeService {

    private final CouponTypeRepository couponTypeRepository;

    @Override
    public CouponType findCouponType(String couponTypeName) {
        return couponTypeRepository.findByCouponTypeName(couponTypeName).orElseThrow(
            CouponTypeNotFoundException::new);
    }
}
