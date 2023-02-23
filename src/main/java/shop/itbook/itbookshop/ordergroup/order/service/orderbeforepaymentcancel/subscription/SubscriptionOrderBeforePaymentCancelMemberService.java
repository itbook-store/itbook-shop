package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.subscription;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.repository.ProductCouponApplyRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.impl.PointHistoryServiceImpl;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class SubscriptionOrderBeforePaymentCancelMemberService
    extends SubscriptionOrderBeforePaymentCancelTemplate {

    private final OrderProductService orderProductService;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final CategoryCouponApplyRepository categoryCouponApplyRepository;
    private final CouponIssueService couponIssueService;
    private final ProductCouponApplyRepository productCouponApplyRepository;
    private final OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy;
    private final OrderTotalCouponApplyService orderTotalCouponApplyService;
    private final OrderMemberRepository orderMemberRepository;
    private final OrderCancelIncreasePointHistoryService orderCancelIncreasePointHistoryService;

    public SubscriptionOrderBeforePaymentCancelMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        OrderSubscriptionRepository orderSubscriptionRepository,
        OrderRepository orderRepository,
        OrderStatusService orderStatusService,
        OrderProductService orderProductService,
        OrderStatusHistoryService orderStatusHistoryService1,
        CategoryCouponApplyRepository categoryCouponApplyRepository,
        CouponIssueService couponIssueService,
        ProductCouponApplyRepository productCouponApplyRepository,
        OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy,
        OrderTotalCouponApplyService orderTotalCouponApplyService,
        OrderMemberRepository orderMemberRepository,
        OrderCancelIncreasePointHistoryService orderCancelIncreasePointHistoryService) {
        super(orderStatusHistoryService, orderSubscriptionRepository, orderRepository,
            orderStatusService);
        this.orderTotalCouponApplyRepositoy = orderTotalCouponApplyRepositoy;
        this.orderProductService = orderProductService;
        this.orderStatusHistoryService = orderStatusHistoryService1;
        this.categoryCouponApplyRepository = categoryCouponApplyRepository;
        this.couponIssueService = couponIssueService;
        this.productCouponApplyRepository = productCouponApplyRepository;
        this.orderTotalCouponApplyService = orderTotalCouponApplyService;
        this.orderMemberRepository = orderMemberRepository;
        this.orderCancelIncreasePointHistoryService = orderCancelIncreasePointHistoryService;
    }

    @Override
    protected void startUsageProcessing(Order order) {

        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(order.getOrderNo());
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();

        List<OrderProduct> orderProductList =
            orderProductService.findOrderProductsEntityByOrderNo(order.getOrderNo());

        // 주문 상품번호리스트가져와서 각각 상품쿠폰, 카테고리 쿠폰 있는지 확인 후 있으면 사용전상태로 변경
        this.changeCategoryAndProductCouponStatusByCancel(orderProductList,
            orderStatusEnum);

        // 주문번호로 주문총액상품쿠폰 있는지 확인하고 있으면 사용전상태로 변경
        this.changeOrderTotalAmountCouponStatusByCancel(order.getOrderNo());

        // 포인트 적립금액 있는지 확인해서 주문취소차감
        this.addOrderCancelIncreaseDecreasePointHistory(order,
            PointHistoryServiceImpl.DECREASE_POINT_HISTORY);

        // 포인트 차감금액 있는지 확인해서 금액만큼 주문취소적립
        this.addOrderCancelIncreaseDecreasePointHistory(order,
            PointHistoryServiceImpl.INCREASE_POINT_HISTORY);
    }

    private void changeCategoryAndProductCouponStatusByCancel(List<OrderProduct> orderProductList,
                                                              OrderStatusEnum orderStatusEnum) {


        for (OrderProduct orderProduct : orderProductList) {

            Optional<CategoryCouponApply> optionalCategoryCouponApply =
                categoryCouponApplyRepository.findByOrderProduct_OrderProductNo(
                    orderProduct.getOrderProductNo());

            if (Objects.equals(orderStatusEnum, OrderStatusEnum.WAIT_DELIVERY) ||
                Objects.equals(orderStatusEnum, OrderStatusEnum.PAYMENT_COMPLETE)) {
                Product product = orderProduct.getProduct();
                int stock = product.getStock();
                product.setStock(stock + orderProduct.getCount());
            }

            if (optionalCategoryCouponApply.isPresent()) {
                CategoryCouponApply categoryCouponApply = optionalCategoryCouponApply.get();
                couponIssueService.cancelCouponIssue(
                    categoryCouponApply.getCouponIssue().getCouponIssueNo());
            } else {
                Optional<ProductCouponApply> optionalProductCouponApply =
                    productCouponApplyRepository.findById(orderProduct.getOrderProductNo());

                if (optionalProductCouponApply.isPresent()) {
                    ProductCouponApply productCouponApply = optionalProductCouponApply.get();
                    couponIssueService.cancelCouponIssue(productCouponApply.getCouponIssueNo());
                }
            }
        }
    }

    private void changeOrderTotalAmountCouponStatusByCancel(Long orderNo) {

        Optional<OrderTotalCouponApply> optionalOrderTotalCouponApply =
            orderTotalCouponApplyRepositoy.findByOrder_OrderNo(orderNo);

        if (optionalOrderTotalCouponApply.isEmpty()) {
            return;
        }

        OrderTotalCouponApply orderTotalCouponApply = optionalOrderTotalCouponApply.get();
        Long couponIssueNo = orderTotalCouponApply.getCouponIssueNo();

        couponIssueService.cancelCouponIssue(couponIssueNo);
        orderTotalCouponApplyService.cancelOrderTotalCouponApplyAndChangeCouponIssue(couponIssueNo);
    }

    private void addOrderCancelIncreaseDecreasePointHistory(Order order, boolean isDecrease) {

        if (isDecrease && Objects.equals(order.getIncreasePoint(), 0L)) {
            return;
        }

        if (!isDecrease && Objects.equals(order.getDecreasePoint(), 0L)) {
            return;
        }

        Optional<OrderMember> optionalOrderMember =
            orderMemberRepository.findById(order.getOrderNo());

        if (optionalOrderMember.isEmpty()) {
            return;
        }

        OrderMember orderMember = optionalOrderMember.get();
        if (isDecrease) {
            orderCancelIncreasePointHistoryService.savePointHistoryAboutOrderCancelDecrease(
                orderMember.getMember(), order, order.getIncreasePoint());
        } else {
            orderCancelIncreasePointHistoryService.savePointHistoryAboutOrderCancelIncrease(
                orderMember.getMember(), order, order.getDecreasePoint());
        }
    }

}
