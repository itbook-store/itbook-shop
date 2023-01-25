package shop.itbook.itbookshop.membergroup.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * query dsl을 사용하기 위한 커스텀 memberRepository 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomMemberRepository {

    /**
     * memberNo로 특정 멤버 정보를 찾는 메서드입니다.
     *
     * @param memberId member id로 테이블에서 멤버를 찾습니다.
     * @return Dto로 받아와 반환합니다.
     * @author 노수연
     */
    Optional<MemberResponseProjectionDto> querydslFindByMemberId(String memberId);

    Optional<MemberResponseDto> querydslFindByMemberIdAllInfo(String memberId);

    Optional<Member> querydslFindByMemberIdToMember(String memberId);

    /**
     * 모든 회원 리스트를 가져오는 메서드입니다.
     *
     * @return 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    List<MemberResponseProjectionDto> querydslFindAll();

    /**
     * 회원 아이디로 로그인에 필요한 정보를 가져오는 메서드 입니다.
     *
     * @param memberId the member id
     * @return the member auth response dto
     * @author 강명관
     */
    MemberAuthResponseDto findAuthInfoByMemberId(String memberId);
}
