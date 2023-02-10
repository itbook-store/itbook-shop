package shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.CouponIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository.OrderCancelIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderCancelIncreasePointHistoryServiceImpl.class)
class OrderCancelIncreasePointHistoryServiceImplTest {

    @Autowired
    OrderCancelIncreasePointHistoryService orderCancelIncreasePointHistoryService;
    @MockBean
    PointHistoryService pointHistoryService;
    @MockBean
    OrderCancelIncreasePointHistoryRepository orderCancelIncreasePointHistoryRepository;


    OrderCancelIncreasePointHistory orderCancelIncreasePointHistory;

    Order order;

    PointHistory pointHistory;

    Member member;

    PointIncreaseDecreaseContentEnum orderCancelPointEnum;

    @BeforeEach
    void setUp() {
        orderCancelPointEnum = PointIncreaseDecreaseContentEnum.ORDER_CANCEL;

        member = MemberDummy.getMember1();
        member.setMemberNo(1L);
        pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setPointHistoryNo(1L);
        pointHistory.setMember(member);

        order = OrderDummy.getOrder();
        order.setOrderNo(1L);
        orderCancelIncreasePointHistory =
            new OrderCancelIncreasePointHistory(pointHistory.getPointHistoryNo(), order);
    }

    @DisplayName("포인트 히스토리를 저장하고 주문취소적립히스토리를 저장하는 서비스로직이 정상적으로 동작한다.")
    @Test
    void savePointHistoryAboutOrderCancelIncrease() {

        // given
        given(pointHistoryService.getSavedIncreasePointHistory(any(Member.class), anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(pointHistory);

        given(orderCancelIncreasePointHistoryRepository.save(
            any(OrderCancelIncreasePointHistory.class)))
            .willReturn(orderCancelIncreasePointHistory);


        // when
        OrderCancelIncreasePointHistory actual =
            orderCancelIncreasePointHistoryService.savePointHistoryAboutOrderCancelIncrease(member,
                order, 500L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(orderCancelIncreasePointHistory.getPointHistoryNo());
        assertThat(actual.getOrder().getOrderNo())
            .isEqualTo(orderCancelIncreasePointHistory.getOrder().getOrderNo());
    }
}