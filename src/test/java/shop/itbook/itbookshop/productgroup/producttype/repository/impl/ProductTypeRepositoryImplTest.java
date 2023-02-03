package shop.itbook.itbookshop.productgroup.producttype.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.dummy.MemberDestinationDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
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
    Product product;
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    Pageable pageable;
    @Autowired
    private MemberDestinationRepository memberDestinationRepository;

    @BeforeEach
    void setUp() {

        pageable = PageRequest.of(0, Integer.MAX_VALUE);

        product = ProductDummy.getProductSuccess();
        productRepository.save(product);

        Book book = BookDummy.getBookSuccess();
        book.setProductNo(product.getProductNo());
        bookRepository.save(book);

        entityManager.flush();
        entityManager.clear();
    }


    // TODO 주문 DB 변경되면 할 것
    @Disabled
    @Test
    @DisplayName("베스트셀러 조회 성공")
    void find_BestSeller_Test() {

        Member member = MemberDummy.getMember1();
        memberRepository.save(member);

        MemberDestination memberDestination = MemberDestinationDummy.getMemberDestination();
        memberDestinationRepository.save(memberDestination);

        Order order = new Order(member, memberDestination, false);
        orderRepository.save(order);

        OrderProduct orderProduct = new OrderProduct(order, product, 1, false);
        orderProductRepository.save(orderProduct);

        Product new_product = ProductDummy.getProductSuccess();
        productRepository.save(new_product);

        Book new_book = BookDummy.getBookSuccess();
        new_book.setProductNo(new_product.getProductNo());
        new_book.setBookCreatedAt(LocalDateTime.now().minusDays(10));
        bookRepository.save(new_book);

        List<ProductDetailsResponseDto> discountBookList =
            productTypeRepository.findBestSellerBookListAdmin(pageable).getContent();

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(1);
        Assertions.assertThat(discountBookList.get(0).getBookCreatedAt())
            .isBetween(LocalDateTime.now().minusDays(7), LocalDateTime.now());
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

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(1);
        Assertions.assertThat(discountBookList.get(0).getBookCreatedAt())
            .isBetween(LocalDateTime.now().minusDays(7), LocalDateTime.now());

    }

    @Test
    @DisplayName("할인 도서 조회 성공")
    void find_Discount_Test() {

        Product no_discount_product = ProductDummy.getProductSuccess();
        no_discount_product.setDiscountPercent(0.0);
        productRepository.save(no_discount_product);

        Book book2 = BookDummy.getBookSuccess();
        book2.setProductNo(no_discount_product.getProductNo());
        book2.setIsbn("book2Isbn");
        bookRepository.save(book2);

        Product sale_product = ProductDummy.getProductSuccess();
        sale_product.setDiscountPercent(30.0);
        productRepository.save(sale_product);

        Book book3 = BookDummy.getBookSuccess();
        book3.setProductNo(sale_product.getProductNo());
        book3.setIsbn("book3Isbn");
        bookRepository.save(book3);

        List<ProductDetailsResponseDto> discountBookList =
            productTypeRepository.findDiscountBookListAdmin(pageable).getContent();

        Assertions.assertThat(discountBookList).isNotEmpty().hasSize(2);
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

        Assertions.assertThat(popularityList).isNotEmpty().hasSize(13);
        Assertions.assertThat(popularityList.get(0).getDailyHits())
            .isEqualTo(11L);
    }

}