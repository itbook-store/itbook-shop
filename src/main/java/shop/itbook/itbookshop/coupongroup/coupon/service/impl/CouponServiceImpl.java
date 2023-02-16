package shop.itbook.itbookshop.coupongroup.coupon.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.exception.CouponNotFoundException;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.coupon.transfer.CouponTransfer;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.UnableToCreateCouponException;
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

    @Override
    @Transactional
    public Long addCoupon(CouponRequestDto couponRequestDto) {

        Coupon coupon = CouponTransfer.dtoToEntity(couponRequestDto);
        coupon.setCode(UUID.randomUUID().toString());
        coupon.setCouponType(couponTypeService.findCouponType(couponRequestDto.getCouponType()));

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
    public Page<AdminCouponListResponseDto> findByCouponList(Pageable pageable) {
        return couponRepository.findCouponList(pageable);
    }

    @Override
    public Page<AdminCouponListResponseDto> findByCouponAtCouponTypeList(Pageable pageable,
                                                                         String couponType) {
        CouponTypeEnum couponTypeEnum = CouponTypeEnum.stringToEnum(couponType);

        return couponRepository.findByCouponAtCouponTypeList(pageable, couponTypeEnum);
    }

    @Override
    public Coupon findByCouponEntity(Long couponNo) {
        return couponRepository.findById(couponNo).orElseThrow(CouponNotFoundException::new);
    }

    @Override
    @Transactional
    public Coupon useCoupon(Coupon coupon) throws UnableToCreateCouponException {
        int quantity = coupon.getIssuedQuantity();

        if (coupon.getTotalQuantity() != 0 && coupon.getTotalQuantity() == quantity) {
            throw new UnableToCreateCouponException();
        }
        coupon.setIssuedQuantity(++quantity);
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findByAvailableCouponByCouponType(String couponType) {

        CouponTypeEnum couponTypeEnum = CouponTypeEnum.stringToEnum(couponType);
        return couponRepository.findByAvailableCouponByCouponType(couponTypeEnum);
    }

    @Override
    public List<AdminCouponListResponseDto> findByAvailableCouponDtoByCouponType(String couponType) {
        CouponTypeEnum couponTypeEnum = CouponTypeEnum.stringToEnum(couponType);
        return couponRepository.findByAvailableCouponDtoByCouponType(couponTypeEnum);
    }

}
