package shop.itbook.itbookshop.productgroup.producttype.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.book.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductTypeRepositoryImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    Product product1;
    Product product2;
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    Pageable pageable;

    @BeforeEach
    void setUp() {

        pageable = PageRequest.of(0, Integer.MAX_VALUE);

        product1 = ProductDummy.getProductSuccess();
        productRepository.save(product1);

        Book book1 = BookDummy.getBookSuccess();
        book1.setProductNo(product1.getProductNo());
        bookRepository.save(book1);

        product2 = ProductDummy.getProductSuccess();
        productRepository.save(product2);

        Book book2 = BookDummy.getBookSuccess();
        book2.setProductNo(product2.getProductNo());
        book2.setIsbn("book2Isbn");
        bookRepository.save(book2);

        entityManager.flush();
        entityManager.clear();
    }


    @Test
    @DisplayName("베스트셀러 조회 성공")
    void find_BestSeller_Test() {

        // 32일 전에 주문한 책이(product1) 가장 많이 주문되었지만 베스트셀러에는 포함이 되지 않옴 (현재-1일~현재-31일 책만 통계내기 때문)
        Order order = OrderDummy.getOrder();
        order.setOrderCreatedAt(LocalDateTime.now().minusDays(90));
        orderRepository.save(order);

        OrderProduct orderProduct1 = new OrderProduct(order, product1, 10, 10000L);
        orderProductRepository.save(orderProduct1);

        Order order2 = OrderDummy.getOrder();
        order2.setOrderCreatedAt(LocalDateTime.now().minusDays(13));

        orderRepository.save(order2);
        OrderProduct orderProduct2 = new OrderProduct(order2, product2, 5, 10000L);
        orderProductRepository.save(orderProduct2);

        Product product3 = ProductDummy.getProductSuccess();
        productRepository.save(product3);
        Book new_book = BookDummy.getBookSuccess();
        new_book.setIsbn("12312");
        new_book.setProductNo(product3.getProductNo());
        bookRepository.save(new_book);

        OrderProduct orderProduct3 = new OrderProduct(order2, product3, 7, 10000L);
        orderProductRepository.save(orderProduct3);

        List<ProductDetailsResponseDto> bestSellerList =
            productTypeRepository.findBestSellerBookListAdmin(pageable).getContent();

        Assertions.assertThat(bestSellerList).isNotEmpty().hasSize(2);
        Assertions.assertThat(bestSellerList.get(0).getProductNo())
            .isEqualTo(product3.getProductNo());
    }

    @Test
    @DisplayName("신간 조회 성공")
    void find_New_Issue_Test() {

        Product new_product = ProductDummy.getProductSuccess();
        productRepository.save(new_product);

        Book new_book = BookDummy.getBookSuccess();
        new_book.setProductNo(new_product.getProductNo());
        new_book.setIsbn("newBookIsbn");
        new_book.setBookCreatedAt(LocalDateTime.now().minusDays(10));
        bookRepository.save(new_book);

        List<ProductDetailsResponseDto> discountBookList =
            productTypeRepository.findNewBookListAdmin(pageable).getContent();

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(2);
        Assertions.assertThat(discountBookList.get(0).getBookCreatedAt())
            .isBetween(LocalDateTime.now().minusDays(7), LocalDateTime.now());

    }

    @Test
    @DisplayName("할인 도서 조회 성공")
    void find_Discount_Test() {

        Product no_discount_product = ProductDummy.getProductSuccess();
        no_discount_product.setDiscountPercent(0.0);
        productRepository.save(no_discount_product);

        Book book_no_discount = BookDummy.getBookSuccess();
        book_no_discount.setProductNo(no_discount_product.getProductNo());
        book_no_discount.setIsbn("Isbn");
        bookRepository.save(book_no_discount);

        Product sale_product = ProductDummy.getProductSuccess();
        sale_product.setDiscountPercent(30.0);
        productRepository.save(sale_product);

        Book book3 = BookDummy.getBookSuccess();
        book3.setProductNo(sale_product.getProductNo());
        book3.setIsbn("book3Isbn");
        bookRepository.save(book3);

        List<ProductDetailsResponseDto> discountBookList =
            productTypeRepository.findDiscountBookListAdmin(pageable).getContent();

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(3);
        Assertions.assertThat(discountBookList.get(0).getDiscountPercent())
            .isEqualTo(sale_product.getDiscountPercent());
    }

    @Test
    @DisplayName("인기 조회 성공")
    void find_Popularity_Test() {

        for (Integer num = 0; num < 12; num++) {
            Product product_dummy = ProductDummy.getProductSuccess();
            Book book_dummy = BookDummy.getBookSuccess();

            product_dummy.setDailyHits(Long.valueOf(num));
            productRepository.save(product_dummy);

            book_dummy.setProductNo(product_dummy.getProductNo());
            book_dummy.setIsbn("isbn" + num);
            bookRepository.save(book_dummy);
        }

        List<ProductDetailsResponseDto> popularityList =
            productTypeRepository.findPopularityBookListAdmin(pageable).getContent();

        Assertions.assertThat(popularityList).isNotEmpty().hasSize(14);
        Assertions.assertThat(popularityList.get(0).getDailyHits())
            .isEqualTo(11L);
    }

}