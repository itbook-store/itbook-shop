package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.memberapply;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.dto.CouponApplyDto;
import shop.itbook.itbookshop.ordergroup.order.dto.CouponApplyDtosAboutProductAndTotal;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForCouponIssueApply;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.CanNotSaveRedisException;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DefaultMemberApplyWhenOrderAfterPaymentSuccess
    implements MemberApplicableWhenOrderAfterPaymentSuccess {

    private final CouponIssueService couponIssueService;
    private final OrderTotalCouponApplyService orderTotalCouponApplyService;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private final OrderMemberRepository orderMemberRepository;
    private final OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;

    @Override
    public void useCoupon(Order order) {
        CouponApplyDtosAboutProductAndTotal couponApplyDtosAboutProductAndTotal =
            this.getCouponApplyDtosFromRedis(order);

        CouponApplyDto orderTotalCouponApplyDto =
            couponApplyDtosAboutProductAndTotal.getOrderTotalCouponApplyDto();

        CouponApplyDto productAndCategoryCouponApplyDto =
            couponApplyDtosAboutProductAndTotal.getProductAndCategoryCouponApplyDto();

        if (Objects.nonNull(productAndCategoryCouponApplyDto)) {
            for (InfoForCouponIssueApply info : productAndCategoryCouponApplyDto.getInfoForCouponIssueApplyList()) {
                couponIssueService.saveCouponApplyAboutCategoryAndProduct(info.getCouponIssueNo(),
                    info.getOrderProductNo());
            }
        }

        if (Objects.nonNull(orderTotalCouponApplyDto)) {
            orderTotalCouponApplyService.saveOrderTotalCouponApplyAndChangeCouponIssue(
                orderTotalCouponApplyDto.getInfoForCouponIssueApplyList().get(0).getCouponIssueNo(),
                order);
        }
    }

    @Override
    public void usePoint(Order order) {
        Optional<OrderMember> optionalOrderMember =
            orderMemberRepository.findById(order.getOrderNo());
        if (optionalOrderMember.isEmpty()) {
            return;
        }

        OrderMember orderMember = optionalOrderMember.get();
        Member member = orderMember.getMember();

        Long increasePoint = order.getIncreasePoint();
        Long decreasePoint = order.getDecreasePoint();

        if (!Objects.equals(decreasePoint, 0L)) {
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderDecrease(member,
                order, decreasePoint);
        }

        if (!Objects.equals(increasePoint, 0L)) {
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderIncrease(member,
                order, increasePoint);
        }
    }

    private CouponApplyDtosAboutProductAndTotal getCouponApplyDtosFromRedis(Order order) {

        CouponApplyDtosAboutProductAndTotal couponApplyDtosAboutProductAndTotal =
            new CouponApplyDtosAboutProductAndTotal();

        String productAndCategoryCouponApplyDtoJson = (String) redisTemplate.opsForHash()
            .get("productAndCategoryCouponApplyDto", String.valueOf(order.getOrderNo()));

        String orderTotalCouponApplyDtoJson = (String) redisTemplate.opsForHash()
            .get("orderTotalCouponApplyDto", String.valueOf(order.getOrderNo()));

        try {
            if (Objects.nonNull(productAndCategoryCouponApplyDtoJson)) {
                couponApplyDtosAboutProductAndTotal.setProductAndCategoryCouponApplyDto(
                    objectMapper.readValue(productAndCategoryCouponApplyDtoJson,
                        CouponApplyDto.class));
            }

            if (Objects.nonNull(orderTotalCouponApplyDtoJson)) {

                couponApplyDtosAboutProductAndTotal.setOrderTotalCouponApplyDto(
                    objectMapper.readValue(orderTotalCouponApplyDtoJson, CouponApplyDto.class));
            }
        } catch (JsonProcessingException e) {
            throw new CanNotSaveRedisException();
        }

        return couponApplyDtosAboutProductAndTotal;
    }
}
