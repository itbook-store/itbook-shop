package shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.config.ServerConfig;
import shop.itbook.itbookshop.deliverygroup.delivery.adaptor.DeliveryAdaptor;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.DeliveryAdminService;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.dummy.DeliveryStatusDummy;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;

/**
 * The type Delivery admin service impl test.
 *
 * @author 정재원
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import({DeliveryAdminServiceImpl.class, ServerConfig.class})
class DeliveryAdminServiceImplTest {

    @Autowired
    DeliveryAdminService deliveryService;
    @MockBean
    DeliveryRepository deliveryRepository;
    @MockBean
    DeliveryAdaptor deliveryAdaptor;
    @MockBean
    DeliveryStatusRepository deliveryStatusRepository;
    @MockBean
    DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;
    @MockBean
    ServerConfig serverConfig;

    @Test
    @DisplayName("서버에 배송 정보 리스트를 보내고 등록 성공")
    void addDeliveryTest() {

        String testTrackingNo = "6557042215056853289";

        DeliveryServerRequestDto deliveryRequestDto = DeliveryServerRequestDto.builder()
            .orderNo(1L)
            .receiverName("테스트 수령인")
            .receiverAddress("테스트 주소")
            .receiverDetailAddress("테스 상세 주소")
            .receiverPhoneNumber("030-111-222")
            .build();

        DeliveryDetailResponseDto deliveryResponseDto = DeliveryDetailResponseDto.builder()
            .deliveryNo(47L)
            .orderNo(1L)
            .receiverName("테스트 수령인")
            .receiverAddress("테스트 주소")
            .receiverDetailAddress("테스 상세 주소")
            .receiverPhoneNumber("030-111-222")
            .trackingNo(testTrackingNo)
            .build();

        List<Delivery> deliveryList = new ArrayList<>();
        deliveryList.add(DeliveryDummy.createDelivery(OrderDummy.getOrder()));

        given(deliveryRepository.findDeliveryEntityListWithStatusWait()).willReturn(deliveryList);

        List<DeliveryDetailResponseDto> deliveryDetailResponseDtoList =
            new ArrayList<>();

        deliveryDetailResponseDtoList.add(deliveryResponseDto);

        given(deliveryAdaptor.postDeliveryList(any(), any(HttpEntity.class))).willReturn(
            deliveryDetailResponseDtoList);
        given((deliveryStatusRepository.findByDeliveryStatusEnum(
            any(DeliveryStatusEnum.class)))).willReturn(
            Optional.of(DeliveryStatusDummy.getDummyWait()));

        given(serverConfig.getDeliveryUrl()).willReturn("url");

        List<DeliveryDetailResponseDto> responseDtoList =
            deliveryService.sendDeliveryListWithStatusWait();

        assertThat(responseDtoList.get(0).getReceiverName()).isEqualTo(
            deliveryResponseDto.getReceiverName());
        assertThat(responseDtoList.get(0).getTrackingNo()).isEqualTo(testTrackingNo);
    }

    @Test
    @DisplayName("배송 대기 중인 배송 정보들 조회 성공")
    void findDeliveryListTest() {

        DeliveryWithStatusResponseDto deliveryWithStatusResponseDto =
            new DeliveryWithStatusResponseDto();
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryNo", 1L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "orderNo", 1L);
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "trackingNo", "123123123");
        ReflectionTestUtils.setField(deliveryWithStatusResponseDto, "deliveryStatus", "배송 대기");

        List<DeliveryWithStatusResponseDto> deliveryWithStatusResponseDtoList = new ArrayList<>();
        deliveryWithStatusResponseDtoList.add(deliveryWithStatusResponseDto);

        Page<DeliveryWithStatusResponseDto> responseDtoPage =
            new PageImpl<>(deliveryWithStatusResponseDtoList);

        given(deliveryRepository.findDeliveryListWithStatusWait(any())).willReturn(
            responseDtoPage);

        assertThat(
            deliveryService.findDeliveryListWithStatusWait(any(Pageable.class))
                .getContent()).isEqualTo(deliveryWithStatusResponseDtoList);
    }
}