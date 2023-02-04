package shop.itbook.itbookshop.membergroup.membership.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class MembershipRepositoryTest {

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Membership membership;

    @BeforeEach
    void setUp() {
        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);
    }

    @Test
    void findByMembershipGrade() {
        Membership testMembership =
            membershipRepository.findByMembershipGrade(membership.getMembershipGrade())
                .orElseThrow();

        assertThat(testMembership.getMembershipNo()).isEqualTo(membership.getMembershipNo());
    }

    @Test
    void findAllBy() {

        List<MembershipResponseDto> memberships = membershipRepository.findAllBy();

        assertThat(memberships.size()).isEqualTo(1);
    }
}