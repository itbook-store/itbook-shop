package shop.itbook.itbookshop.bookmark.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.book.dummy.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookshop.bookmark.dummy.BookmarkDummy;
import shop.itbook.itbookshop.bookmark.entity.Bookmark;
import shop.itbook.itbookshop.bookmark.repository.BookmarkRepository;
import shop.itbook.itbookshop.bookmark.service.impl.BookmarkServiceImpl;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidProductException;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 강명관
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class BookmarkServiceTest {

    BookmarkService bookmarkService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    BookmarkRepository bookmarkRepository;

    Member member;

    Product product;

    Book book;

    Bookmark bookmark;

    BookmarkRequestDto bookmarkRequestDto;

    ProductDetailsResponseDto productDetailsResponseDto;

    BookmarkResponseDto bookmarkResponseDto;


    @BeforeEach
    void setUp() {
        member = MemberDummy.getMember1();
        product = ProductDummy.getProductSuccess();
        book = BookDummy.getBookSuccess();

        bookmarkRequestDto = new BookmarkRequestDto(
            member.getMemberNo(),
            product.getProductNo()
        );

        bookmark = BookmarkDummy.getBookmark(member, product);

        productDetailsResponseDto = ProductDummy.getProductDetailsResponseDto();

        bookmarkResponseDto =
            new BookmarkResponseDto(LocalDateTime.now(), productDetailsResponseDto);

        bookmarkService =
            new BookmarkServiceImpl(bookmarkRepository, memberRepository, productRepository);

    }

    @DisplayName("즐겨찾기에 상품 등록 테스트")
    @Test
    void addProductInBookmark() {
        // given
        given(bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            member.getMemberNo(),
            product.getProductNo()
        )).willReturn(Boolean.FALSE);
        given(memberRepository.findById(member.getMemberNo())).willReturn(Optional.of(member));
        given(productRepository.findById(product.getProductNo())).willReturn(Optional.of(product));
        given(bookmarkRepository.save(any(Bookmark.class))).willReturn(bookmark);

        // when
        boolean expect = bookmarkService.addProductInBookmark(bookmarkRequestDto);

        // then
        assertTrue(expect);

    }

    @DisplayName("즐겨찾기에 상품 등록_이미 등록된 상품_실패 테스트")
    @Test
    void addProductInBookmark_thenAlreadyExistProduct_returnFalse() {
        // given
        given(bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            member.getMemberNo(),
            product.getProductNo()
        )).willReturn(Boolean.TRUE);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        // when
        boolean expect = bookmarkService.addProductInBookmark(bookmarkRequestDto);

        // then
        assertFalse(expect);

    }

    @DisplayName("즐겨찾기 상품 등록_회원이 존재하지 않는 경우 실패 테스트")
    @Test
    void addProductInBookmark_notExistMember_thenMemberNotFoundException() {
        // given
        given(bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            member.getMemberNo(),
            product.getProductNo()
        )).willReturn(Boolean.FALSE);

        given(memberRepository.findById(anyLong())).willThrow(MemberNotFoundException.class);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        // when, then
        assertThatThrownBy(() -> bookmarkService.addProductInBookmark(bookmarkRequestDto))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("즐겨찾기 상품 등록_상품이 존재하지 않는 경우 실패 테스트")
    @Test
    void addProductInBookmark_notExistProduct_thenMemberNotFoundException() {
        // given
        given(bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            member.getMemberNo(),
            product.getProductNo()
        )).willReturn(Boolean.FALSE);

        given(memberRepository.findById(member.getMemberNo())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willThrow(ProductNotFoundException.class);

        // when, then
        assertThatThrownBy(() -> bookmarkService.addProductInBookmark(bookmarkRequestDto))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("즐겨찾기 상품 등록_유효하지 않은 상품의 경우 실패 테스트")
    @Test
    void addProductInBookmark_invalidProduct_thenThrowIvalidProductException() {
        // given
        given(bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            member.getMemberNo(),
            product.getProductNo()
        )).willReturn(Boolean.FALSE);

        given(memberRepository.findById(member.getMemberNo())).willReturn(Optional.of(member));
        given(productRepository.findById(product.getProductNo())).willReturn(Optional.of(product));

        ReflectionTestUtils.setField(product, "isDeleted", Boolean.TRUE);
        // when, then
        assertThatThrownBy(() -> bookmarkService.addProductInBookmark(bookmarkRequestDto))
            .isInstanceOf(InvalidProductException.class);
    }

    @DisplayName("즐겨찾기에 상품 삭제 테스트")
    @Test
    void deleteProductInBookmarkTest() {
        // given, when
        bookmarkService.deleteProductInBookmark(bookmarkRequestDto);

        // then
        verify(bookmarkRepository, times(1)).deleteByMemberNoAndProductNo(
            bookmark.getMember().getMemberNo(),
            bookmark.getProduct().getProductNo()
        );
    }

    @DisplayName("즐겨찾기 회원번호로 모든 상품 삭제 테스트")
    @Test
    void deleteAllProductInBookmarkTest() {
        // given, when
        bookmarkService.deleteAllProductInBookmark(member.getMemberNo());

        // then
        verify(bookmarkRepository, times(1)).deleteAllByMemberNo(
            bookmark.getMember().getMemberNo()
        );
    }

    @DisplayName("회원번호를 통해 모든 상품 상세 리스트 조회 성공 테스트")
    @Test
    void getAllProductInBookmarkTest() {
        // given
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);


        given(memberRepository.existsById(member.getMemberNo()))
            .willReturn(true);

        given(bookmarkRepository.findAllProductDetailsByMemberNo(
            pageable,
            bookmark.getMember().getMemberNo()
        )).willReturn(new PageImpl<>(List.of(bookmarkResponseDto), pageable, 0));

        // when
        Page<BookmarkResponseDto> allProductInBookmark =
            bookmarkService.getAllProductInBookmark(
                pageable,
                member.getMemberNo()
            );

        List<BookmarkResponseDto> content = allProductInBookmark.getContent();

        // then
        assertThat(content).hasSize(1);
        assertThat(content.get(0).getBookmarkCreateAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(content.get(0).getProductDetailsResponseDto().getProductNo()).isEqualTo(
            product.getProductNo());
    }

    @DisplayName("회원번호를 통해 모든 상품 상세 리스트 조회_회원정보 없음_실패 테스트")
    @Test
    void getAllProductInBookmarkTest_notExistMember_thenMemberNotFoundException() {
        // given
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);


        given(memberRepository.existsById(anyLong())).willThrow(MemberNotFoundException.class);

        given(bookmarkRepository.findAllProductDetailsByMemberNo(
            pageable,
            bookmark.getMember().getMemberNo()
        )).willReturn(new PageImpl<>(List.of(bookmarkResponseDto), pageable, 0));

        // when, then
        assertThatThrownBy(() -> bookmarkService.getAllProductInBookmark(pageable, anyLong()))
            .isInstanceOf(MemberNotFoundException.class);

    }
}