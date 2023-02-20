package shop.itbook.itbookshop.membergroup.membership.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipModifyRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.membership.transfer.MembershipTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(MembershipServiceImpl.class)
class MembershipServiceImplTest {

    @Autowired
    MembershipService membershipService;

    @MockBean
    MembershipRepository membershipRepository;

    Membership membership;

    @BeforeEach
    void setUp() {
        membership = MembershipDummy.getMembership();

        MembershipRequestDto membershipRequestDto = new MembershipRequestDto();
        ReflectionTestUtils.setField(membershipRequestDto, "membershipGrade", "white");
        ReflectionTestUtils.setField(membershipRequestDto, "membershipStandardAmount", 100_000L);
        ReflectionTestUtils.setField(membershipRequestDto, "membershipPoint", 10_000L);

        given(membershipRepository.save(membership)).willReturn(membership);

    }

    @Test
    void removeMembership() {
        given(membershipRepository.findById(any())).willReturn(Optional.ofNullable(membership));

        membershipService.removeMembership(1);
    }

    @Test
    void modifyMembership() {

        MembershipModifyRequestDto membershipModifyRequestDto = new MembershipModifyRequestDto();
        ReflectionTestUtils.setField(membershipModifyRequestDto, "membershipGrade", "silver");
        ReflectionTestUtils.setField(membershipModifyRequestDto, "membershipStandardAmount",
            200_000L);
        ReflectionTestUtils.setField(membershipModifyRequestDto, "membershipPoint", 20_000L);

        given(membershipRepository.findById(any())).willReturn(Optional.ofNullable(membership));

        membershipService.modifyMembership(1, membershipModifyRequestDto);

    }

    @Test
    void findMembership() {
        given(membershipRepository.findById(any())).willReturn(Optional.ofNullable(membership));

        MembershipResponseDto membershipResponseDto =
            MembershipTransfer.entityToDto(membershipRepository.findById(1).get());

        assertThat(membershipResponseDto.getMembershipGrade()).isEqualTo("white");

    }

    @Test
    void findMembershipByMembershipGrade() {

        given(membershipRepository.findByMembershipGrade(anyString())).willReturn(
            Optional.of(membership));

        assertThat(membershipService.findMembershipByMembershipGrade("white")
            .getMembershipPoint()).isEqualTo(10_000L);
    }

    @Test
    void findMembershipList() {

        MembershipResponseDto membershipResponseDto =
            new MembershipResponseDto(1, "white", 100_000L, 10_000L);

        given(membershipRepository.findAllBy()).willReturn(List.of(membershipResponseDto));

        assertThat(membershipService.findMembershipList().size()).isEqualTo(1);
    }
}