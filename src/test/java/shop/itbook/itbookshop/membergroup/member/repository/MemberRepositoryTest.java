package shop.itbook.itbookshop.membergroup.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Membership membership;

    MemberStatus normalMemberStatus;

    Member member;

    @BeforeEach
    void setup() {

        //given
        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        testEntityManager.flush();
        testEntityManager.clear();

    }

    @Test
    @DisplayName("멤버 아이디로 특정 멤버를 찾기")
    void findByMemberNo() {

        // when
        MemberExceptPwdResponseDto
            testMember = memberRepository.findByMemberNo(member.getMemberNo()).orElseThrow();

        // then
        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    @DisplayName("멤버아이디로 멤버 모든 정보 가져오기")
    void findByMemberNoAllInfo() {

        // when
        MemberResponseDto testMember =
            memberRepository.findByMemberNoAllInfo(member.getMemberNo()).orElseThrow();

        // then
        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    void findByMemberId() {
        MemberResponseDto testMember =
            memberRepository.findByMemberId(member.getMemberId()).orElseThrow();

        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    @DisplayName("멤버아이디로 멤버 모든 정보 가져오기")
    void findByMemberNoReceiveMember() {

        // when
        Member testMember =
            memberRepository.findByMemberNoReceiveMember(member.getMemberNo()).orElseThrow();

        // then
        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());

    }

    @Test
    void findWriterList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findWriterList(pageRequest);

        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findMemberList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberList(pageRequest);

        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findNormalMemberList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findNormalMemberList(pageRequest);

        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findBlockMemberList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findBlockMemberList(pageRequest);

        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(0);
    }

    @Test
    void findWithdrawMemberList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page =
            memberRepository.findWithdrawMemberList(pageRequest);

        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(0);
    }

    @Test
    void existsByEmailAndIsSocial() {
        assertThat(memberRepository.existsByEmailAndIsSocial(member.getEmail())).isEqualTo(true);
    }

    @Test
    void existsByMemberIdAndIsSocial() {
        assertThat(memberRepository.existsByMemberIdAndIsSocial(member.getEmail())).isEqualTo(
            false);
    }

    @Test
    void findMemberListByMemberId() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberListByMemberId(
            member.getMemberId(), member.getMemberStatus().getMemberStatusEnum().getMemberStatus(),
            pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findMemberListByNickname() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberListByNickname(
            member.getNickname(), member.getMemberStatus().getMemberStatusEnum().getMemberStatus(),
            pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findMemberListByName() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberListByName(
            member.getName(), member.getMemberStatus().getMemberStatusEnum().getMemberStatus(),
            pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findMemberListByPhoneNumber() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberListByPhoneNumber(
            member.getPhoneNumber(),
            member.getMemberStatus().getMemberStatusEnum().getMemberStatus(),
            pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findMemberListByDateOfJoining() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberListByDateOfJoining(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            LocalDateTime.of(2030, 2, 20, 0, 0, 0),
            member.getMemberStatus().getMemberStatusEnum().getMemberStatus(),
            pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findMemberListBySearchWord() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberListBySearchWord(
            member.getPhoneNumber(),
            member.getMemberStatus().getMemberStatusEnum().getMemberStatus(),
            pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    void findBlockMemberByMemberNo() {
        assertThat(memberRepository.findBlockMemberByMemberNo(member.getMemberNo())).isNull();
    }

    @Test
    void memberCountBy() {

        Long memberCnt = memberRepository.memberCountBy();

        assertThat(memberCnt).isEqualTo(1);

    }

    @Test
    void memberCountByStatusName() {
        Long memberCnt = memberRepository.memberCountByStatusName(
            member.getMemberStatus().getMemberStatusEnum().getMemberStatus());

        assertThat(memberCnt).isEqualTo(1);
    }

    @Test
    void memberCountByMembershipGrade() {
        Long memberCnt = memberRepository.memberCountByMembershipGrade(
            member.getMembership().getMembershipGrade());

        assertThat(memberCnt).isEqualTo(1);
    }

    void existsByNameAndFindNameWithMemberId() {
        assertThat(memberRepository.existsByNameAndFindNameWithMemberId(member.getMemberId(),
            member.getName())).isTrue();
    }

    @Test
    @DisplayName("모든 멤버 리스트 찾기")
    void findAllBy() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<MemberExceptPwdResponseDto> page = memberRepository.findMemberList(pageRequest);

        //when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        //then
        assertThat(memberList.size()).isNotIn(0);
    }

    @Test
    @DisplayName("해당 멤버 아이디가 테이블에 존재하는지 확인하기")
    void existsByMemberId() {

        String memberId = "user1000";

        //then
        assertThat(memberRepository.existsByMemberId(memberId)).isTrue();
    }

    @Test
    @DisplayName("해당 닉네임이 테이블에 존재하는지 확인하기")
    void existsByNickname() {

        String nickname = "감자";

        //then
        assertThat(memberRepository.existsByNickname(nickname)).isTrue();
    }

    @Test
    @DisplayName("해당 이메일이 테이블에 존재하는지 확인하기")
    void existsByEmail() {

        String email = "user1000@test.com";

        //then
        assertThat(memberRepository.existsByEmail(email)).isTrue();
    }

    @Test
    @DisplayName("해당 핸드폰 번호가 테이블에 존재하는지 확인하기")
    void existsByPhoneNumber() {

        String phoneNumber = "010-9999-9999";

        //then
        assertThat(memberRepository.existsByPhoneNumber(phoneNumber)).isTrue();
    }

    @DisplayName("자사 로그인 회원 조회 성공 테스트")
    @Test
    void findAuthInfoByMemberIdTest() {

        // given, when
        Optional<MemberAuthInfoResponseDto> memberAuthInfoResponseDto =
            memberRepository.findAuthInfoByMemberId(member.getMemberId());

        // then
        memberAuthInfoResponseDto.ifPresent(dto ->
            assertThat(dto.getMemberNo()).isEqualTo(member.getMemberNo())
        );
    }

}