package shop.itbook.itbookshop.cart.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.cart.dto.request.CartModifyRequestDto;
import shop.itbook.itbookshop.cart.dto.request.CartRequestDto;
import shop.itbook.itbookshop.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookshop.cart.entity.Cart;
import shop.itbook.itbookshop.cart.exception.CartNotFountException;
import shop.itbook.itbookshop.cart.repository.CartRepository;
import shop.itbook.itbookshop.cart.service.CartService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * 장바구니 비지니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public boolean registerCart(CartRequestDto cartRequestDto) {

        boolean checkExistProduct = cartRepository.existsById(
            new Cart.Pk(
                cartRequestDto.getMemberNo(),
                cartRequestDto.getProductNo()
            )
        );

        if (checkExistProduct) {
            return false;
        }

        Member member = memberRepository.findById(cartRequestDto.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);

        Product product = productRepository.findById(cartRequestDto.getProductNo())
            .orElseThrow(ProductNotFoundException::new);

        Cart cart = new Cart(member, product);

        cartRepository.save(cart);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CartProductDetailsResponseDto> getProductList(Long memberNo) {
        return cartRepository.findProductCartListByMemberNo(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteProduct(CartRequestDto cartRequestDto) {

        cartRepository.deleteById(
            new Cart.Pk(
                cartRequestDto.getMemberNo(),
                cartRequestDto.getProductNo()
            )
        );

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAllProduct(Long memberNo) {
        cartRepository.deleteAllByMemberNo(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void modifyProductCount(CartModifyRequestDto cartModifyRequestDto) {
        Cart cartProduct = cartRepository.findById(
            new Cart.Pk(
                cartModifyRequestDto.getMemberNo(),
                cartModifyRequestDto.getProductNo()
            )
        ).orElseThrow(CartNotFountException::new);

        cartProduct.changeProductCount(cartModifyRequestDto.getProductCount());

    }
}
