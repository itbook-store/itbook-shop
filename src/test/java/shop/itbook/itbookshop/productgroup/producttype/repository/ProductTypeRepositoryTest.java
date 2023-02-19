package shop.itbook.itbookshop.productgroup.producttype.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.book.dummy.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductTypeRepositoryTest {
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
    @DisplayName("관리자가 베스트셀러 조회 성공")
    void findBestSellerForAdminTest() {

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

        Page<ProductDetailsResponseDto> bestSellerList =
            productTypeRepository.findBestSellerBookListAdmin(pageable);

        Assertions.assertThat(bestSellerList).isNotEmpty().hasSize(2);

        ProductDetailsResponseDto content = bestSellerList.getContent().get(0);

        Assertions.assertThat(content.getProductNo()).isEqualTo(product3.getProductNo());
        Assertions.assertThat(content.getFixedPrice()).isEqualTo(product3.getFixedPrice());
    }

    @Test
    @DisplayName("사용자가 베스트셀러 조회 성공")
    void findBestSellerForUserTest() {

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

        Page<ProductDetailsResponseDto> bestSellerList =
            productTypeRepository.findBestSellerBookListUser(pageable);

        Assertions.assertThat(bestSellerList).isNotEmpty().hasSize(2);

        ProductDetailsResponseDto content = bestSellerList.getContent().get(0);

        Assertions.assertThat(content.getProductNo()).isEqualTo(product3.getProductNo());
        Assertions.assertThat(content.getFixedPrice()).isEqualTo(product3.getFixedPrice());
    }

    @Test
    @DisplayName("관리자가 신간 조회 성공")
    void findNewIssueForAdminTest() {

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
    @DisplayName("사용자가 신간 조회 성공")
    void findNewIssueForUserTest() {

        Product new_product = ProductDummy.getProductSuccess();
        productRepository.save(new_product);

        Book new_book = BookDummy.getBookSuccess();
        new_book.setProductNo(new_product.getProductNo());
        new_book.setIsbn("newBookIsbn");
        new_book.setBookCreatedAt(LocalDateTime.now().minusDays(10));
        bookRepository.save(new_book);

        List<ProductDetailsResponseDto> discountBookList =
            productTypeRepository.findNewBookListUser(pageable).getContent();

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(2);
        Assertions.assertThat(discountBookList.get(0).getBookCreatedAt())
            .isBetween(LocalDateTime.now().minusDays(7), LocalDateTime.now());

    }

    @Test
    @DisplayName("관리자가 할인 도서 조회 성공")
    void findDiscountForAdminTest() {

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
    @DisplayName("사용자가 할인 도서 조회 성공")
    void findDiscountForUserTest() {

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
            productTypeRepository.findDiscountBookListUser(pageable).getContent();

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(3);
        Assertions.assertThat(discountBookList.get(0).getDiscountPercent())
            .isEqualTo(sale_product.getDiscountPercent());
    }

    @Test
    @DisplayName("관리자가 인기 조회 성공")
    void findPopularityForAdminTest() {

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

    @Test
    @DisplayName("사용자가 인기 조회 성공")
    void findPopularityForUserTest() {

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
            productTypeRepository.findPopularityBookListUser(pageable).getContent();

        Assertions.assertThat(popularityList).isNotEmpty().hasSize(14);
        Assertions.assertThat(popularityList.get(0).getDailyHits())
            .isEqualTo(11L);
    }
}