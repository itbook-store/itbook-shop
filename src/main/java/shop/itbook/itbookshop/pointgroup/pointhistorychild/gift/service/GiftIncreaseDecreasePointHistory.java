package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.OrderIncreaseDecreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface GiftIncreaseDecreasePointHistory {

    GiftIncreaseDecreasePointHistory savePointHistoryAboutGiftIncrease(Member receiver,
                                                                       Member sender,
                                                                       Long pointToApply);

    GiftIncreaseDecreasePointHistory savePointHistoryAboutGiftDecrease(Member sender,
                                                                       Member receiver,
                                                                       Long pointToApply);
}
