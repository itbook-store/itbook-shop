package shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import javax.swing.Spring;
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
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.OrderIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.repository.OrderIncreaseDecreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository.OrderCancelIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.entity.ReviewIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.repository.ReviewIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.ReviewIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * @author ?????????
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderIncreaseDecreasePointHistoryServiceImpl.class)
class OrderIncreaseDecreasePointHistoryServiceImplTest {


    @Autowired
    OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;
    @MockBean
    PointHistoryService pointHistoryService;
    @MockBean
    OrderIncreaseDecreasePointHistoryRepository orderIncreaseDecreasePointHistoryRepository;

    OrderIncreaseDecreasePointHistory orderIncreaseDecreasePointHistory;

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
        orderIncreaseDecreasePointHistory =
            new OrderIncreaseDecreasePointHistory(pointHistory.getPointHistoryNo(), order);
    }

    @DisplayName("????????? ??????????????? ???????????? ??????????????????????????? ???????????? ?????????????????? ??????????????? ????????????.")
    @Test
    void savePointHistoryAboutOrderIncrease() {

        // given
        given(pointHistoryService.getSavedIncreasePointHistory(any(Member.class), anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(pointHistory);

        given(orderIncreaseDecreasePointHistoryRepository.save(
            any(OrderIncreaseDecreasePointHistory.class)))
            .willReturn(orderIncreaseDecreasePointHistory);


        // when
        OrderIncreaseDecreasePointHistory actual =
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderIncrease(member,
                order, 500L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(orderIncreaseDecreasePointHistory.getPointHistoryNo());
        assertThat(actual.getOrder().getOrderNo())
            .isEqualTo(orderIncreaseDecreasePointHistory.getOrder().getOrderNo());
    }

    @DisplayName("????????? ??????????????? ???????????? ??????????????????????????? ???????????? ?????????????????? ??????????????? ????????????.")
    @Test
    void savePointHistoryAboutOrderDecrease() {

        // given
        pointHistory.setIsDecrease(true);
        given(pointHistoryService.getSavedDecreasePointHistory(any(Member.class), anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(pointHistory);

        given(orderIncreaseDecreasePointHistoryRepository.save(
            any(OrderIncreaseDecreasePointHistory.class)))
            .willReturn(orderIncreaseDecreasePointHistory);

        // when
        OrderIncreaseDecreasePointHistory actual =
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderDecrease(member,
                order, 500L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(orderIncreaseDecreasePointHistory.getPointHistoryNo());
        assertThat(actual.getOrder().getOrderNo())
            .isEqualTo(orderIncreaseDecreasePointHistory.getOrder().getOrderNo());
    }

}