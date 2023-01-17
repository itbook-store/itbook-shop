package shop.itbook.itbookshop.common.health;

import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로드 밸런서 헬스 체크를 위한 컨트롤러입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RestController
public class HealthController {

    private Boolean isOk = Boolean.TRUE;
    private static final Boolean OK = Boolean.TRUE;
    private static final Boolean INTERNAL = Boolean.FALSE;

    private static final String OK_MESSAGE = "\"result\" : \"now server status is OK 200\"";
    private static final String INTERNAL_MESSAGE =
        "\"result\" : \"now server status is INTERNAL 500\"";


    @GetMapping("/monitor/l7check")
    public ResponseEntity<Void> health() {

        if (Boolean.TRUE.equals(isOk)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping(value = "/monitor/l7check")
    public ResponseEntity<String> healthStatusModify(
        @RequestParam(required = false) String healthStatus) {

        if (Objects.isNull(healthStatus)) {
            this.isOk = OK;
            return ResponseEntity.ok(OK_MESSAGE);
        }

        this.isOk = INTERNAL;
        return ResponseEntity.ok(INTERNAL_MESSAGE);
    }
}
