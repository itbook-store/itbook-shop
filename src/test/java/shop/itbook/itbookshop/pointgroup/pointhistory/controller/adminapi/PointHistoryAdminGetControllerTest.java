package shop.itbook.itbookshop.pointgroup.pointhistory.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.impl.PointHistoryAdminServiceImpl;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@WebMvcTest(PointHistoryAdminGetController.class)
class PointHistoryAdminGetControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PointHistoryAdminGetController pointHistoryAdminGetController;

    @MockBean
    PointHistoryAdminServiceImpl pointHistoryAdminService;

    @Autowired
    ObjectMapper objectMapper;

    PointHistoryListResponseDto orderIncreasePointHistory;
    PointHistoryListResponseDto orderDecreasePointHistory;

    @BeforeEach
    void setUp() {

        orderDecreasePointHistory = new PointHistoryListResponseDto();
        ReflectionTestUtils.setField(orderDecreasePointHistory, "memberNo", 1L);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "memberId", "z1존길동");
        ReflectionTestUtils.setField(orderDecreasePointHistory, "memberName", "고길동");
        ReflectionTestUtils.setField(orderDecreasePointHistory, "pointHistoryNo", 1L);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "content", "주문");
        ReflectionTestUtils.setField(orderDecreasePointHistory, "isDecrease", false);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "increaseDecreasePoint", 500L);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "remainedPoint", 500L);

        orderIncreasePointHistory = new PointHistoryListResponseDto();
        ReflectionTestUtils.setField(orderIncreasePointHistory, "memberNo", 1L);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "memberId", "z1존길동");
        ReflectionTestUtils.setField(orderIncreasePointHistory, "memberName", "고길동");
        ReflectionTestUtils.setField(orderIncreasePointHistory, "pointHistoryNo", 2L);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "content", "주문");
        ReflectionTestUtils.setField(orderIncreasePointHistory, "isDecrease", true);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "increaseDecreasePoint", 200L);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "remainedPoint", 300L);

    }


    @Test
    void pointHistoryDetails() throws Exception {


    }

    @DisplayName("url에 맞게 요청이 잘 들어가고 PointHistoryListResponseDto 형태의 List를 200상태로 잘 받아온다.")
    @Test
    void pointHistoryList() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page =
            new PageImpl(List.of(orderDecreasePointHistory, orderIncreasePointHistory), pageRequest,
                10);

        given(pointHistoryAdminService.findPointHistoryList(any(),
            any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/point-histories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].memberNo",
                equalTo(orderDecreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[0].pointHistoryNo",
                equalTo(orderDecreasePointHistory.getPointHistoryNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].memberNo",
                equalTo(orderIncreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].pointHistoryNo",
                equalTo(orderIncreasePointHistory.getPointHistoryNo()), Long.class));
    }

    @DisplayName("url에 맞게 요청이 잘 들어가고 PointHissotryListResponseDto 형태와 200상태로 잘 반환되어 진다.")
    @Test
    void pointHistoryListBySearch() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page =
            new PageImpl(List.of(orderDecreasePointHistory, orderIncreasePointHistory), pageRequest,
                10);

        given(pointHistoryAdminService.findPointHistoryListBySearch(any(),
            any(), any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/point-histories/search?searchWord=z1존길동")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].memberNo",
                equalTo(orderDecreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[0].pointHistoryNo",
                equalTo(orderDecreasePointHistory.getPointHistoryNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].memberNo",
                equalTo(orderIncreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].pointHistoryNo",
                equalTo(orderIncreasePointHistory.getPointHistoryNo()), Long.class));
    }
}