package shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
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
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(MemberStatusAdminServiceImpl.class)
class MemberStatusAdminServiceImplTest {

    @Autowired
    MemberStatusAdminService memberStatusAdminService;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    MemberStatus normalMemberStatus;

    @BeforeEach
    void setUp() {
        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);
    }

    @Test
    void findMemberStatus() {

        MemberStatusResponseDto memberStatusResponseDto = new MemberStatusResponseDto();
        ReflectionTestUtils.setField(memberStatusResponseDto, "memberStatusNo", 1);
        ReflectionTestUtils.setField(memberStatusResponseDto, "memberStatusName", "정상회원");

        given(memberStatusRepository.findByMemberStatusName(anyString())).willReturn(
            Optional.of(memberStatusResponseDto));

        assertThat(memberStatusAdminService.findMemberStatus("정상회원")).isEqualTo(
            memberStatusResponseDto);
    }

    @Test
    void findMemberStatusList() {
        MemberStatusResponseDto memberStatusResponseDto = new MemberStatusResponseDto();
        ReflectionTestUtils.setField(memberStatusResponseDto, "memberStatusNo", 1);
        ReflectionTestUtils.setField(memberStatusResponseDto, "memberStatusName", "정상회원");

        given(memberStatusRepository.findMemberStatusResponseAll()).willReturn(
            List.of(memberStatusResponseDto));

        assertThat(memberStatusAdminService.findMemberStatusList()).hasSize(1);

    }
}