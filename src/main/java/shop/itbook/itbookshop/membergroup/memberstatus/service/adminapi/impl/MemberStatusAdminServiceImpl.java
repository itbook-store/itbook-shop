package shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.exception.MemberStatusNotFound;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;

/**
 * 멤버 상태 서비스 인터페이스를 상속받아 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberStatusAdminServiceImpl implements MemberStatusAdminService {

    private final MemberStatusRepository memberStatusRepository;

    @Override
    public MemberStatusResponseDto findMemberStatus(String memberStatusName) {
        return memberStatusRepository.querydslFindByName(memberStatusName)
            .orElseThrow(MemberStatusNotFound::new);
    }

    @Override
    public MemberStatusResponseDto findMemberStatusWithMemberStatusNo(int memberStatusNo) {
        return memberStatusRepository.querydslFindByNo(memberStatusNo)
            .orElseThrow(MemberStatusNotFound::new);
    }

    @Override
    public List<MemberStatusResponseDto> findMemberStatusList() {
        return memberStatusRepository.querydslFindAll();
    }

}
