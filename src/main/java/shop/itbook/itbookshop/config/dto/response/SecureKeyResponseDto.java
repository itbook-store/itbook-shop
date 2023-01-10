package shop.itbook.itbookshop.config.dto.response;

import lombok.Getter;

/**
 * NHN Cloud Secure Key Manager 에서 Secure Key의 response을 받기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
public class SecureKeyResponseDto {
    private Header header;
    private Body body;


    /**
     * NHN Cloud Secure Key Manager json응답의 header의 구조입니다.
     *
     * @author 강명관
     * @since 1.0
     */
    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    /**
     * NHN Cloud Secure Key Manager json응답의 body의 구조입니다.
     *
     * @author 강명관
     * @since 1.0
     */
    @Getter
    public static class Body {
        private String secret;
    }
}
