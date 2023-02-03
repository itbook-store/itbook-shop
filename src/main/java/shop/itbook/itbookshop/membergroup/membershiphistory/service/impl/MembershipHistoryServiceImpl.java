package shop.itbook.itbookshop.membergroup.membershiphistory.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookshop.membergroup.membershiphistory.repository.MembershipHistoryRepository;
import shop.itbook.itbookshop.membergroup.membershiphistory.service.MembershipHistoryService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MembershipHistoryServiceImpl implements MembershipHistoryService {

    private final MembershipHistoryRepository membershipHistoryRepository;

    @Override
    public List<MembershipHistoryResponseDto> getMembershipHistories(String memberId) {

        return membershipHistoryRepository.findByMemberId(memberId);
    }
}
