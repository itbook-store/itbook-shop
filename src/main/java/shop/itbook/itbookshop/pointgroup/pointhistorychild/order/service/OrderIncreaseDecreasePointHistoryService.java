package shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.OrderIncreaseDecreasePointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderIncreaseDecreasePointHistoryService {

    OrderIncreaseDecreasePointHistory savePointHistoryAboutOrderIncrease(Member member, Order order,
                                                                         Long pointToApply);

    OrderIncreaseDecreasePointHistory savePointHistoryAboutOrderDecrease(Member member, Order order,
                                                                         Long pointToApply);
}
