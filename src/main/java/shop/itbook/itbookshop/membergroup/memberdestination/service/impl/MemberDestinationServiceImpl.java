package shop.itbook.itbookshop.membergroup.memberdestination.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.exception.MemberDestinationComeCloseOtherMemberException;
import shop.itbook.itbookshop.membergroup.memberdestination.exception.MemberDestinationNotFoundException;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.membergroup.memberdestination.transfer.MemberDestinationTransfer;

/**
 * MemberDestinationService 인터페이스의 구현 클래스
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberDestinationServiceImpl implements MemberDestinationService {

    private final MemberDestinationRepository memberDestinationRepository;

    private final MemberService memberService;

    @Override
    public List<MemberDestinationResponseDto> findMemberDestinationResponseDtoByMemberNo(
        Long memberNo) {

        return memberDestinationRepository.findAllByMember_MemberNoOrderByRecipientDestinationNoDesc
                (memberNo)
            .stream()
            .map(MemberDestinationTransfer::entityToDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMemberDestination(
        List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList) {

        for (MemberDestinationNoResponseDto memberDestinationNo : memberDestinationNoResponseDtoList) {
            memberDestinationRepository.deleteById(memberDestinationNo.getRecipientDestinationNo());
        }

    }

    @Override
    @Transactional
    public Long addMemberDestination(MemberDestinationRequestDto memberDestinationRequestDto) {

        Member member =
            memberService.findMemberByMemberNo(memberDestinationRequestDto.getMemberNo());

        MemberDestination memberDestination = MemberDestination.builder()
            .member(member)
            .recipientName(memberDestinationRequestDto.getRecipientName())
            .recipientPhoneNumber(memberDestinationRequestDto.getRecipientPhoneNumber())
            .postcode(memberDestinationRequestDto.getPostcode())
            .roadNameAddress(memberDestinationRequestDto.getRoadNameAddress())
            .recipientAddressDetails(memberDestinationRequestDto.getRecipientAddressDetails())
            .build();

        return memberDestinationRepository.save(memberDestination).getRecipientDestinationNo();
    }

    @Override
    public MemberDestination findByRecipientDestinationNo(Long recipientDestinationNo) {
        return memberDestinationRepository.findById(recipientDestinationNo).orElseThrow(
            MemberDestinationNotFoundException::new);
    }

    @Override
    public MemberDestination findByRecipientDestinationNoAndMemberNo(Long memberNo,
                                                                     Long recipientDestinationNo) {

        MemberDestination memberDestination =
            memberDestinationRepository.findById(recipientDestinationNo).orElseThrow(
                MemberDestinationNotFoundException::new);

        if (!Objects.equals(memberDestination.getMember().getMemberNo(), memberNo)) {
            throw new MemberDestinationComeCloseOtherMemberException();
        }

        return memberDestination;
    }

    @Override
    @Transactional
    public void modifyMemberDestination(Long recipientDestinationNo,
                                        MemberDestinationRequestDto memberDestinationRequestDto) {

        MemberDestination memberDestination = findByRecipientDestinationNo(recipientDestinationNo);

        memberDestination.setRecipientName(memberDestinationRequestDto.getRecipientName());
        memberDestination.setRecipientPhoneNumber(
            memberDestinationRequestDto.getRecipientPhoneNumber());
        memberDestination.setPostcode(memberDestinationRequestDto.getPostcode());
        memberDestination.setRoadNameAddress(memberDestinationRequestDto.getRoadNameAddress());
        memberDestination.setRecipientAddressDetails(
            memberDestinationRequestDto.getRecipientAddressDetails());

    }
}
