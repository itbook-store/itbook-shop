package shop.itbook.itbookshop.membergroup.member.service.serviceapi;

import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;

/**
 * 서비스 API 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberService {

    /**
     * 프론트 서버에서 유저가 비밀번호를 포함하여 자신의 모든 개인정보를 조회할 수 있도록 테이블에서 모든 필드들의 값을 가져오는 메서드입니다.
     *
     * @param memberId 멤버 아이디로 테이블에서 해당 멤버를 찾습니다.
     * @return 테이블의 필드들을 MemberResponseDto로 받아와 반환합니다.
     * @author 노수연
     */
    MemberResponseDto findMember(String memberId);

    /**
     * 프론트 서버에서 DTO를 통해 유저가 입력한 정보들이 넘어오면 이를 테이블에 저장하는 메서드입니다.
     *
     * @param requestDto 프론트 서버에서 넘어온 정보들을 저장한 DTO 입니다.
     * @return 멤버를 테이블에 저장한 뒤 저장된 memberNo를 반환합니다.
     * @author 노수연
     */
    Long addMember(MemberRequestDto requestDto);

    /**
     * 프론트 서버에서 DTO를 통해 유저가 수정하려고자 하는 정보들이 넘어오면 이를 dirty checking으로 테이블을 수정하는 메서드입니다.
     *
     * @param memberId   멤버 아이디로 테이블에서 해당 멤버를 찾습니다.
     * @param requestDto 수정할 정보가 담긴 DTO 입니다.
     * @author 노수연
     */
    void modifyMember(String memberId, MemberUpdateRequestDto requestDto);

    /**
     * 특정 유저 탈퇴 로직이 담긴 메서드입니다.
     *
     * @param memberId   멤버 아이디로 테이블에서 해당 멤버를 찾습니다.
     * @param requestDto '탈퇴 회원'이라는 멤버 상태가 담긴 DTO 입니다. 이 DTO로 해당 멤버의 멤버상태를 '탈퇴회원'으로 바꿉니다.
     * @author 노수연
     */
    void withDrawMember(String memberId, MemberStatusUpdateAdminRequestDto requestDto);

    MemberBooleanResponseDto checkMemberOauthEmailExists(String email);

    MemberBooleanResponseDto checkMemberOauthInfoExists(String memberId);

    /**
     * 인증에 대한 회원 정보를 반환받는 메서드 입니다.
     *
     * @param memberId 회원 아이디
     * @return 회원 넘버, 아이디, 비밀번호
     * @author 강명관
     */
    MemberAuthResponseDto findMemberAuthInfo(String memberId);

    /**
     * 파라미터로 넘어온 멤버아이디가 테이블에 있는지 판별하여 true/false로 반환하는 메서드입니다.
     * 회원가입할 때 입력한 아이디가 이미 테이블에 존재하는지 중복확인하기 위한 메서드입니다.
     *
     * @param memberId 테이블에 존재하는지 판별할 멤버 아이디입니다.
     * @return 테이블에 해당 멤버 아이디가 존재하면 true, 없으면 false를 반환합니다.
     * @author 노수연
     */
    MemberBooleanResponseDto checkMemberIdDuplicate(String memberId);

    /**
     * 파라미터로 넘어온 닉네임이 테이블에 있는지 판별하여 true/false로 반환하는 메서드입니다.
     * 회원가입할 때 입력한 닉네임이 이미 테이블에 존재하는지 중복확인하기 위한 메서드입니다.
     *
     * @param nickname 테이블에 존재하는지 판별할 닉네임입니다.
     * @return 테이블에 해당 닉네임이 존재하면 true, 없으면 false를 반환합니다.
     * @author 노수연
     */
    MemberBooleanResponseDto checkNickNameDuplicate(String nickname);

    /**
     * 파라미터로 넘어온 이메일이 테이블에 있는지 판별하여 true/false로 반환하는 메서드입니다.
     * 회원가입할 때 입력한 이메일이 이미 테이블에 존재하는지 중복확인하기 위한 메서드입니다.
     *
     * @param email 테이블에 존재하는지 판별할 이메일입니다.
     * @return 테이블에 해당 이메일이 존재하면 true, 없으면 false를 반환합니다.
     * @author 노수연
     */
    MemberBooleanResponseDto checkEmailDuplicate(String email);

    /**
     * 파라미터로 넘어온 핸드폰번호가 테이블에 있는지 판별하여 true/false로 반환하는 메서드입니다.
     * 회원가입할 때 입력한 핸드폰번호가 이미 테이블에 존재하는지 중복확인하기 위한 메서드입니다.
     *
     * @param phoneNumber 테이블에 존재하는지 판별할 핸드폰 번호입니다.
     * @return 테이블에 해당 핸드폰 번호가 존재하면 true, 없으면 false를 반환합니다.
     * @author 노수연
     */
    MemberBooleanResponseDto checkPhoneNumberDuplicate(String phoneNumber);
}
