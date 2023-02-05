package shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository.OrderCancelIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCancelIncreasePointHistoryServiceImpl
    implements OrderCancelIncreasePointHistoryService {

    private final PointHistoryService pointHistoryService;
    private final OrderCancelIncreasePointHistoryRepository
        orderCancelIncreasePointHistoryRepository;

    @Override
    public OrderCancelIncreasePointHistory savePointHistoryAboutOrderCancelIncrease(Member member,
                                                                                    Order order,
                                                                                    Long pointToApply) {
        PointHistory savedPointHistory =
            pointHistoryService.getSavedIncreasePointHistory(member, pointToApply,
                PointIncreaseDecreaseContentEnum.ORDER_CANCEL);

        OrderCancelIncreasePointHistory orderCancelIncreasePointHistory =
            new OrderCancelIncreasePointHistory(savedPointHistory.getPointHistoryNo(), order);

        return orderCancelIncreasePointHistoryRepository.save(orderCancelIncreasePointHistory);
    }
}
