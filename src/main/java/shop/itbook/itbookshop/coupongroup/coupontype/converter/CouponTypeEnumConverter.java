package shop.itbook.itbookshop.coupongroup.coupontype.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
@Convert
public class CouponTypeEnumConverter implements AttributeConverter<CouponTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(CouponTypeEnum couponTypeEnum) {
        return couponTypeEnum.getCouponType();
    }

    @Override
    public CouponTypeEnum convertToEntityAttribute(String s) {
        return CouponTypeEnum.stringToEnum(s);
    }
}
