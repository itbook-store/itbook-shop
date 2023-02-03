package shop.itbook.itbookshop.coupongroup.couponissue.eventlistner.eventhandler;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.itbook.itbookshop.coupongroup.couponissue.eventlistner.SignedUpEvent;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponIssueEvent {

    private final CouponIssueService couponIssueService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = AFTER_COMMIT, fallbackExecution = true)
    public void handleCouponIssueEvent(SignedUpEvent event) {
        couponIssueService.addCouponIssueByWelcomeCoupon(event.getMember());
    }
}
