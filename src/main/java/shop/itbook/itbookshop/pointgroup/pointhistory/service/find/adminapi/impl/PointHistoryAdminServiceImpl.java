package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderCrudService;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryAdminServiceImpl implements PointHistoryAdminService {

    private final PointHistoryRepository pointHistoryRepository;
    private final OrderCrudService orderCrudService;

    @Override
    public Page<PointHistoryListResponseDto> findPointHistoryList(Pageable pageable,
                                                                  PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointHistoryRepository.findPointHistoryListResponseDto(pageable,
            pointIncreaseDecreaseContentEnum);
    }

    @Override
    public Page<PointHistoryListResponseDto> findPointHistoryListBySearch(Pageable pageable,
                                                                          PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                                          String searchWord) {
        return pointHistoryRepository.findPointHistoryListResponseDtoThroughSearch(pageable,
            pointIncreaseDecreaseContentEnum, searchWord);
    }

    @Override
    public PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsDto(
        Long pointHistoryNo) {


        return pointHistoryRepository.findPointHistoryGiftDetailsResponseDto(pointHistoryNo);
    }

    @Override
    public PointHistoryGradeDetailsResponseDto findPointHistoryGradeDetailsDto(
        Long pointHistoryNo) {

        return pointHistoryRepository.findMembershipResponseDtoThroughPointHistory(pointHistoryNo);
    }

    @Override
    public ReviewResponseDto findReviewResponseDtoForPointHistoryReviewDetails(
        Long pointHistoryNo) {

        return pointHistoryRepository.findPointHistoryReviewDetailsDto(pointHistoryNo);
    }

    @Override
    public PointHistoryCouponDetailsResponseDto findPointHistoryCouponDetailsDto(
        Long pointHistoryNo) {

        return pointHistoryRepository.findPointHistoryCouponDetailsDto(pointHistoryNo);
    }

    @Override
    public OrderDetailsResponseDto findOrderDetailsResponseDtoForPointHistoryOrderDetails(
        Long pointHistoryNo) {

        Long orderNo = pointHistoryRepository.findOrderNoThroughPointHistory(pointHistoryNo);
        return orderCrudService.findOrderDetails(orderNo);
    }
}
