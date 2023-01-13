package shop.itbook.itbookshop.membergroup.memberstatusenum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author 노수연
 * @since 1.0
 */
@Converter
public class MemberStatusEnumConverter implements AttributeConverter<MemberStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(MemberStatusEnum memberStatusEnum) {
        return memberStatusEnum.getMemberStatus();
    }

    @Override
    public MemberStatusEnum convertToEntityAttribute(String dbData) {
        return MemberStatusEnum.stringToEnum(dbData);
    }
}
