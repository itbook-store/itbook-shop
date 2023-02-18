package shop.itbook.itbookshop.coupongroup.usagestatus.dummy;

import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
public class UsageStatusDummy {

    private UsageStatusDummy() {

    }

    public static UsageStatus getAvailableUsageStatus() {
        return new UsageStatus(0, UsageStatusEnum.AVAILABLE);
    }

    public static UsageStatus getCompletedUsageStatus() {
        return new UsageStatus(0, UsageStatusEnum.COMPLETED);
    }

    public static UsageStatus getUsageStatus() {
        return new UsageStatus(1, UsageStatusEnum.AVAILABLE);
    }
}
