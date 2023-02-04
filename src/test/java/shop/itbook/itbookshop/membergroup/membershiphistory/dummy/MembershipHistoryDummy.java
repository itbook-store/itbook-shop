package shop.itbook.itbookshop.membergroup.membershiphistory.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.membershiphistory.entity.MembershipHistory;

/**
 * @author 노수연
 * @since 1.0
 */
public class MembershipHistoryDummy {

    public static MembershipHistory getMembershipHistory() {
        return MembershipHistory.builder().monthlyUsageAmount(120_000L).membershipHistoryCreatedAt(
            LocalDateTime.now()).build();
    }
}
