package shop.itbook.itbookshop.usagestatus.entity;

/**
 * 쿠폰 사용 여부와 상태종류를 나타내는 엔터티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public enum UsageStatusEnum {

    COMPLETED("사용완료"),
    AVAILABLE("사용가능");

    private String usageStatus;

    UsageStatusEnum(String usageStatus) {
        this.usageStatus = usageStatus;
    }
}
