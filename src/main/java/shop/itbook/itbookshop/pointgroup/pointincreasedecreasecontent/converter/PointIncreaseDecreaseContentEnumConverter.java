package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
@Converter
public class PointIncreaseDecreaseContentEnumConverter
    implements AttributeConverter<PointIncreaseDecreaseContentEnum, String> {

    @Override
    public String convertToDatabaseColumn(
        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointIncreaseDecreaseContentEnum.getStatusName();
    }

    @Override
    public PointIncreaseDecreaseContentEnum convertToEntityAttribute(String statusName) {
        return PointIncreaseDecreaseContentEnum.stringToEnum(statusName);
    }
}
