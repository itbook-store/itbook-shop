package shop.itbook.itbookshop.membergroup.membershiphistory.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookshop.membergroup.membershiphistory.repository.MembershipHistoryRepository;
import shop.itbook.itbookshop.membergroup.membershiphistory.service.MembershipHistoryService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(MembershipHistoryServiceImpl.class)
class MembershipHistoryServiceImplTest {

    @Autowired
    MembershipHistoryService membershipHistoryService;

    @MockBean
    MembershipHistoryRepository membershipHistoryRepository;

    @Test
    void getMembershipHistories() {
        MembershipHistoryResponseDto membershipHistoryResponseDto =
            new MembershipHistoryResponseDto();
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "membershipHistoryNo", 1L);
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "monthlyUsageAmount", 189_000L);
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "membershipHistoryCreatedAt",
            LocalDateTime.now());
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "memberNo", 1L);
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "membershipNo", 1);
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "membershipGrade", "white");
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "memberStatusNo", 1);
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "nickname", "짱구");
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "name", "짱구");
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "isMan", true);
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "birth",
            LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "phoneNumber", "01022229333");
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "email", "user@test.com");
        ReflectionTestUtils.setField(membershipHistoryResponseDto, "memberCreatedAt",
            LocalDateTime.of(2023, 2, 2, 0, 0, 0));

        given(membershipHistoryRepository.findByMemberNo(anyLong())).willReturn(
            List.of(membershipHistoryResponseDto));

        assertThat(membershipHistoryService.getMembershipHistories(1L)).hasSize(1);
    }
}