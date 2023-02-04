package shop.itbook.itbookshop.membergroup.memberdestination.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.membergroup.memberdestination.transfer.MemberDestinationTransfer;

/**
 * MemberDestinationService 인터페이스의 구현 클래스
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberDestinationServiceImpl implements MemberDestinationService {

    private final MemberDestinationRepository memberDestinationRepository;

    @Override
    public List<MemberDestinationResponseDto> findMemberDestinationResponseDtoByMemberNo(
        Long memberNo) {

        return memberDestinationRepository.findAllByMember_MemberNo(memberNo).stream().map(
            MemberDestinationTransfer::entityToDto).collect(Collectors.toList());
    }
}
