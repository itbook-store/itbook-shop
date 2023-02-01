package shop.itbook.itbookshop.coupongroup.coupon.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.exception.CouponNotFoundException;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.coupon.transfer.CouponTransfer;

/**
 * 쿠폰의
 * @author 송다혜
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public Long addCoupon(CouponRequestDto couponRequestDto) {

        Coupon coupon = CouponTransfer.dtoToEntity(couponRequestDto);
        coupon.setCode(UUID.randomUUID().toString());

        return couponRepository.save(coupon).getCouponNo();
    }

    @Override
    @Transactional
    public void deleteCoupon(Long couponNo) {

        couponRepository.deleteById(couponNo);
    }

    @Override
    public CouponResponseDto findByCouponResponseDto(String code) {

        return CouponTransfer.entityToDto(couponRepository.findCouponByCode(code)
            .orElseThrow(CouponNotFoundException::new));
    }

    @Override
    public Page<CouponListResponseDto> findByCouponList(Pageable pageable) {
        return couponRepository.findCouponList(pageable);
    }
}
