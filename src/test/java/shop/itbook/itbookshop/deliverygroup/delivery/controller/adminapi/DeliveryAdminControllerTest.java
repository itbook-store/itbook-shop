package shop.itbook.itbookshop.deliverygroup.delivery.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.resultmessageenum.DeliveryResultMessageEnum;
import shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.DeliveryAdminService;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.fileservice.init.TokenInterceptor;

/**
 * @author 정재원
 * @since 1.0
 */
@WebMvcTest(DeliveryAdminController.class)
class DeliveryAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DeliveryAdminController deliveryAdminController;

    @MockBean
    DeliveryAdminService deliveryAdminService;

    @MockBean
    TokenInterceptor tokenInterceptor;

    @Test
    @DisplayName("모든 배송 정보 성공적 반환")
    void getDeliveryListWithStatusSuccessTest() throws Exception {

        List<DeliveryWithStatusResponseDto> responseDtoList = new ArrayList<>();

        DeliveryWithStatusResponseDto deliveryWithStatusResponseDto1 =
            new DeliveryWithStatusResponseDto();
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "deliveryNo", 2200000000L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "orderNo", 2200000000L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "trackingNo", "123123123");
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "deliveryStatus",
            DeliveryStatusEnum.DELIVERY_COMPLETED.getDeliveryStatus());

        DeliveryWithStatusResponseDto deliveryWithStatusResponseDto2 =
            new DeliveryWithStatusResponseDto();
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "deliveryNo", 2200000002L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "orderNo", 2200000002L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "trackingNo", "123123124");
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto1, "deliveryStatus",
            DeliveryStatusEnum.DELIVERY_IN_PROGRESS.getDeliveryStatus());

        responseDtoList.add(deliveryWithStatusResponseDto1);
        responseDtoList.add(deliveryWithStatusResponseDto2);

        Page<DeliveryWithStatusResponseDto> pageList = new PageImpl<>(responseDtoList);

        given(deliveryAdminService.findDeliveryListWithStatus(any(Pageable.class))).willReturn(
            pageList);

        mockMvc.perform(get("/api/admin/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    @DisplayName("배송 대기 상태인 배송 정보만을 성공적으로 가져온다")
    void getDeliveryListWithStatusWaitSuccessTest() throws Exception {

        List<DeliveryWithStatusResponseDto> responseDtoList = new ArrayList<>();

        DeliveryWithStatusResponseDto deliveryWithStatusResponseDto =
            new DeliveryWithStatusResponseDto();
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryNo", 1L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "orderNo", 1L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "trackingNo", "123123123");
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryStatus",
            DeliveryStatusEnum.WAIT_DELIVERY.getDeliveryStatus());

        responseDtoList.add(deliveryWithStatusResponseDto);

        Page<DeliveryWithStatusResponseDto> pageList = new PageImpl<>(responseDtoList);

        given(deliveryAdminService.findDeliveryListWithStatusWait(any(Pageable.class)))
            .willReturn(pageList);

        mockMvc.perform(get("/api/admin/deliveries/wait-list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(
                    DeliveryResultMessageEnum.DELIVERY_LIST_SUCCESS_MESSAGE.getResultMessage())));
    }

    @Test
    @DisplayName("배송 서버에 배송 정보 등록 요청 성공")
    void addDeliveryListWithStatusWait() throws Exception {

        List<DeliveryDetailResponseDto> deliveryDetailResponseDtoList = new ArrayList<>();

        DeliveryDetailResponseDto deliveryDetailResponseDto = DeliveryDetailResponseDto.builder()
            .deliveryNo(11L)
            .orderNo(11L)
            .receiverName("testName")
            .receiverAddress("testAddress")
            .receiverDetailAddress("testDetailAddress")
            .receiverPhoneNumber("receiverPhoneNumber")
            .trackingNo("trackingNo")
            .build();

        deliveryDetailResponseDtoList.add(deliveryDetailResponseDto);

        given(deliveryAdminService.sendDeliveryListWithStatusWait()).willReturn(
            deliveryDetailResponseDtoList);

        mockMvc.perform(post("/api/admin/deliveries/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result[0].trackingNo",
                equalTo(deliveryDetailResponseDto.getTrackingNo())));
    }
}