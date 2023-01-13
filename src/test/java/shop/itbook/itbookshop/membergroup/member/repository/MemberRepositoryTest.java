package shop.itbook.itbookshop.membergroup.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Member testMember1;
    Member testMember2;
    Membership membership;
    MemberStatus memberStatus;

    @Autowired
    private MemberStatusRepository memberStatusRepository;
    @Autowired
    private MembershipRepository membershipRepository;

    @MockBean
    private CustomMemberRepository customMemberRepository;

    @BeforeEach
    void setup() {

        membership =
            Membership.builder().membershipGrade("white").membershipStandardAmount(100_000L)
                .membershipPoint(10_000L).build();

        membershipRepository.save(membership);

        memberStatus = MemberStatus.builder().memberStatusEnum(MemberStatusEnum.NORMAL).build();

        memberStatusRepository.save(memberStatus);

        testMember1 =
            Member.builder().membership(membership).memberStatus(memberStatus).id("user134")
                .nickname("유저134").name("홍길동").isMan(true).birth(
                    LocalDateTime.of(2000, 1, 1, 0, 0, 0)).password("1234")
                .phoneNumber("010-03440-0000")
                .email("user1342@test1.com").memberCreatedAt(LocalDateTime.now()).build();

        memberRepository.save(testMember1);

        testMember2 = Member.builder().membership(membership).memberStatus(memberStatus).id("user2")
            .nickname("유저2").name("김철수").isMan(true).birth(
                LocalDateTime.of(2000, 1, 1, 0, 0, 0)).password("1234").phoneNumber("010-1000-0000")
            .email("user2@test1.com").memberCreatedAt(LocalDateTime.now()).build();

        memberRepository.save(testMember2);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    /*@Test
    @DisplayName("멤버no로 특정 멤버를 찾기")
    void findById() {

        // when
        Member member = memberRepository.findById(testMember1.getMemberNo()).orElseThrow();

        //then
        assertThat(member.getMemberNo()).isEqualTo(testMember1.getMemberNo());

    }*/

    /*@Test
    @DisplayName("모든 멤버 리스트 찾기")
    void findAllBy() {

        //when
        List<MemberResponseProjectionDto> memberList = memberRepository.findAllBy();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }*/

    @Test
    @DisplayName("멤버 저장 테스트")
    void save() {
        Member member =
            Member.builder().membership(membership).memberStatus(memberStatus).id("user3")
                .nickname("유저3").name("김유리").isMan(true).birth(
                    LocalDateTime.of(2000, 1, 1, 0, 0, 0)).password("1234").phoneNumber("010-2000-0000")
                .email("user3@test1.com").memberCreatedAt(LocalDateTime.now()).build();

        memberRepository.save(member);

        Member testMember = memberRepository.findById(member.getMemberNo()).orElseThrow();
        System.out.println(testMember.getName());

        assertThat(testMember.getName()).isEqualTo("김유리");
    }

    /*@Test
    @DisplayName("멤버 삭제 테스트")
    void delete() {

        Member member =
            Member.builder().membership(membership).memberStatus(memberStatus).id("user4")
                .nickname("유저4").name("김짱구").isMan(true).birth(
                    LocalDateTime.of(2000, 1, 1, 0, 0, 0)).password("1234").phoneNumber("010-4000-0000")
                .email("user4@test.com").memberCreatedAt(LocalDateTime.now()).build();

        memberRepository.save(member);

        List<MemberResponseProjectionDto> memberList = customMemberRepository.querydslFindAll();

        assertThat(memberList.size()).isEqualTo(3);

        memberRepository.deleteById(member.getMemberNo());

        memberList = customMemberRepository.querydslFindAll();

        assertThat(memberList.size()).isEqualTo(2);

    }*/

}