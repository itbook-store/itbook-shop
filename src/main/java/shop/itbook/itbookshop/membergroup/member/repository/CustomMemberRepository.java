package shop.itbook.itbookshop.membergroup.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdBlockResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
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
     * memberNo로 특정 멤버 정보를 찾는 메서드입니다.
     * 프론트 서버에서 관리자가 특정 회원의 정보를 열람할 때 비밀번호를 제외한 모든 필드들의 값을 볼 수 있게 합니다.
     *
     * @param memberNo memberNo로 테이블에서 멤버를 찾습니다.
     * @return 멤버 Dto로 반환합니다.
     * @author 노수연
     */
    Optional<MemberExceptPwdResponseDto> findByMemberNo(Long memberNo);

    /**
     * memberNo로 특정 멤버 정보를 찾는 메서드입니다.
     * 프론트 서버에서 유저가 비밀번호를 포함하여 자신의 모든 개인정보를 조회할 수 있도록 멤버 엔티티의 모든 필드들을 dto를 통해 반환합니다.
     *
     * @param memberNo memberNo로 테이블에서 멤버를 찾습니다.
     * @return 멤버 Dto로 반환합니다.
     * @author 노수연
     */
    Optional<MemberResponseDto> findByMemberNoAllInfo(Long memberNo);


    /**
     * memberId로 특정 멤버 정보를 찾는 메서드입니다.
     * 프론트 서버에서 유저가 비밀번호를 포함하여 자신의 모든 개인정보를 조회할 수 있도록 멤버 엔티티의 모든 필드들을 dto를 통해 반환합니다.
     *
     * @param memberId memberId로 테이블에서 멤버를 찾습니다.
     * @return 멤버 Dto로 반환합니다.
     * @author 노수연
     */
    Optional<MemberResponseDto> findByMemberId(String memberId);

    /**
     * 멤버를 수정할 때 특정 멤버 아이디로 멤버 엔티티를 불러와 dirty checking 일어날 수 있도록
     * Optional wrapper 클래스로 멤버 엔티티를 감싸 반환합니다.
     *
     * @param memberNo memberNo로 테이블에서 멤버를 찾습니다.
     * @return Optional wrapper 클래스로 멤버 엔티티를 감싸 반환합니다.
     * @author 노수연
     */
    Optional<Member> findByMemberNoReceiveMember(Long memberNo);


    /**
     * 모든 작가 리스트를 가져오는 query dsl 입니다.
     *
     * @param pageable 페이징된 데이터를 받습니다.
     * @return 작가인 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findWriterList(Pageable pageable);

    /**
     * 모든 회원 리스트를 가져오는 메서드입니다.
     *
     * @param pageable 페이징된 데이터를 받습니다.
     * @return 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberList(Pageable pageable);


    /**
     * 모든 정상회원 리스트를 가져오는 메서드입니다.
     *
     * @param pageable 페이징된 데이터를 받습니다.
     * @return 정상 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findNormalMemberList(Pageable pageable);


    /**
     * 모든 차단회원 리스트를 가져오는 메서드입니다.
     *
     * @param pageable 페이징된 데이터를 받습니다.
     * @return 차단 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findBlockMemberList(Pageable pageable);

    /**
     * 탈퇴 회원 리스트를 받아옵니다.
     *
     * @param pageable 페이징된 데이터를 받습니다.
     * @return 탈퇴 회원 리스트를 받아옵니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findWithdrawMemberList(Pageable pageable);

    /**
     * 넘어온 이메일 값이 이메일 필드에 존재하고 소셜회원인지의 여부를 판단합니다
     *
     * @param email Oauth 로그인 시 넘어오는 이메일 값입니다.
     * @return 이메일 필드에 존재하고 소셜회원이면 true를 반환합니다.
     * @author 노수연
     */
    boolean existsByEmailAndIsSocial(String email);

    /**
     * 넘어온 이메일 값이 아이디 필드에 존재하고 소셜회원인지의 여부를 판단합니다.
     *
     * @param email Oauth 로그인 시 넘어오는 이메일 값입니다.
     * @return 아이디 필드에 존재하고 소셜회원이면 true를 반환합니다.
     * @author 노수연
     */
    boolean existsByMemberIdAndIsSocial(String email);

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
     * @param memberId         검색한 단어가 포함되는 멤버 아이디를 찾습니다.
     * @param memberStatusName 멤버 상태별 데이터를 가져오기 위한 파라미터 입니다.
     * @param pageable         검색된 멤버 리스트는 페이징 처리합니다.
     * @return 검색된 단어가 포함된 멤버 아이디가 있는 데이터 리스트들을 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberListByMemberId(String memberId,
                                                              String memberStatusName,
                                                              Pageable pageable);

    /**
     * 프론트 관리자 페이지에서 닉네임으로 검색하였을 때
     * 테이블에서 입력된 검색어 기준으로 데이터들을 가져옵니다.
     *
     * @param nickname         검색한 단어가 포함되는 닉네임을 찾습니다.
     * @param memberStatusName 멤버 상태별 데이터를 가져오기 위한 파라미터 입니다.
     * @param pageable         검색된 멤버 리스트는 페이징 처리합니다.
     * @return 검색된 단어가 포함된 닉네임이 있는 데이터 리스트들을 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberListByNickname(String nickname,
                                                              String memberStatusName,
                                                              Pageable pageable);

    /**
     * 프론트 관리자 페이지에서 이름으로 검색하였을 때
     * 테이블에서 입력된 검색어 기준으로 데이터들을 가져옵니다.
     *
     * @param name             검색한 단어가 포함되는 이름을 찾습니다.
     * @param memberStatusName 멤버 상태별 데이터를 가져오기 위한 파라미터 입니다.
     * @param pageable         검색된 멤버 리스트는 페이징 처리합니다.
     * @return 검색된 단어가 포함된 이름이 있는 데이터 리스트들을 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberListByName(String name,
                                                          String memberStatusName,
                                                          Pageable pageable);

    /**
     * 프론트 관리자 페이지에서 핸드폰번호로 검색하였을 때
     * 테이블에서 입력된 검색어 기준으로 데이터들을 가져옵니다.
     *
     * @param phoneNumber      검색한 단어가 포함되는 핸드폰번호를 찾습니다.
     * @param memberStatusName 멤버 상태별 데이터를 가져오기 위한 파라미터 입니다.
     * @param pageable         검색된 멤버 리스트는 페이징 처리합니다.
     * @return 검색된 단어가 포함된 핸드폰 번호가 있는 데이터 리스트들을 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberListByPhoneNumber(String phoneNumber,
                                                                 String memberStatusName,
                                                                 Pageable pageable);

    /**
     * Find member list by date of joining page.
     *
     * @param start            the start
     * @param end              the end
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return the page
     * @author
     */
    Page<MemberExceptPwdResponseDto> findMemberListByDateOfJoining(LocalDateTime start,
                                                                   LocalDateTime end,
                                                                   String memberStatusName,
                                                                   Pageable pageable);

    /**
     * 프론트 관리자 페이지에서 특정 검색어로 검색하였을 때
     * 테이블에서 입력된 검색어 기준으로 데이터들을 가져옵니다.
     *
     * @param searchWord       검색한 단어가 포함되는 특정 검색어를 찾습니다.
     * @param memberStatusName 멤버 상태별 데이터를 가져오기 위한 파라미터 입니다.
     * @param pageable         검색된 멤버 리스트는 페이징 처리합니다.
     * @return 검색된 단어가 포함된 특정 검색어가 있는 데이터 리스트들을 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberListBySearchWord(String searchWord,
                                                                String memberStatusName,
                                                                Pageable pageable);

    /**
     * 멤버 No로 차단된 회원 정보를 찾기 위한 query dsl 입니다.
     *
     * @param memberNo 검색할 멤버 아이디입니다.
     * @return 검색된 멤버 정보를 담을 dto 입니다.
     * @author 노수연
     */
    MemberExceptPwdBlockResponseDto findBlockMemberByMemberNo(Long memberNo);

    /**
     * 전체 멤버 수를 카운트하기 위한 query dsl 입니다.
     *
     * @return 멤버 수를 반환합니다.
     * @author 노수연
     */
    Long memberCountBy();

    /**
     * 멤버상태별 전체 멤버 수를 카운트하기 위한 query dsl 입니다.
     *
     * @param statusName the status name
     * @return 멤버 수를 반환합니다.
     * @author 노수연
     */
    Long memberCountByStatusName(String statusName);

    /**
     * 멤버등급별 전체 멤버 수를 카운트하기 위한 query dsl 입니다.
     *
     * @param membershipGrade the membership grade
     * @return 멤버 수를 반환합니다.
     * @author 노수연
     */
    Long memberCountByMembershipGrade(String membershipGrade);

    /**
     * 이름으로 테이블에 해당 이름을 가진 회원이 존재하는지 확인하는 메서드입니다.
     * 작가등록할 때 입력한 이름이 테이블의 이름과 일치하는지 확인하기 위함입니다.
     *
     * @param memberId the member id
     * @param name     이름으로 테이블에 회원 데이터가 있는지 확인합니다.
     * @return 존재하면 true 없으면 false를 반환합니다.
     * @author 노수연
     */
    Boolean existsByNameAndFindNameWithMemberId(String memberId, String name);

}
