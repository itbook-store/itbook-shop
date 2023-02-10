package shop.itbook.itbookshop.membergroup.memberrole.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleAllResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.dummy.MemberRoleDummy;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;
import shop.itbook.itbookshop.membergroup.memberrole.repository.MemberRoleRepository;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.role.dummy.RoleDummy;
import shop.itbook.itbookshop.role.entity.Role;

/**
 * @author 강명관
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class MemberRoleServiceImplTest {

    MemberRoleService memberRoleService;

    @MockBean
    MemberRoleRepository memberRoleRepository;

    Member member;

    Role role;

    MemberRole memberRole;

    MemberRoleResponseDto memberRoleResponseDto;

    MemberRoleAllResponseDto memberRoleAllResponseDto;

    @BeforeEach
    void setUp() {
        member = MemberDummy.getMember1();
        role = RoleDummy.getRole();
        memberRole = MemberRoleDummy.getMemberRole(member, role);

        memberRoleResponseDto = new MemberRoleResponseDto(role.getRoleType().getRoleName());

        memberRoleAllResponseDto =
            new MemberRoleAllResponseDto(role.getRoleNo(), role.getRoleType().getRoleName());

        memberRoleService = new MemberRoleServiceImpl(memberRoleRepository);

    }

    @DisplayName("회원 번호를 통해 회원의 권한 이름 리스트 갖고오기 테스트")
    @Test
    void findMemberRoleWithMemberNo() {
        // given
        given(memberRoleRepository.findMemberRoleWithMemberNo(member.getMemberNo()))
            .willReturn(List.of(memberRoleResponseDto));

        // when
        List<MemberRoleResponseDto> memberRoleWithMemberNo =
            memberRoleService.findMemberRoleWithMemberNo(member.getMemberNo());

        //then
        assertThat(memberRoleWithMemberNo.get(0)).isEqualTo(memberRoleResponseDto);
    }

    @DisplayName("회원 번호를 통해 회원의 권한 번호, 권한 이름 리스트 갖고오기 테스트")
    @Test
    void findMemberRoleAllInfoWithMemberNo() {
        // given
        given(memberRoleRepository.findMemberRoleAllInfoWithMemberNo(member.getMemberNo()))
            .willReturn(List.of(memberRoleAllResponseDto));

        // when
        List<MemberRoleAllResponseDto> memberRoleAllInfoWithMemberNo =
            memberRoleService.findMemberRoleAllInfoWithMemberNo(member.getMemberNo());

        // then
        assertThat(memberRoleAllInfoWithMemberNo).hasSize(1);
        assertThat(memberRoleAllInfoWithMemberNo.get(0).getRoleNo()).isEqualTo(role.getRoleNo());
    }

    @DisplayName("회원 권한 추가 테스트")
    @Test
    void addMemberRole() {
        // given
        memberRoleService.addMemberRole(member, role);

        // when, then
        verify(memberRoleRepository, times(1)).save(any());
    }

    @DisplayName("회원 권한 삭제 테스트")
    @Test
    void deleteMemberRole() {
        // given
        memberRoleService.deleteMemberRole(member.getMemberNo(), role.getRoleNo());

        // when, then
        verify(memberRoleRepository, times(1)).deleteById(any());

    }
}