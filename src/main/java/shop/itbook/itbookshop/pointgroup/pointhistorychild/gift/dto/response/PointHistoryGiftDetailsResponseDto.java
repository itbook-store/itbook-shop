package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryGiftDetailsResponseDto {
    private String mainMemberId;
    private String mainMemberName;
    private String subMemberId;
    private Long point;
    private Long remainedPoint;
    private LocalDateTime historyCreatedAt;
    private Boolean isDecrease;
}
