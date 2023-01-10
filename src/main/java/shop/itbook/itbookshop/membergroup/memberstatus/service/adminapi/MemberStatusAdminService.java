package shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.request.MemberStatusRequestDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseProjectionDto;

/**
 * 멤버 상태 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusAdminService {

    /**
     * 특정 memberStatusNo로 멤버 상태 조회 기능을 담당하는 메서드입니다.
     *
     * @param memberStatusNo memberStatusNo로 테이블에서 멤버 상태를 찾습니다.
     * @return MemberStatusResponseDto를 반환합니다.
     * @author 노수연
     */
    MemberStatusResponseDto findMemberStatus(Long memberStatusNo);

    /**
     * 모든 멤버 상태 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @return memberStatusResponseProjectDto를 반환합니다.
     * @author 노수연
     */
    List<MemberStatusResponseProjectionDto> findMemberStatusList();

    /**
     * 멤버 상태를 추가하는 로직을 담당하는 메서드입니다.
     *
     * @param requestDto 멤버 상태 가져오는 dto를 받아와서 테이블에 멤버 상태를 추가합니다.
     * @return 추가된 멤버 상태의 멤버상태번호를 반환합니다.
     * @author 노수연
     */
    Long addMemberStatus(MemberStatusRequestDto requestDto);

    /**
     * 특정 멤버상태를 수정하는 로직을 담당하는 메서드입니다.
     *
     * @param memberStatusNo 멤버 상태 번호로 테이블에서 해당 멤버 상태를 찾습니다.
     * @param requestDto     멤버 상태 가져오는 dto를 받아와서 테이블에 멤버 상태를 수정합니다.
     * @author 노수연
     */
    void modifyMemberStatus(Long memberStatusNo, MemberStatusRequestDto requestDto);

    /**
     * 특정 멤버 상태를 삭제하는 로직을 담당한 메서드입니다.
     *
     * @param memberStatusNo 멤버 상태 번호로 테이블에서 해당 멤버 상태를 찾아 삭제합니다.
     * @author 노수연
     */
    void removeMemberStatus(Long memberStatusNo);
}
