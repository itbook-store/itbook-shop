package shop.itbook.itbookshop.common.health;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로드 밸런서 헬스 체크를 위한 컨트롤러입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RestController
public class HealthController {

    private Boolean isHealth = Boolean.TRUE;

    @GetMapping("/monitor/l7check")
    public ResponseEntity<Void> health() {

        if (Boolean.TRUE.equals(isHealth)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/monitor/l7check")
    public ResponseEntity<Void> healthStatusModify() {
        isHealth = !isHealth;
        return ResponseEntity.ok().build();
    }
}
