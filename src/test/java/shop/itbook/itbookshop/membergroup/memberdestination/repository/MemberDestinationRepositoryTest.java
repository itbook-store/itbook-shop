package shop.itbook.itbookshop.membergroup.memberdestination.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.dummy.MemberDestinationDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class MemberDestinationRepositoryTest {

    @Autowired
    MemberDestinationRepository memberDestinationRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberStatusRepository memberStatusRepository;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    TestEntityManager testEntityManager;

    Member member;

    @BeforeEach
    void setUp() {

        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);
        Membership memberShip = MembershipDummy.getMembership();
        membershipRepository.save(memberShip);

        member = MemberDummy.getMember1();
        member.setMemberStatus(normalMemberStatus);
        member.setMembership(memberShip);
        memberRepository.save(member);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("회원 배송지 DB에 엔티티 저장 성공")
    void saveSuccessTest() {
        MemberDestination memberDestination =
            MemberDestinationDummy.createMemberDestination(member);

        MemberDestination savedMemberDestination =
            memberDestinationRepository.save(memberDestination);

        assertThat(savedMemberDestination.getRecipientDestinationNo()).isEqualTo(
            memberDestination.getRecipientDestinationNo());
    }

    @Test
    @DisplayName("멤버 번호로 해당 멤버의 배송지 정보 리스트를 가져오기 성공")
    void findMemberDestinationByMember_MemberNoSuccessTest() {
        MemberDestination memberDestination =
            MemberDestinationDummy.createMemberDestination(member);

        memberDestinationRepository.save(memberDestination);

        List<MemberDestination> memberDestinationList =
            memberDestinationRepository.findAllByMember_MemberNoOrderByRecipientDestinationNoDesc(
                member.getMemberNo());

        assertThat(memberDestinationList.get(0).getMember().getMemberNo()).isEqualTo(
            member.getMemberNo());
    }
}