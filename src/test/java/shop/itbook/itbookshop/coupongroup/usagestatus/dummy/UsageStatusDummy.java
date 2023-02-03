package shop.itbook.itbookshop.coupongroup.usagestatus.dummy;

import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
public class UsageStatusDummy {
    public static UsageStatus getUsageStatus(){
        return new UsageStatus(0, UsageStatusEnum.AVAILABLE);
    }
}
