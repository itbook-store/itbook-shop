package shop.itbook.itbookshop.pointgroup.pointhistory.dto.response;

import java.time.LocalDateTime;
import javax.persistence.Convert;
import lombok.Getter;
import lombok.Setter;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.converter.PointIncreaseDecreaseContentEnumConverter;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class PointHistoryListDto {
    private Long memberNo;
    private String memberId;
    private String memberName;
    private Long pointHistoryNo;
    private Boolean isDecrease;
    private Long increaseDecreasePoint;
    private String content;
    private Long remainedPoint;
    private LocalDateTime historyCreatedAt;

}
