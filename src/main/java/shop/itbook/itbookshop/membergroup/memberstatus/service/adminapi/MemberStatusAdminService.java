package shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

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
     * @param memberStatusEnum memberStatusEnum으로 테이블에서 멤버 상태를 찾습니다.
     * @return MemberStatusResponseDto를 반환합니다.
     * @author 노수연
     */
    MemberStatusResponseDto findMemberStatus(MemberStatusEnum memberStatusEnum);

    MemberStatusResponseDto findMemberStatusWithMemberStatusNo(int memberStatusNo);

    /**
     * 모든 멤버 상태 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @return memberStatusResponseProjectDto를 반환합니다.
     * @author 노수연
     */
    List<MemberStatusResponseDto> findMemberStatusList();

}
