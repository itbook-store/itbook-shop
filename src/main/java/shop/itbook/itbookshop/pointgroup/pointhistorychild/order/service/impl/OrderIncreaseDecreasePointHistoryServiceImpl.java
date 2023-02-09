package shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.OrderIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.repository.OrderIncreaseDecreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderIncreaseDecreasePointHistoryServiceImpl
    implements OrderIncreaseDecreasePointHistoryService {

    private final PointHistoryService pointHistoryService;
    private final OrderIncreaseDecreasePointHistoryRepository
        orderIncreaseDecreasePointHistoryRepository;

    @Override
    @Transactional
    public OrderIncreaseDecreasePointHistory savePointHistoryAboutOrderIncrease(Member member,
                                                                                Order order,
                                                                                Long pointToApply) {

        PointHistory savedPointHistory =
            pointHistoryService.getSavedIncreasePointHistory(member, pointToApply,
                PointIncreaseDecreaseContentEnum.ORDER);

        OrderIncreaseDecreasePointHistory orderIncreaseDecreasePointHistory =
            new OrderIncreaseDecreasePointHistory(savedPointHistory.getPointHistoryNo(), order);

        return orderIncreaseDecreasePointHistoryRepository.save(orderIncreaseDecreasePointHistory);
    }

    @Override
    @Transactional
    public OrderIncreaseDecreasePointHistory savePointHistoryAboutOrderDecrease(Member member,
                                                                                Order order,
                                                                                Long pointToApply) {

        PointHistory savedPointHistory =
            pointHistoryService.getSavedDecreasePointHistory(member, pointToApply,
                PointIncreaseDecreaseContentEnum.ORDER);

        OrderIncreaseDecreasePointHistory orderIncreaseDecreasePointHistory =
            new OrderIncreaseDecreasePointHistory(savedPointHistory.getPointHistoryNo(), order);

        return orderIncreaseDecreasePointHistoryRepository.save(orderIncreaseDecreasePointHistory);
    }
}
