package shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum;

import lombok.Getter;

/**
 * 쿠폰 사용 여부와 상태종류를 나타내는 엔터티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
public enum UsageStatusEnum {

    COMPLETED("사용완료"),
    AVAILABLE("사용가능");

    private final String usageStatus;

    UsageStatusEnum(String usageStatus) {
        this.usageStatus = usageStatus;
    }

    public static UsageStatusEnum stringToEnum(String s) {
        for (UsageStatusEnum value : UsageStatusEnum.values()) {
            if (value.getUsageStatus().equals(s)) {
                return value;
            }
        }
        return null;
    }
}
