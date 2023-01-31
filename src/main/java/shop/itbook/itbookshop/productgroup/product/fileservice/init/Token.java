package shop.itbook.itbookshop.productgroup.product.fileservice.init;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Getter
@Setter
public class Token {
    private String id;

    //    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expires;
}
