package shop.itbook.itbookshop.cart.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.book.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.cart.dto.request.CartRequestDto;
import shop.itbook.itbookshop.cart.entity.Cart;
import shop.itbook.itbookshop.cart.exception.CartNotFountException;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 강명관
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CartRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Membership membershipDummy;

    MemberStatus memberStatusDummy;

    Member memberDummy;

    Product productDummy;

    Book bookDummy;


    @BeforeEach
    void setUp() {
        membershipDummy = MembershipDummy.getMembership();
        memberStatusDummy = MemberStatusDummy.getNormalMemberStatus();
        memberDummy = MemberDummy.getMember1();
        productDummy = ProductDummy.getProductSuccess();
        bookDummy = BookDummy.getBookSuccess();

        membershipRepository.save(membershipDummy);
        memberStatusRepository.save(memberStatusDummy);

        memberDummy.setMembership(membershipDummy);
        memberDummy.setMemberStatus(memberStatusDummy);
        memberRepository.save(memberDummy);

        productRepository.save(productDummy);

        bookDummy.setProductNo(productDummy.getProductNo());
        bookRepository.save(bookDummy);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @DisplayName("장바구니 세이브 테스트")
    @Test
    void saveTest() {
        // given
        Cart cart = new Cart(memberDummy, productDummy);
        Cart save = cartRepository.save(cart);

        // then
        assertThat(save.getMember().getMemberNo()).isEqualTo(memberDummy.getMemberNo());
        assertThat(save.getProduct().getProductNo()).isEqualTo(productDummy.getProductNo());

    }

    @DisplayName("장바구니 findById 테스트")
    @Test
    void findById() {

        // given
        Cart cart = new Cart(memberDummy, productDummy);
        Cart save = cartRepository.save(cart);

        // when

        Cart findCart = cartRepository.findById(save.getPk())
            .orElseThrow(CartNotFountException::new);

        // then

        assertThat(findCart.getMember().getMemberNo()).isEqualTo(memberDummy.getMemberNo());
        assertThat(findCart.getProduct().getProductNo()).isEqualTo(productDummy.getProductNo());

    }

    @DisplayName("장바구니 existById 테스트")
    @Test
    void existByIdTest() {
        // given
        Cart cart = new Cart(memberDummy, productDummy);
        Cart save = cartRepository.save(cart);

        // when

        boolean result = cartRepository.existsById(
            new Cart.Pk(memberDummy.getMemberNo(), productDummy.getProductNo()));

        // then

        assertThat(result).isTrue();
    }

    @DisplayName("회원 번호를 톹해 장바구니 상품 리스트 갖고오는 테스트")
    @Test
    void findProductCartListByMemberNoTest() {

        // given

        Cart cart = new Cart(memberDummy, productDummy);
        cartRepository.save(cart);


        // when

        List<ProductDetailsResponseDto> productCartListByMemberNo =
            cartRepository.findProductCartListByMemberNo(memberDummy.getMemberNo());

        // then

        assertThat(productCartListByMemberNo).hasSize(1);

        assertThat(productCartListByMemberNo.get(0).getProductNo()).isEqualTo(
            productDummy.getProductNo());
    }

    @DisplayName("회원번호와 상품번호를 통해 장바구니 상품 삭제 테스트")
    @Test
    void deleteByIdTest() {
        // given
        Cart cart = new Cart(memberDummy, productDummy);
        cartRepository.save(cart);

        CartRequestDto cartRequestDto = new CartRequestDto(
            memberDummy.getMemberNo(),
            productDummy.getProductNo()
        );

        // when
        cartRepository.deleteById(
            new Cart.Pk(
                cartRequestDto.getMemberNo(),
                cartRequestDto.getProductNo()
            )
        );

        // then
        List<ProductDetailsResponseDto> productCartListByMemberNo =
            cartRepository.findProductCartListByMemberNo(cartRequestDto.getMemberNo());

        assertThat(productCartListByMemberNo).isEmpty();

    }

    @DisplayName("해당 회원 번호에 따른 장바구니 상품 모두 삭제 테스트")
    @Test
    void deleteAllByMemberNo() {
        // given
        Cart cart = new Cart(memberDummy, productDummy);
        cartRepository.save(cart);

        // when
        cartRepository.deleteAllByMemberNo(memberDummy.getMemberNo());

        // then
        List<ProductDetailsResponseDto> productCartListByMemberNo =
            cartRepository.findProductCartListByMemberNo(memberDummy.getMemberNo());

        assertThat(productCartListByMemberNo).isEmpty();
    }

    // 카운트를 수정하는 쿼리를 작성하고, 그렇게하면 쿼리 하나로 다 쓸 수 있고, 그렇게 되면 js 로 input 값 변경 이벤트 달아서 쿼리 나가게
}