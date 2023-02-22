package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.dto.CouponApplyDto;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForCouponIssueApply;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.CanNotSaveRedisException;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderAfterPaymentSuccessMemberService
    extends SubscriptionOrderAfterPaymentSuccessTemplate {

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final CouponIssueService couponIssueService;
    private final OrderTotalCouponApplyService orderTotalCouponApplyService;
    private final OrderMemberRepository orderMemberRepository;
    private final OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;

    public SubscriptionOrderAfterPaymentSuccessMemberService(
        OrderStatusHistoryService orderStatusHistoryService, RedisTemplate redisTemplate,
        ObjectMapper objectMapper, CouponIssueService couponIssueService,
        OrderTotalCouponApplyService orderTotalCouponApplyService,
        OrderMemberRepository orderMemberRepository,
        OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService) {
        super(orderStatusHistoryService);
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.couponIssueService = couponIssueService;
        this.orderTotalCouponApplyService = orderTotalCouponApplyService;
        this.orderMemberRepository = orderMemberRepository;
        this.orderIncreaseDecreasePointHistoryService = orderIncreaseDecreasePointHistoryService;
    }

    @Override
    protected void startUsageProcessing(Order order) {
        String productAndCategoryCouponApplyDtoJson = (String) redisTemplate.opsForHash()
            .get("productAndCategoryCouponApplyDto", String.valueOf(order.getOrderNo()));

        String orderTotalCouponApplyDtoJson = (String) redisTemplate.opsForHash()
            .get("orderTotalCouponApplyDto", String.valueOf(order.getOrderNo()));

        CouponApplyDto productAndCategoryCouponApplyDto = null;
        CouponApplyDto orderTotalCouponApplyDto = null;

        try {
            if (Objects.nonNull(productAndCategoryCouponApplyDtoJson)) {

                productAndCategoryCouponApplyDto =
                    objectMapper.readValue(productAndCategoryCouponApplyDtoJson,
                        CouponApplyDto.class);
            }

            if (Objects.nonNull(orderTotalCouponApplyDtoJson)) {

                orderTotalCouponApplyDto =
                    objectMapper.readValue(orderTotalCouponApplyDtoJson, CouponApplyDto.class);
            }
        } catch (JsonProcessingException e) {
            throw new CanNotSaveRedisException();
        }

        usingCouponIssue(productAndCategoryCouponApplyDto, orderTotalCouponApplyDto, order);
        savePointHistoryAboutMember(order);

    }

    private void usingCouponIssue(CouponApplyDto productAndCategoryCouponApplyDto,
                                  CouponApplyDto orderTotalCouponApplyDto, Order order) {

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

    private void savePointHistoryAboutMember(Order order) {

        Optional<OrderMember> optionalOrderMember =
            orderMemberRepository.findById(order.getOrderNo());
        if (!optionalOrderMember.isPresent()) {
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
}
