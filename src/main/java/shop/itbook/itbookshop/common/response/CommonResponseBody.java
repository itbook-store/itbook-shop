package shop.itbook.itbookshop.common.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 모든 반환타입에 적용할 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonResponseBody<T> {
    private CommonHeader header;
    private T result;

    /**
     * 개발자가 확인해야할 값들을 저장하는 클래스입니다.
     *
     * @author 최겸준
     * @since 1.0
     */
    @AllArgsConstructor
    @Getter
    public static class CommonHeader {
        private boolean isSuccessful;
        private Integer resultCode;
        private String resultMessage;
    }
}
