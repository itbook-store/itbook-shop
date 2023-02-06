package shop.itbook.itbookshop.coupongroup.coupon.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.exception.CouponNotFoundException;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.coupon.transfer.CouponTransfer;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.service.CouponTypeService;

/**
 * 쿠폰의
 *
 * @author 송다혜
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponTypeService couponTypeService;
    private final CategoryCouponService categoryCouponService;

    @Override
    @Transactional
    public Long addCoupon(CouponRequestDto couponRequestDto) {

        Coupon coupon = CouponTransfer.dtoToEntity(couponRequestDto);
        coupon.setCode(UUID.randomUUID().toString());
        coupon.setCouponType(couponTypeService.findCouponType(couponRequestDto.getCouponType()));

        coupon = couponRepository.save(coupon);
        if (!Objects.isNull(couponRequestDto.getCategoryNo())) {
            categoryCouponService.addCategoryCoupon(
                new CategoryCouponRequestDto(coupon.getCouponNo(),
                    couponRequestDto.getCategoryNo()));
        }

        return coupon.getCouponNo();
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

    @Override
    public Page<CouponListResponseDto> findByCouponAtCouponTypeList(Pageable pageable, String couponType) {
        CouponTypeEnum couponTypeEnum = CouponTypeEnum.stringToEnum(couponType);

        return couponRepository.findByCouponAtCouponTypeList(pageable, couponTypeEnum);
    }

    @Override
    public Coupon findByCouponEntity(Long couponNo) {
        return couponRepository.findById(couponNo).orElseThrow(CouponNotFoundException::new);
    }

    @Override
    public List<Coupon> findByAvailableCouponByCouponType(String couponType) {

        CouponTypeEnum couponTypeEnum = CouponTypeEnum.stringToEnum(couponType);
        return couponRepository.findByAvailableCouponByCouponType(couponTypeEnum);
    }
}
