package shop.itbook.itbookshop.ordergroup.orderproduct.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * OrderProductService 인터페이스의 구현체입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addOrderProduct(Order order, Product product, Integer productCnt,
                                Long productPrice) {

        Optional<OrderProduct> optOrderProduct = orderProductRepository.findByOrder(order);

        if (optOrderProduct.isPresent()) {
            OrderProduct orderProduct = optOrderProduct.get();
            orderProduct.setProductPrice(productPrice);
            return;
        }

        OrderProduct orderProduct = OrderProduct.builder()
            .order(order)
            .product(product)
            .count(productCnt)
            .productPrice(productPrice)
            .build();
        orderProductRepository.save(orderProduct);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderProductDetailResponseDto> findOrderProductsByOrderNo(Long orderNo) {
        return orderProductRepository.findOrderProductsByOrderNo(orderNo);
    }
}
