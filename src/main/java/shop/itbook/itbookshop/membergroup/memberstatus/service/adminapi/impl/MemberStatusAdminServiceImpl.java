package shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.request.MemberStatusRequestDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseProjectionDto;
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
    public MemberStatusResponseDto findMemberStatus(Long memberStatusNo) {
        return null;
    }

    @Override
    public List<MemberStatusResponseProjectionDto> findMemberStatusList() {
        return null;
    }

    @Override
    @Transactional
    public Long addMemberStatus(MemberStatusRequestDto requestDto) {
        return null;
    }

    @Override
    @Transactional
    public void modifyMemberStatus(Long memberStatusNo, MemberStatusRequestDto requestDto) {

    }

    @Override
    @Transactional
    public void removeMemberStatus(Long memberStatusNo) {

    }
}
