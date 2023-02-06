package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface GiftIncreaseDecreasePointHistoryService {

    GiftIncreaseDecreasePointHistory savePointHistoryAboutGiftDecreaseAndIncrease(Member sender,
                                                                                  Member receiver,
                                                                                  Long pointToApply);
}
