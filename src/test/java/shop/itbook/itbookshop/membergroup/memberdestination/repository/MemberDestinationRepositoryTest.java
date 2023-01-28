package shop.itbook.itbookshop.membergroup.memberdestination.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    MemberDestination memberDestination;

    @BeforeEach
    void setUp() {

//        Member member = MemberDummy.getMember();
//        member.setMembership(MembershipDummy.getMembership());
//        member.setMemberStatus(MemberStatusDummy.getMemberStatus());

//        membershipRepository.save(MembershipDummy.getMembership());
//        memberRepository.save(member);

        memberDestination = MemberDestinationDummy.getMemberDestination();
//        memberDestination.setMember(member);

    }

    @Disabled
    @Test
    @DisplayName("멤버 번호로 해당 멤버의 배송지 정보를 가져오기 성공")
    void findMemberDestinationByMember_MemberNoSuccessTest() {

        Member member = MemberDummy.getMember();
        Membership membership = membershipRepository.getReferenceById(1);
        memberRepository.save(member);

        testEntityManager.flush();
        testEntityManager.clear();

        memberDestination.setMember(member);
        memberDestinationRepository.save(memberDestination);

        assertThat(memberDestinationRepository.findMemberDestinationsByMember_MemberNo(
                member.getMemberNo()).get(1)
            .getRecipientAddressDetails()).isEqualTo(
            memberDestination.getRecipientAddressDetails());
    }
}