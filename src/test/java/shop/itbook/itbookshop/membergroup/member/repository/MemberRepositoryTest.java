package shop.itbook.itbookshop.membergroup.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    void findByMemberId() {

        // when
        MemberExceptPwdResponseDto
            testMember = memberRepository.findByMemberNo(member.getMemberNo()).orElseThrow();

        // then
        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    @DisplayName("멤버아이디로 멤버 모든 정보 가져오기")
    void findByMemberIdAllInfo() {

        // when
        MemberResponseDto testMember =
            memberRepository.findByMemberNoAllInfo(member.getMemberNo()).orElseThrow();

        // then
        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    @DisplayName("멤버아이디로 멤버 모든 정보 가져오기")
    void findByMemberIdReceiveMember() {

        // when
        Member testMember =
            memberRepository.findByMemberNoReceiveMember(member.getMemberNo()).orElseThrow();

        // then
        assertThat(testMember.getMemberId()).isEqualTo(member.getMemberId());

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

}