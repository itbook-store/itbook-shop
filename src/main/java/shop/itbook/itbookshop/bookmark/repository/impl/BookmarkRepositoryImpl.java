package shop.itbook.itbookshop.bookmark.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookshop.bookmark.entity.Bookmark;
import shop.itbook.itbookshop.bookmark.entity.QBookmark;
import shop.itbook.itbookshop.bookmark.repository.CustomBookmarkRepository;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * QueryDsl 을 담당하는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class BookmarkRepositoryImpl extends QuerydslRepositorySupport implements
    CustomBookmarkRepository {

    public BookmarkRepositoryImpl() {
        super(Bookmark.class);
    }

    @Override
    public void deleteAllByMemberNo(Long memberNo) {
        QBookmark qBookmark = QBookmark.bookmark;

        delete(qBookmark)
            .where(qBookmark.member.memberNo.eq(memberNo))
            .execute();
    }

    @Override
    public void deleteByMemberNoAndProductNo(Long memberNo, Long productNo) {
        QBookmark qBookmark = QBookmark.bookmark;

        delete(qBookmark)
            .where(qBookmark.member.memberNo.eq(memberNo)
                .and(qBookmark.product.productNo.eq(productNo))
            ).execute();
    }

    @Override
    public Page<BookmarkResponseDto> findAllProductDetailsByMemberNo(Pageable pageable,
                                                                     Long memberNo) {
        QBookmark qBookmark = QBookmark.bookmark;
        QMember qMember = QMember.member;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<BookmarkResponseDto> productListQuery = from(qBookmark)
            .innerJoin(qBookmark.member, qMember)
            .innerJoin(qBookmark.product, qProduct)
            .innerJoin(qProduct.book, qBook)
            .select(Projections.constructor(BookmarkResponseDto.class,
                qBookmark.bookmarkCreatedAt,
                Projections.constructor(ProductDetailsResponseDto.class,
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
                    qProduct.isSubscription,
                    qProduct.isDeleted,
                    qProduct.dailyHits
                )))
            .where(qBookmark.member.memberNo.eq(memberNo))
            .where(qProduct.isDeleted.isFalse());

        List<BookmarkResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        productList.forEach(dto -> dto.getProductDetailsResponseDto().setSelledPrice(
            (long) (dto.getProductDetailsResponseDto().getFixedPrice() *
                ((100 - dto.getProductDetailsResponseDto().getDiscountPercent()) * 0.01))
        ));

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }
}
