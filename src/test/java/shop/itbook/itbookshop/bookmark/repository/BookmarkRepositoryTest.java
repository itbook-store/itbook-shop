package shop.itbook.itbookshop.bookmark.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.book.dummy.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookshop.bookmark.dummy.BookmarkDummy;
import shop.itbook.itbookshop.bookmark.entity.Bookmark;
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
class BookmarkRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

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

    @DisplayName("즐겨찾기 테이블 세이브 테스트")
    @Test
    void saveTest() {

        // given
        Bookmark bookmark = BookmarkDummy.getBookmark(memberDummy, productDummy);

        // when
        Bookmark save = bookmarkRepository.save(bookmark);

        // then
        assertThat(save.getMember()).isEqualTo(memberDummy);
        assertThat(save.getProduct()).isEqualTo(productDummy);

    }

    @DisplayName("즐겨찾기 findById 테스트")
    @Test
    void findByIdTest() {

        // given
        Bookmark bookmark = BookmarkDummy.getBookmark(memberDummy, productDummy);
        Bookmark save = bookmarkRepository.save(bookmark);

        // when
        Bookmark byId = bookmarkRepository.findById(save.getBookmarkNo()).get();

        // then
        assertThat(byId).isEqualTo(save);

    }

    @DisplayName("회원번호와 상품번호로 즐겨찾기 특정 상품 삭제 테스트")
    @Test
    void deleteByMemberNoAndProductNoTest() {

        // given
        Bookmark bookmark = BookmarkDummy.getBookmark(memberDummy, productDummy);
        Bookmark save = bookmarkRepository.save(bookmark);

        // when
        bookmarkRepository.deleteByMemberNoAndProductNo(
            memberDummy.getMemberNo(),
            productDummy.getProductNo()
        );

        // then
        List<Bookmark> all = bookmarkRepository.findAll();

        assertThat(all).isEmpty();
    }

    @DisplayName("deleteAllByMemberNo 테스트 멤버 아이디로 모든 즐겨찾기 삭제")
    @Test
    void deleteAllByMemberNoTest() {

        // given
        Bookmark bookmark = BookmarkDummy.getBookmark(memberDummy, productDummy);
        bookmarkRepository.save(bookmark);

        // when
        bookmarkRepository.deleteAllByMemberNo(memberDummy.getMemberNo());

        // then
        List<Bookmark> all = bookmarkRepository.findAll();
        assertThat(all).isEmpty();
    }


    @DisplayName("회원번호와 상품번호로 이미 존재하는지 테스트")
    @Test
    void existsByMember_MemberNoAndProduct_ProductNoTest() {
        // given
        Bookmark bookmark = BookmarkDummy.getBookmark(memberDummy, productDummy);
        Bookmark save = bookmarkRepository.save(bookmark);

        // when
        boolean result = bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            memberDummy.getMemberNo(),
            productDummy.getProductNo()
        );

        // then
        assertTrue(result);
    }

    @DisplayName("회원번호를 통해 테이블의 모든 상품들의 상세 정보")
    @Test
    void findAllProductDetailsByMemberNoTest() {
        // given
        Bookmark bookmark = BookmarkDummy.getBookmark(memberDummy, productDummy);
        Bookmark save = bookmarkRepository.save(bookmark);


        // when
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Page<BookmarkResponseDto> productList =
            bookmarkRepository.findAllProductDetailsByMemberNo(pageable, memberDummy.getMemberNo());

        // then
        List<BookmarkResponseDto> content = productList.getContent();
        assertThat(content).hasSize(1);
        assertThat(content.get(0).getBookmarkCreateAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(content.get(0).getProductDetailsResponseDto().getProductNo())
            .isEqualTo(productDummy.getProductNo());
    }
}