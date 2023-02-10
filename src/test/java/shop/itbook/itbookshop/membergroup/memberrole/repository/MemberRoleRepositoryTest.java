package shop.itbook.itbookshop.membergroup.memberrole.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleAllResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.dummy.MemberRoleDummy;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.role.dummy.RoleDummy;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.repository.RoleRepository;

/**
 * @author 강명관
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MemberRoleRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MemberRoleRepository memberRoleRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Membership membership;

    MemberStatus memberStatus;

    Member member;

    Role role;

    MemberRole memberRole;

    @BeforeEach
    void setUp() {

        membership = MembershipDummy.getMembership();
        memberStatus = MemberStatusDummy.getNormalMemberStatus();
        member = MemberDummy.getMember1();
        role = RoleDummy.getRole();
        memberRole = MemberRoleDummy.getMemberRole(member, role);

        membershipRepository.save(membership);
        memberStatusRepository.save(memberStatus);

        member.setMembership(membership);
        member.setMemberStatus(memberStatus);
        memberRepository.save(member);

        roleRepository.save(role);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @DisplayName("회원 권한 save 테스트")
    @Test
    void saveTest() {
        // given, when
        MemberRole save = memberRoleRepository.save(memberRole);

        // then
        assertThat(save.getMember().getMemberNo()).isEqualTo(member.getMemberNo());
        assertThat(save.getRole().getRoleType()).isEqualTo(role.getRoleType());
    }

    @Test
    @DisplayName("회원번호를 통해 회원 권한 갖고오기")
    void findMemberRoleWithMemberNoTest() {

        // given
        MemberRole save = memberRoleRepository.save(memberRole);

        // when, then
        List<MemberRoleResponseDto> memberRoleWithMemberNo =
            memberRoleRepository.findMemberRoleWithMemberNo(member.getMemberNo());

        assertThat(memberRoleWithMemberNo).hasSize(1);
        assertThat(memberRoleWithMemberNo.get(0).getRole()).isEqualTo(
            role.getRoleType().getRoleName());
    }

    @DisplayName("회원번호를 통해 모든 회원 권한 정보 갖고오기")
    @Test
    void findMemberRoleAllInfoWithMemberNoTest() {
        // given
        MemberRole save = memberRoleRepository.save(memberRole);

        // when, then
        List<MemberRoleAllResponseDto> memberRoleAllInfoWithMemberNo =
            memberRoleRepository.findMemberRoleAllInfoWithMemberNo(member.getMemberNo());

        assertThat(memberRoleAllInfoWithMemberNo).hasSize(1);
        assertThat(memberRoleAllInfoWithMemberNo.get(0).getRoleNo()).isEqualTo(role.getRoleNo());
    }
}
