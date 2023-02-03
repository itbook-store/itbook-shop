package shop.itbook.itbookshop.coupongroup.usagestatus.cunverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
@Convert
public class UsageStatusEnumConverter implements AttributeConverter<UsageStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(UsageStatusEnum usageStatusEnum) {
        return usageStatusEnum.getUsageStatus();
    }

    @Override
    public UsageStatusEnum convertToEntityAttribute(String s) {
        return UsageStatusEnum.stringToEnum(s);
    }

}
