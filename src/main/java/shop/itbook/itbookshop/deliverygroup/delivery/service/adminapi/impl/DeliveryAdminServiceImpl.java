package shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.deliverygroup.delivery.adaptor.DeliveryAdaptor;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryOrderNoResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.DeliveryAdminService;
import shop.itbook.itbookshop.deliverygroup.delivery.transfer.DeliveryTransfer;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.entity.DeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.exception.DeliveryStatusNotFoundException;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * DeliveryService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class DeliveryAdminServiceImpl implements DeliveryAdminService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;
    private final DeliveryAdaptor deliveryAdaptor;
    private String deliveryUrl;
    @Value("${itbook-server-url.delivery-post-path}")
    private String deliveryPostPath;
    private static final StringBuffer stringBuffer = new StringBuffer();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DeliveryOrderNoResponseDto addDelivery(Order order) {

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DeliveryDetailResponseDto sendDelivery(DeliveryServerRequestDto deliveryRequestDto) {

        HttpEntity<DeliveryServerRequestDto> http =
            new HttpEntity<>(deliveryRequestDto);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
            .fromUriString("http://localhost:8083" + deliveryPostPath).encode();

        ResponseEntity<CommonResponseBody<DeliveryDetailResponseDto>> responseEntity =
            deliveryAdaptor.postDelivery(uriComponentsBuilder.toUriString(), http);

        // TODO: 2023/01/13 DB 저장 로직. 

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeliveryWithStatusResponseDto> findDeliveryListWithStatus() {
        return deliveryRepository.findDeliveryListWithStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait() {
        return deliveryRepository.findDeliveryListWithStatusWait();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<DeliveryDetailResponseDto> sendDeliveryListWithStatusWait() {

        List<Delivery> deliveryList = deliveryRepository.findDeliveryEntityListWithStatusWait();

        HttpEntity<List<DeliveryServerRequestDto>> http =
            new HttpEntity<>(
                deliveryList.stream()
                    .map(DeliveryTransfer::entityToServerRequestDto).collect(
                        Collectors.toList()));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
            .fromUriString("http://localhost:8083" + deliveryPostPath).encode();

        ResponseEntity<CommonResponseBody<List<DeliveryDetailResponseDto>>> responseEntity =
            deliveryAdaptor.postDeliveryList(uriComponentsBuilder.toUriString(), http);

        List<DeliveryDetailResponseDto> result =
            Objects.requireNonNull(responseEntity.getBody()).getResult();

        Queue<DeliveryDetailResponseDto> deliveryDetailResponseDtoQueue =
            new LinkedList<>(result);

        deliveryList.stream().forEach(delivery -> {
            DeliveryStatus deliveryStatus = deliveryStatusRepository.findByDeliveryStatusEnum(
                DeliveryStatusEnum.DELIVERY_IN_PROGRESS).orElseThrow(
                DeliveryStatusNotFoundException::new);

            DeliveryStatusHistory deliveryStatusHistory = DeliveryStatusHistory.builder()
                .delivery(delivery)
                .historyLocation(
                    stringBuffer.append(deliveryDetailResponseDtoQueue.poll().getReceiverAddress())
                        .append(" ")
                        .append(deliveryDetailResponseDtoQueue.poll().getReceiverDetailAddress())
                        .toString())
                .deliveryStatus(deliveryStatus)
                .build();
        });

        return result;
    }
}
