package shop.itbook.itbookshop.membergroup.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdBlockResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberOauthLoginResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
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
     * memberId로 특정 멤버 정보를 찾는 메서드입니다.
     * 프론트 서버에서 관리자가 특정 회원의 정보를 열람할 때 비밀번호를 제외한 모든 필드들의 값을 볼 수 있게 합니다.
     *
     * @param memberId memberId로 테이블에서 멤버를 찾습니다.
     * @return 멤버 Dto로 반환합니다.
     * @author 노수연
     */
    Optional<MemberExceptPwdResponseDto> findByMemberId(String memberId);

    /**
     * memberId로 특정 멤버 정보를 찾는 메서드입니다.
     * 프론트 서버에서 유저가 비밀번호를 포함하여 자신의 모든 개인정보를 조회할 수 있도록 멤버 엔티티의 모든 필드들을 dto를 통해 반환합니다.
     *
     * @param memberId memberId로 테이블에서 멤버를 찾습니다.
     * @return 멤버 Dto로 반환합니다.
     * @author 노수연
     */
    Optional<MemberResponseDto> findByMemberIdAllInfo(String memberId);

    /**
     * 멤버를 수정할 때 특정 멤버 아이디로 멤버 엔티티를 불러와 dirty checking 일어날 수 있도록
     * Optional wrapper 클래스로 멤버 엔티티를 감싸 반환합니다.
     *
     * @param memberId memberId로 테이블에서 멤버를 찾습니다.
     * @return Optional wrapper 클래스로 멤버 엔티티를 감싸 반환합니다.
     * @author 노수연
     */
    Optional<Member> findByMemberIdReceiveMember(String memberId);

    /**
     * 모든 회원 리스트를 가져오는 메서드입니다.
     *
     * @return 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    List<MemberExceptPwdResponseDto> findMemberList();

    boolean existsByEmailAndIsSocial(String email);

    boolean existsByMemberIdAndIsSocial(String email);

    Optional<MemberOauthLoginResponseDto> findOauthInfoByMemberId(String memberId);

    /**
     * 회원 아이디로 로그인에 필요한 정보를 가져오는 메서드 입니다.
     *
     * @param memberId the member id
     * @return the member auth response dto
     * @author 강명관
     */
    Optional<MemberAuthInfoResponseDto> findAuthInfoByMemberId(String memberId);


    /**
     * 프론트 관리자 페이지에서 멤버 아이디로 검색하였을 때
     * 테이블에서 입력된 검색어 기준으로 데이터들을 가져옵니다.
     *
     * @param memberId 검색한 단어가 포함되는 멤버 아이디를 찾습니다.
     * @return 검색된 단어가 포함된 멤버 아이디가 있는 데이터 리스트들을 반환합니다.
     * @author 노수연
     */
    List<MemberExceptPwdResponseDto> findMemberListByMemberId(String memberId);

    List<MemberExceptPwdResponseDto> findMemberListByNickname(String nickname);

    List<MemberExceptPwdResponseDto> findMemberListByName(String name);

    List<MemberExceptPwdResponseDto> findMemberListByPhoneNumber(String phoneNumber);

    List<MemberExceptPwdResponseDto> findMemberListBySearchWord(String searchWord);

    MemberExceptPwdBlockResponseDto findBlockMemberByMemberId(String memberId);
}
