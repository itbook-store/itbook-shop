package shop.itbook.itbookshop.membergroup.member.service.adminapi;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdBlockResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;

/**
 * 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    /**
     * 특정 memberNo로 멤버 조회 기능을 담당하는 메서드입니다.
     * 관리자는 개별 회원을 조회할 때 비밀번호를 제외한 정보들을 조회할 수 있습니다.
     *
     * @param memberNo 멤버 no로 테이블에서 멤버를 찾습니다.
     * @return MemberResponseDto를 반환합니다.
     * @author 노수연
     */
    MemberExceptPwdResponseDto findMember(Long memberNo);


    /**
     * 작가인 모든 회원 리스트를 반환하는 메서드입니다.
     *
     * @param pageable 페이징처리된 리스트를 반환합니다.
     * @return 페이징처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findWriterList(Pageable pageable);

    /**
     * 모든 멤버 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @param pageable 페이징처리된 리스트를 반환합니다.
     * @return 페이징처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberList(Pageable pageable);

    /**
     * @param pageable 페이징처리된 리스트를 반환합니다.
     * @return 페이징처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findNormalMemberList(Pageable pageable);

    /**
     * @param pageable 페이징처리된 리스트를 반환합니다.
     * @return 페이징처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findBlockMemberList(Pageable pageable);

    /**
     * @param pageable 페이징처리된 리스트를 반환합니다.
     * @return 페이징처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findWithdrawMemberList(Pageable pageable);

    /**
     * 관리자는 특정 멤버의 상태를 '차단회원'과 '정상회원'으로 변경할 수 있습니다.
     *
     * @param memberNo   멤버 no로 테이블에서 해당 멤버를 찾습니다.
     * @param requestDto 멤버 상태 값이 담긴 dto를 받아와 테이블에서 해당 멤버의 멤버상태를 변경합니다.
     * @author 노수연
     */
    void modifyMemberStatusInfo(Long memberNo, MemberStatusUpdateAdminRequestDto requestDto);


    /**
     * 관리자는 관리자 페이지의 회원 목록 페이지에서 멤버 아이디 기준으로 검색하였을때
     * 검색어가 포함된 멤버 리스트들을 볼 수 있습니다.
     *
     * @param memberId         관리자가 검색한 멤버 아이디 단어 입니다.
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return 검색어가 포함된 멤버 아이디들의 데이터를 리스트로 반환합니다.
     * @author 노수연
     */
    Page<MemberExceptPwdResponseDto> findMemberListByMemberId(String memberId,
                                                              String memberStatusName,
                                                              Pageable pageable);

    /**
     * @param nickname         the nickname
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return the page
     * @author
     */
    Page<MemberExceptPwdResponseDto> findMemberListByNickname(String nickname,
                                                              String memberStatusName,
                                                              Pageable pageable);

    /**
     * @param name             the name
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return the page
     * @author
     */
    Page<MemberExceptPwdResponseDto> findMemberListByName(String name, String memberStatusName,
                                                          Pageable pageable);

    /**
     * @param phoneNumber      the phone number
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return the page
     * @author
     */
    Page<MemberExceptPwdResponseDto> findMemberListByPhoneNumber(String phoneNumber,
                                                                 String memberStatusName,
                                                                 Pageable pageable);

    /**
     * @param start            the start
     * @param end              the end
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return the page
     * @author
     */
    Page<MemberExceptPwdResponseDto> findMemberListByDateOfJoining(LocalDate start, LocalDate end,
                                                                   String memberStatusName,
                                                                   Pageable pageable);

    /**
     * @param searchWord       the search word
     * @param memberStatusName the member status name
     * @param pageable         the pageable
     * @return the page
     * @author
     */
    Page<MemberExceptPwdResponseDto> findMemberListBySearchWord(String searchWord,
                                                                String memberStatusName,
                                                                Pageable pageable);

    /**
     * @param memberNo the member no
     * @return the member except pwd block response dto
     * @author
     */
    MemberExceptPwdBlockResponseDto findBlockMember(Long memberNo);

    /**
     * @return the member count response dto
     * @author
     */
    MemberCountResponseDto memberCountByMemberStatus();

    /**
     * @return the member count by membership response dto
     * @author
     */
    MemberCountByMembershipResponseDto memberCountByMembership();

    void modifyToWriterAccount(Long memberNo);
}
