package shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookshop.config.ServerConfig;
import shop.itbook.itbookshop.deliverygroup.delivery.adaptor.DeliveryAdaptor;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.request.DeliveryServerRequestDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.exception.DeliveryNoWaitStatusException;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.service.adminapi.DeliveryAdminService;
import shop.itbook.itbookshop.deliverygroup.delivery.transfer.DeliveryTransfer;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.DeliveryStatus;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.exception.DeliveryStatusNotFoundException;
import shop.itbook.itbookshop.deliverygroup.deliverystatus.repository.DeliveryStatusRepository;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.entity.DeliveryStatusHistory;
import shop.itbook.itbookshop.deliverygroup.deliverystatushistory.repository.DeliveryStatusHistoryRepository;

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
    private final DeliveryAdaptor<DeliveryDetailResponseDto> deliveryAdaptor;
    private final ServerConfig serverConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatus(Pageable pageable) {
        return deliveryRepository.findDeliveryListWithStatus(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DeliveryWithStatusResponseDto> findDeliveryListWithStatusWait(Pageable pageable) {
        return deliveryRepository.findDeliveryListWithStatusWait(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<DeliveryDetailResponseDto> sendDeliveryListWithStatusWait() {

        StringBuilder stringBuilder = new StringBuilder();

        List<Delivery> deliveryList = deliveryRepository.findDeliveryEntityListWithStatusWait();

        if (deliveryList.isEmpty()) {
            throw new DeliveryNoWaitStatusException();
        }

        HttpEntity<List<DeliveryServerRequestDto>> http =
            new HttpEntity<>(
                deliveryList.stream()
                    .map(DeliveryTransfer::entityToServerRequestDto).collect(
                        Collectors.toList()));

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(serverConfig.getDeliveryUrl())
            .path(serverConfig.getDeliveryPostPath())
            .encode()
            .build();

        List<DeliveryDetailResponseDto> result =
            deliveryAdaptor.postDeliveryList(uriComponents.toUri(), http);

        Queue<DeliveryDetailResponseDto> deliveryDetailResponseDtoQueue =
            new LinkedList<>(result);

        DeliveryStatus deliveryStatus = deliveryStatusRepository.findByDeliveryStatusEnum(
            DeliveryStatusEnum.DELIVERY_IN_PROGRESS).orElseThrow(
            DeliveryStatusNotFoundException::new);

        deliveryList.forEach(delivery -> {

            delivery.setTrackingNo(
                Objects.requireNonNull(deliveryDetailResponseDtoQueue.peek()).getTrackingNo());

            DeliveryStatusHistory deliveryStatusHistory = DeliveryStatusHistory.builder()
                .delivery(delivery)
                .historyLocation(
                    stringBuilder.append(
                            Objects.requireNonNull(deliveryDetailResponseDtoQueue.peek())
                                .getReceiverAddress())
                        .append(" ")
                        .append(Objects.requireNonNull(deliveryDetailResponseDtoQueue.poll())
                            .getReceiverDetailAddress())
                        .toString())
                .deliveryStatus(deliveryStatus)
                .build();

            stringBuilder.delete(0, stringBuilder.length());

            deliveryRepository.save(delivery);
            deliveryStatusHistoryRepository.save(deliveryStatusHistory);
        });

        return result;
    }
}
