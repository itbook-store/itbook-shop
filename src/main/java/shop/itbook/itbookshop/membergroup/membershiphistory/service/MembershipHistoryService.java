package shop.itbook.itbookshop.membergroup.membershiphistory.service;

import java.util.List;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MembershipHistoryService {

    List<MembershipHistoryResponseDto> getMembershipHistories(String memberId);

}
