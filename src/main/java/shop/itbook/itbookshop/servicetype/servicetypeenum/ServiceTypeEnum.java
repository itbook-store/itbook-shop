package shop.itbook.itbookshop.servicetype.servicetypeenum;

import lombok.Getter;

/**
 * 회원서비스 테이블에 들어갈 service type (서비스 유형)을 열거한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum ServiceTypeEnum {

    NOTICE("공지사항"), FAQ("자주묻는질문"), CUSTOMER_CENTER("고객센터");

    private String serviceTypeName;

    ServiceTypeEnum(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }
}
