package shop.itbook.itbookshop.membergroup.membershiphistory.controller.serviceapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookshop.membergroup.membershiphistory.service.MembershipHistoryService;

/**
 * @author 노수연
 * @since 1.0
 */
@WebMvcTest(MembershipHistoryController.class)
class MembershipHistoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MembershipHistoryService membershipHistoryService;

    @Test
    void membershipHistoryList() throws Exception {
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

        given(membershipHistoryService.getMembershipHistories(any())).willReturn(
            List.of(membershipHistoryResponseDto));

        mvc.perform(get("/api/membership-history/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}