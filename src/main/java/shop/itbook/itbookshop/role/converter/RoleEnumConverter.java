package shop.itbook.itbookshop.role.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * @author 강명관
 * @since 1.0
 */
@Convert
public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {

    @Override
    public String convertToDatabaseColumn(RoleEnum roleEnum) {
        return roleEnum.getRoleName();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String s) {
        return RoleEnum.stringToEnum(s);
    }
}
