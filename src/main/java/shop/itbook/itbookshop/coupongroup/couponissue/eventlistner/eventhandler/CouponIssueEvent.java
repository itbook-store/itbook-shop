package shop.itbook.itbookshop.coupongroup.couponissue.eventlistner.eventhandler;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.itbook.itbookshop.coupongroup.couponissue.eventlistner.SignedUpEvent;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponIssueEvent {

    private final CouponIssueService couponIssueService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = AFTER_COMMIT, fallbackExecution = true)
    public void handleCouponIssueEvent(SignedUpEvent event) {
        try {
            couponIssueService.addCouponIssueByCoupons(event.getMember().getMemberNo(),
                CouponTypeEnum.WELCOME_COUPON.getCouponType());
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
