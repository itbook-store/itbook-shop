package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy;

import static org.assertj.core.api.Assertions.assertThat;

import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public class PointIncreaseDecreaseContentDummy {

    public static PointIncreaseDecreaseContent getOrderIncreasePointIncreaseDecreaseContent() {

        return new PointIncreaseDecreaseContent(PointIncreaseDecreaseContentEnum.ORDER_INCREASE,
            false);
    }

    public static PointIncreaseDecreaseContent getOrderDecreasePointIncreaseDecreaseContent() {

        return new PointIncreaseDecreaseContent(PointIncreaseDecreaseContentEnum.ORDER_DECREASE,
            true);
    }
}