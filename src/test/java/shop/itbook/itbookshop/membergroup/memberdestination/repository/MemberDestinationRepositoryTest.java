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
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.dummy.MemberDestinationDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
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

    @BeforeEach
    void setUp() {
        memberRepository.save(MemberDummy.getMember1());
    }

    @Test
    @DisplayName("멤버 번호로 해당 멤버의 배송지 정보를 가져오기 성공")
    void findMemberDestinationByMember_MemberNoSuccessTest() {
        MemberDestination memberDestination = MemberDestinationDummy.getMemberDestination();

        MemberDestination savedMemberDestination =
            memberDestinationRepository.save(MemberDestinationDummy.getMemberDestination());

        assertThat(savedMemberDestination.getRecipientDestinationNo()).isEqualTo(
            memberDestination.getRecipientDestinationNo());
    }
}