package shop.itbook.itbookshop.couponcoverage.entity;

/**
 * 쿠폰 적용 범위를 나타내는 이늄입니다
 *
 * @author 송다혜
 * @since 1.0
 */
public enum CouponCoverageEnum {

    ALL("전체"),
    CATEGORY("카테고리"),
    INDIVIDUAL_PRODUCTS("개별상품");

    private String couponCoverage;

    CouponCoverageEnum(String couponCoverage) {
        this.couponCoverage = couponCoverage;
    }
}
