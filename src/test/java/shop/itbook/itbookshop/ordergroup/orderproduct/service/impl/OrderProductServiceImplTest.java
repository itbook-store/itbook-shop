package shop.itbook.itbookshop.ordergroup.orderproduct.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderProductServiceImpl.class)
class OrderProductServiceImplTest {

    @Autowired
    OrderProductService orderProductService;

    @MockBean
    OrderService orderService;

    @MockBean
    ProductService productService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    OrderProductRepository orderProductRepository;

    OrderProductDetailResponseDto orderProductDetailResponseDto;

    Order dummyOrder;

    Product dummyProduct;

    OrderProduct dummyOrderProduct;

    @BeforeEach
    void setUp() {
        dummyOrder = OrderDummy.getOrder();
        orderRepository.save(dummyOrder);

        dummyProduct = ProductDummy.getProductSuccess();
        productRepository.save(dummyProduct);

        dummyOrderProduct = OrderProductDummy.createOrderProduct(dummyOrder, dummyProduct);
        orderProductRepository.save(dummyOrderProduct);

    }

    @Test
    void addOrderProduct() {
        OrderProduct orderProduct = dummyOrderProduct;

        given(orderProductRepository.save(any(OrderProduct.class)))
            .willReturn(orderProduct);

        Long actual = orderProductService.addOrderProduct(dummyOrder, dummyProduct, 1,
            dummyProduct.getFixedPrice()).getOrderProductNo();

        assertThat(actual).isEqualTo(orderProduct.getOrderProductNo());
    }

    @Test
    void findOrderProductsByOrderNo() {

        orderProductDetailResponseDto = new OrderProductDetailResponseDto();

        ReflectionTestUtils.setField(orderProductDetailResponseDto, "orderProductNo", 1L);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "productNo", 1L);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "productName", "테스트상품");
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "count", 1);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "productPrice", 1_000L);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "fileThumbnailsUrl", "testUrl");
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "couponName", "이달의 할인쿠폰");
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "couponAmount", 1L);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "couponPercent", 20);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "fixedPrice", 1_000L);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "discountPercent", 20.0);
        ReflectionTestUtils.setField(orderProductDetailResponseDto, "sellingPrice", 800L);

        given(orderProductRepository.findOrderProductsByOrderNo(anyLong())).willReturn(
            List.of(orderProductDetailResponseDto));

        List<OrderProductDetailResponseDto> orderProductList =
            orderProductService.findOrderProductsByOrderNo(1L);

        assertThat(orderProductList.size()).isEqualTo(1);

    }

    @Test
    void findOrderProductsEntityByOrderNo() {
        given(orderProductRepository.findByOrder_OrderNo(anyLong())).willReturn(
            List.of(dummyOrderProduct));

        List<OrderProduct> orderProductList =
            orderProductService.findOrderProductsEntityByOrderNo(1L);

        assertThat(orderProductList.size()).isEqualTo(1);
    }
}