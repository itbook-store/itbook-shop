package shop.itbook.itbookshop.bookmark.dummy;

import shop.itbook.itbookshop.bookmark.entity.Bookmark;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 강명관
 * @since 1.0
 */
public class BookmarkDummy {

    private BookmarkDummy() {

    }

    public static Bookmark getBookmark(Member member, Product product) {
        return new Bookmark(member, product);
    }

}
