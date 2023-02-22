package shop.itbook.itbookshop.fileservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 오브젝트 스토리지 토큰에 대한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Data
public class ItBookObjectStorageToken implements Serializable {
    private Access access;

    @Data
    public static class Access implements Serializable {
        private Token token;

        @NoArgsConstructor
        @Getter
        @Setter
        public static class Token implements Serializable {
            private String id;

            private LocalDateTime expires;
        }
    }
}


