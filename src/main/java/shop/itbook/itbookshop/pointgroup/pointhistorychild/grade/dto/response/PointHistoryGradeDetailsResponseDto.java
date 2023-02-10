package shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryGradeDetailsResponseDto {

    private String memberId;
    private String memberName;
    private Integer membershipNo;
    private String membershipGrade;
    private Long membershipStandardAmount;
    private Long membershipPoint;
    private Long point;
    private Long remainedPoint;
    private LocalDateTime historyCreatedAt;
    private Boolean isDecrease;
}
