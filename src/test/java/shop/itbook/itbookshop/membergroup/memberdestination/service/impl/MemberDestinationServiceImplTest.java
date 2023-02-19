package shop.itbook.itbookshop.membergroup.memberdestination.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dummy.MemberDestinationDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.exception.MemberDestinationComeCloseOtherMemberException;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(MemberDestinationServiceImpl.class)
class MemberDestinationServiceImplTest {

    @Autowired
    MemberDestinationService memberDestinationService;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberDestinationRepository memberDestinationRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    MembershipRepository membershipRepository;

    MemberDestination memberDestination;

    Member member;

    MemberStatus normalMemberStatus;

    Membership membership;

    MemberDestinationRequestDto memberDestinationRequestDto;

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

        memberDestination = MemberDestinationDummy.createMemberDestination(member);
        memberDestination.setRecipientDestinationNo(1L);
        memberDestinationRepository.save(memberDestination);

        memberDestinationRequestDto = new MemberDestinationRequestDto();
        ReflectionTestUtils.setField(memberDestinationRequestDto, "memberNo", 1L);
        ReflectionTestUtils.setField(memberDestinationRequestDto, "recipientName", "유저");
        ReflectionTestUtils.setField(memberDestinationRequestDto, "recipientPhoneNumber",
            "01022922222");
        ReflectionTestUtils.setField(memberDestinationRequestDto, "postcode", 12345);
        ReflectionTestUtils.setField(memberDestinationRequestDto, "roadNameAddress", "서울 동작구");
        ReflectionTestUtils.setField(memberDestinationRequestDto, "recipientAddressDetails", "2층");
    }

    @Test
    void findMemberDestinationResponseDtoByMemberNo() {
        MemberDestinationResponseDto memberDestinationResponseDto =
            new MemberDestinationResponseDto();
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientDestinationNo", 1L);
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientName", "유저");
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientPhoneNumber",
            "01029292811");
        ReflectionTestUtils.setField(memberDestinationResponseDto, "postcode", 62222);
        ReflectionTestUtils.setField(memberDestinationResponseDto, "roadNameAddress", "서울 동작구");
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientAddressDetails", "1층");

        given(memberDestinationRepository.findAllByMember_MemberNoOrderByRecipientDestinationNoDesc(
            any())).willReturn(List.of(memberDestination));

        assertThat(memberDestinationService.findMemberDestinationResponseDtoByMemberNo(1L).get(0)
            .getPostcode()).isEqualTo(12345);
    }

    @Test
    void deleteMemberDestination() {
        MemberDestinationNoResponseDto memberDestinationNoResponseDto =
            new MemberDestinationNoResponseDto();
        ReflectionTestUtils.setField(memberDestinationNoResponseDto, "recipientDestinationNo", 1L);

        memberDestinationService.deleteMemberDestination(List.of(memberDestinationNoResponseDto));

        verify(memberDestinationRepository, times(1)).deleteById(1L);
    }

    @Test
    void addMemberDestination() {

        given(memberDestinationRepository.save(any())).willReturn(memberDestination);

        Long actual = memberDestinationService.addMemberDestination(memberDestinationRequestDto);

        assertThat(actual).isEqualTo(memberDestination.getRecipientDestinationNo());
    }

    @Test
    void findByRecipientDestinationNo() {

        given(memberDestinationRepository.findById(anyLong())).willReturn(
            Optional.of(memberDestination));

        assertThat(memberDestinationService.findByRecipientDestinationNo(1L)
            .getRecipientDestinationNo()).isEqualTo(
            memberDestination.getRecipientDestinationNo());
    }

    @Test
    void findByRecipientDestinationNoAndMemberNoException() {

        given(memberDestinationRepository.findById(anyLong())).willReturn(
            Optional.of(memberDestination));

        assertThatThrownBy(() -> {
            memberDestinationService.findByRecipientDestinationNoAndMemberNo(1L, 1L)
                .getRecipientDestinationNo();
        }).isInstanceOf(MemberDestinationComeCloseOtherMemberException.class)
            .hasMessage("다른 회원의 배송지에는 접근할 수 없습니다.");
    }

    @Test
    void modifyMemberDestination() {
        given(memberDestinationRepository.findById(any())).willReturn(
            Optional.of(memberDestination));

        memberDestinationService.modifyMemberDestination(1L, memberDestinationRequestDto);
    }
}