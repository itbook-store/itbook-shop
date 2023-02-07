package shop.itbook.itbookshop.cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.book.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.cart.dto.request.CartModifyRequestDto;
import shop.itbook.itbookshop.cart.dto.request.CartRequestDto;
import shop.itbook.itbookshop.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookshop.cart.dummy.CartDummy;
import shop.itbook.itbookshop.cart.entity.Cart;
import shop.itbook.itbookshop.cart.exception.CartNotFountException;
import shop.itbook.itbookshop.cart.repository.CartRepository;
import shop.itbook.itbookshop.cart.service.impl.CartServiceImpl;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 강명관
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class CartServiceTest {

    CartService cartService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CartRepository cartRepository;

    CartRequestDto cartRequestDto;

    Member member;

    Product product;

    Book book;

    Cart cart;

    ProductDetailsResponseDto productDetailsResponseDto;

    CartProductDetailsResponseDto cartProductDetailsResponseDto;

    List<CartProductDetailsResponseDto> cartProductDetailsResponseDtoList;

    @BeforeEach
    void setUp() {
        member = MemberDummy.getMember1();
        product = ProductDummy.getProductSuccess();
        book = BookDummy.getBookSuccess();

        cartRequestDto = new CartRequestDto(
            member.getMemberNo(),
            product.getProductNo()
        );

        cart = CartDummy.getCart(member, product);

        productDetailsResponseDto = ProductDummy.getProductDetailsResponseDto();

        cartProductDetailsResponseDto =
            new CartProductDetailsResponseDto(1, productDetailsResponseDto);

        cartProductDetailsResponseDtoList = List.of(cartProductDetailsResponseDto);

        cartService = new CartServiceImpl(cartRepository, memberRepository, productRepository);

    }

    @DisplayName("장바구니 상품 등록 테스트")
    @Test
    void registerCart() {

        // given
        given(memberRepository.findById(member.getMemberNo())).willReturn(Optional.of(member));
        given(productRepository.findById(product.getProductNo())).willReturn(Optional.of(product));
        given(cartRepository.save(any(Cart.class))).willReturn(cart);

        // when
        boolean actual = cartService.registerCart(cartRequestDto);

        // then
        assertTrue(actual);
    }

    @DisplayName("장바구니 상품등록 실패 테스트")
    @Test
    void registerCart_existProduct_thenReturnFalse() {
        // given
        given(cartRepository.existsById(cart.getPk())).willReturn(true);

        // when
        boolean actual = cartService.registerCart(cartRequestDto);

        // then
        assertFalse(actual);
    }

    @DisplayName("장바구니 상품 등록 그러나 회원 번호 없음")
    @Test
    void registerCart_notExistMember_thenThrowMemberNotFoundException() {

        // given
        given(cartRepository.existsById(cart.getPk())).willReturn(false);
        given(memberRepository.findById(anyLong())).willThrow(MemberNotFoundException.class);

        // then
        assertThatThrownBy(() -> cartService.registerCart(cartRequestDto))
            .isInstanceOf(MemberNotFoundException.class);

    }

    @DisplayName("장바구니 상품 등록 그러나 상품 번호 없음")
    @Test
    void registerCart_notExistProduct_thenThrowProductNotFoundException() {

        // given
        given(cartRepository.existsById(cart.getPk())).willReturn(false);
        given(memberRepository.findById(member.getMemberNo())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willThrow(ProductNotFoundException.class);

        // then
        assertThatThrownBy(() -> cartService.registerCart(cartRequestDto))
            .isInstanceOf(ProductNotFoundException.class);

    }

    @DisplayName("장바구니 상품 가져오기 성공 테스트")
    @Test
    void getProductList() {
        // given
        given(cartRepository.findProductCartListByMemberNo(member.getMemberNo()))
            .willReturn(cartProductDetailsResponseDtoList);

        // when
        List<CartProductDetailsResponseDto> productList =
            cartService.getProductList(member.getMemberNo());

        // then

        assertThat(productList).hasSize(1);
        assertThat(productList.get(0)).isEqualTo(cartProductDetailsResponseDto);
    }

    @DisplayName("장바구니 해당 상품 삭제 테스트")
    @Test
    void deleteProduct() {
        // given, when
        cartService.deleteProduct(cartRequestDto);

        // then
        verify(cartRepository, times(1)).deleteById(cart.getPk());

    }

    @DisplayName("장바구니 상품 모두 삭제 테스트")
    @Test
    void deleteAllProduct() {

        // given
        Long memberNo = cart.getPk().getMemberNo();

        // when
        cartService.deleteAllProduct(memberNo);

        // then
        verify(cartRepository, times(1)).deleteAllByMemberNo(memberNo);

    }

    @DisplayName("장바구니 상품 갯수 수정 테스트")
    @Test
    void modifyProductCount() {
        // given
        CartModifyRequestDto cartModifyRequestDto = new CartModifyRequestDto(
            cart.getPk().getMemberNo(),
            cart.getProduct().getProductNo(),
            10
        );

        given(cartRepository.findById(cart.getPk())).willReturn(Optional.of(cart));

        // when
        cartService.modifyProductCount(cartModifyRequestDto);

        // then
        assertThatNoException();
    }

    @DisplayName("장바구니 상품 갯수 수정 시, 해당 장바구니 상품을 찾지 못하는 경우 테스트")
    @Test
    void modifyProductCount_notExistCart_thenCartNotFoundException() {
        // given
        CartModifyRequestDto cartModifyRequestDto = new CartModifyRequestDto(
            cart.getPk().getMemberNo(),
            cart.getProduct().getProductNo(),
            10
        );

        given(cartRepository.findById(cart.getPk())).willThrow(CartNotFountException.class);

        // when, then
        Assertions.assertThatThrownBy(() -> cartService.modifyProductCount(cartModifyRequestDto))
            .isInstanceOf(CartNotFountException.class);
    }
}