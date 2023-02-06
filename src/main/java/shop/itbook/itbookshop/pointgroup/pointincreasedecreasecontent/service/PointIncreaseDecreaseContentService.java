package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service;

import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointIncreaseDecreaseContentService {
    PointIncreaseDecreaseContent findPointIncreaseDecreaseContentThroughContentEnum(
        PointIncreaseDecreaseContentEnum coupon);
}
