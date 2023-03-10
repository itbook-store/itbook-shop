package shop.itbook.itbookshop.membergroup.memberstatus.transfer;

import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * MemberStatus 엔티티와 dto 간의 변환을 작성한 transfer 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberStatusTransfer {

    private MemberStatusTransfer() {
    }

    /**
     * MemberStatus 엔티티를 dto로 변경하는 메서드입니다.
     *
     * @param memberStatus dto로 변경하기 위한 엔티티 클래스입니다.
     * @return 멤버상태 dto를 만들어서 반환합니다.
     * @author 노수연
     */
    public static MemberStatusResponseDto entityToDto(MemberStatus memberStatus) {
        return MemberStatusResponseDto.builder()
            .memberStatusName(memberStatus.getMemberStatusEnum().getMemberStatus()).build();
    }

    public static MemberStatus dtoToEntity(MemberStatusResponseDto memberStatusResponseDto) {
        return MemberStatus.builder().memberStatusNo(memberStatusResponseDto.getMemberStatusNo())
            .memberStatusEnum(
                MemberStatusEnum.stringToEnum(memberStatusResponseDto.getMemberStatusName())
            ).build();
    }
}
