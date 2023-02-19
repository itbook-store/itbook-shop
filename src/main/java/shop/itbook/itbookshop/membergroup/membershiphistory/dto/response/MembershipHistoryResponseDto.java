package shop.itbook.itbookshop.membergroup.membershiphistory.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipHistoryResponseDto {

    private Long membershipHistoryNo;
    private Long monthlyUsageAmount;
    private LocalDateTime membershipHistoryCreatedAt;
    private Long memberNo;
    private Integer membershipNo;
    private String membershipGrade;
    private Integer memberStatusNo;
    private String nickname;
    private String name;
    private Boolean isMan;
    private LocalDateTime birth;
    private String phoneNumber;
    private String email;
    private LocalDateTime memberCreatedAt;

}
