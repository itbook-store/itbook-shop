package shop.itbook.itbookshop.memberinquirygroup.inquirytypeenum;

import lombok.Getter;

/**
 * 문의유형에 대한 enum입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
public enum InquiryTypeEnum {
    GENERAL("일반문의"), COMPLAINT("불만접수");

    private String inquiryType;

    InquiryTypeEnum(String inquiryType) {
        this.inquiryType = inquiryType;
    }
}
