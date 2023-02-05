package shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;


/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderCancelIncreasePointHistoryService {
    OrderCancelIncreasePointHistory savePointHistoryAboutOrderCancelIncrease(Member member,
                                                                             Order order,
                                                                             Long pointToApply);
}
