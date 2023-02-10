package shop.itbook.itbookshop.membergroup.membershiphistory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookshop.membergroup.membershiphistory.dummy.MembershipHistoryDummy;
import shop.itbook.itbookshop.membergroup.membershiphistory.entity.MembershipHistory;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class MembershipHistoryRepositoryTest {

    @Autowired
    MembershipHistoryRepository membershipHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    TestEntityManager testEntityManager;

    MembershipHistory membershipHistory;

    Member member;

    Membership membership;

    MemberStatus normalMemberStatus;

    @BeforeEach
    void setUp() {

        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        membershipHistory = MembershipHistoryDummy.getMembershipHistory();
        membershipHistory.setMembership(membership);
        membershipHistory.setMember(member);
        membershipHistoryRepository.save(membershipHistory);

        testEntityManager.flush();
        testEntityManager.clear();

    }

    @Test
    void findByMemberNo() {
        List<MembershipHistoryResponseDto> membershipHistoryResponseDtoList =
            membershipHistoryRepository.findByMemberNo(member.getMemberNo());

        assertThat(membershipHistoryResponseDtoList.size()).isEqualTo(1);
    }
}