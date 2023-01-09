package shop.itbook.itbookshop.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 강명관
 * @since 1.0
 */

@RestController
public class Controller {

    @GetMapping("/api/test")
    public String test() {
        return "test";
    }


}
