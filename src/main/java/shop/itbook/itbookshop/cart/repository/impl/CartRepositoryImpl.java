package shop.itbook.itbookshop.cart.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.cart.entity.Cart;
import shop.itbook.itbookshop.cart.entity.QCart;
import shop.itbook.itbookshop.cart.repository.CustomCartRepository;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * 장바구니 queryDsl을 사용하기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class CartRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCartRepository {

    /**
     * queryDsl을 사용하기 위한 설정.
     *
     * @author 강명관
     */
    public CartRepositoryImpl() {
        super(Cart.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDetailsResponseDto> findProductCartListByMemberNo(Long memberNo) {

        QCart qCart = QCart.cart;
        QMember qMember = QMember.member;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        return from(qCart)
            .innerJoin(qCart.member, qMember)
            .innerJoin(qCart.product, qProduct)
            .innerJoin(qProduct.book, qBook)
            .select(Projections.constructor(ProductDetailsResponseDto.class,
                qProduct.productNo,
                qProduct.name,
                qProduct.simpleDescription,
                qProduct.detailsDescription,
                qProduct.isSelled,
                qProduct.isForceSoldOut,
                qProduct.stock,
                qProduct.increasePointPercent,
                qProduct.rawPrice,
                qProduct.fixedPrice,
                qProduct.discountPercent,
                qProduct.thumbnailUrl,
                qBook.isbn,
                qBook.pageCount,
                qBook.bookCreatedAt,
                qBook.isEbook,
                qBook.ebookUrl,
                qBook.publisherName,
                qBook.authorName,
                qProduct.isPointApplyingBasedSellingPrice,
                qProduct.isPointApplying,
                qProduct.isSubscription)
            )
            .where(qMember.memberNo.eq(memberNo).and(qProduct.isSelled.eq(Boolean.TRUE)))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllByMemberNo(Long memberNo) {

        QCart qCart = QCart.cart;

        delete(qCart)
            .where(qCart.member.memberNo.eq(memberNo))
            .execute();
    }
}
