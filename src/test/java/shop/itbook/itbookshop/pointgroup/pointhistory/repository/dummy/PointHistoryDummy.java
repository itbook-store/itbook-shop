package shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy;

import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;

/**
 * @author 최겸준
 * @since 1.0
 */
public class PointHistoryDummy {

    public static final PointHistory getPointHistory() {
        PointHistory pointHistory =
            new PointHistory();

        pointHistory.setIncreaseDecreasePoint(1000);
        pointHistory.setRemainedPoint(5000);

        return pointHistory;
    }
}
