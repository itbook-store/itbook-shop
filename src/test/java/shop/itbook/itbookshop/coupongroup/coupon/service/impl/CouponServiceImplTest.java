package shop.itbook.itbookshop.coupongroup.coupon.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.UnableToCreateCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.exception.CouponTypeNotFoundException;
import shop.itbook.itbookshop.coupongroup.coupontype.service.CouponTypeService;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CouponServiceImpl.class)
class CouponServiceImplTest {

    @Autowired
    CouponService couponService;

    @MockBean
    CategoryCouponService categoryCouponService;

    @MockBean
    CouponRepository couponRepository;

    @MockBean
    CouponTypeService couponTypeService;

    @MockBean
    CouponIssueService couponIssueService;


    Coupon percentDummyCoupon;

    CouponRequestDto couponRequestDto;
    @BeforeEach
    public void setup(){
        percentDummyCoupon = CouponDummy.getPercentCoupon();
        percentDummyCoupon.setCouponType(new CouponType(0, CouponTypeEnum.NORMAL_COUPON));
        couponRequestDto = new CouponRequestDto();
        ReflectionTestUtils.setField(couponRequestDto, "name", "쿠폰이름");

    }
    @Test
    void addCoupon() {
        //given
        percentDummyCoupon.setCouponNo(1L);

        ReflectionTestUtils.setField(couponRequestDto, "couponType", "일반쿠폰");
        ReflectionTestUtils.setField(couponRequestDto, "couponCreatedAt", LocalDateTime.now().toString());
        ReflectionTestUtils.setField(couponRequestDto, "couponExpiredAt", LocalDateTime.now().toString());


        given(couponRepository.save(any(Coupon.class)))
            .willReturn(percentDummyCoupon);

        //when
        Long actual = couponService.addCoupon(couponRequestDto);

        //then
        assertThat(actual).isEqualTo(percentDummyCoupon.getCouponNo());

    }

    @Test
    void deleteCoupon() {

        couponService.deleteCoupon(0L);
        verify(couponRepository).deleteById(0L);
    }

    @Test
    void findByCouponResponseDto() {
        //given
        ReflectionTestUtils.setField(couponRequestDto, "code", percentDummyCoupon.getCode());
        ReflectionTestUtils.setField(couponRequestDto, "couponCreatedAt", LocalDateTime.now().toString());
        ReflectionTestUtils.setField(couponRequestDto, "couponExpiredAt", LocalDateTime.now().toString());

        given(couponRepository.findCouponByCode(anyString()))
            .willReturn(Optional.of(percentDummyCoupon));

        //when
        CouponResponseDto actual = couponService.findByCouponResponseDto(percentDummyCoupon.getCode());

        //then
        assertThat(actual.getCode()).isEqualTo(percentDummyCoupon.getCode());

    }

    @Test
    void findByCouponList(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto()), pageRequest, 10);

        given(couponRepository.findCouponList(any())).willReturn(page);

        Page<AdminCouponListResponseDto> results = couponService.findByCouponList(pageRequest.previousOrFirst());

        assertThat(results).hasSize(page.getContent().size());
    }

    @Test
    void findByCouponAtCouponTypeList_success(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto()), pageRequest, 10);

        given(couponRepository.findByCouponAtCouponTypeList(any(),any())).willReturn(page);

        Page<AdminCouponListResponseDto> results = couponService.findByCouponAtCouponTypeList(pageRequest.previousOrFirst(), CouponTypeEnum.BIRTHDAY_COUPON.getCouponType());

        assertThat(results).hasSize(page.getContent().size());
    }

    @Test
    void findByCouponAtCouponTypeList_fail(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto()), pageRequest, 10);

        given(couponRepository.findByCouponAtCouponTypeList(any(),any())).willReturn(page);

        assertThatThrownBy(() -> couponService.findByCouponAtCouponTypeList(pageRequest.previousOrFirst(), "아무거나"))
            .isInstanceOf(CouponTypeNotFoundException.class);
    }

    @Test
    void findByCouponEntity_success(){
        Coupon coupon = new Coupon();
        coupon.setCouponNo(1L);
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        Coupon result = couponService.findByCouponEntity(coupon.getCouponNo());

        assertThat(result.getCouponNo()).isEqualTo(coupon.getCouponNo());
    }

    @Test
    void useCoupon_success() throws UnableToCreateCouponException {
        Coupon coupon = new Coupon();
        coupon.setCouponNo(1L);
        coupon.setIssuedQuantity(1);
        coupon.setTotalQuantity(2);
        given(couponRepository.save(any())).willReturn(coupon);

        Coupon result = couponService.useCoupon(coupon);

        assertThat(result.getIssuedQuantity()).isEqualTo(2);
    }


    @Test
    void useCoupon_success2() throws UnableToCreateCouponException {
        Coupon coupon = new Coupon();
        coupon.setCouponNo(1L);
        coupon.setIssuedQuantity(1);
        coupon.setTotalQuantity(0);
        given(couponRepository.save(any())).willReturn(coupon);

        Coupon result = couponService.useCoupon(coupon);

        assertThat(result.getIssuedQuantity()).isEqualTo(2);
    }

    @Test
    void useCoupon_fail() throws UnableToCreateCouponException {
        Coupon coupon = new Coupon();
        coupon.setCouponNo(1L);
        coupon.setIssuedQuantity(1);
        coupon.setTotalQuantity(1);

        assertThatThrownBy(() -> couponService.useCoupon(coupon))
            .isInstanceOf(UnableToCreateCouponException.class);
    }

    @Test
    void findByAvailableCouponByCouponType_success() {

        List<Coupon> couponList = List.of(new Coupon(), new Coupon());
        given(couponRepository.findByAvailableCouponByCouponType(any())).willReturn(couponList);

        List<Coupon> results = couponService.findByAvailableCouponByCouponType(CouponTypeEnum.BIRTHDAY_COUPON.getCouponType());

        assertThat(results).hasSize(couponList.size());
    }

    @Test
    void findByAvailableCouponByCouponType_fail(){
        List<Coupon> couponList = List.of(new Coupon(), new Coupon());

        given(couponRepository.findByAvailableCouponByCouponType(any())).willReturn(couponList);

        assertThatThrownBy(() -> couponService.findByAvailableCouponByCouponType("아무거나"))
            .isInstanceOf(CouponTypeNotFoundException.class);
    }

    @Test
    void findByAvailableCouponDtoByCouponType_success(){

        List<AdminCouponListResponseDto>  couponList = List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto());
        given(couponRepository.findByAvailableCouponDtoByCouponType(any())).willReturn(couponList);

        List<AdminCouponListResponseDto> results = couponService.findByAvailableCouponDtoByCouponType(CouponTypeEnum.BIRTHDAY_COUPON.getCouponType());

        assertThat(results).hasSize(couponList.size());
    }

    @Test
    void findByAvailableCouponDtoByCouponType_fail(){
        List<AdminCouponListResponseDto>  couponList = List.of(new AdminCouponListResponseDto(), new AdminCouponListResponseDto());

        given(couponRepository.findByAvailableCouponDtoByCouponType(any())).willReturn(couponList);

        assertThatThrownBy(() -> couponService.findByAvailableCouponByCouponType("아무거나"))
            .isInstanceOf(CouponTypeNotFoundException.class);    }

}