package shop.itbook.itbookshop.bookmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookshop.bookmark.entity.Bookmark;
import shop.itbook.itbookshop.bookmark.repository.BookmarkRepository;
import shop.itbook.itbookshop.bookmark.service.BookmarkService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidProductException;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * 즐겨찾기 비지니스 로직을 담당하는 서비스 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public boolean addProductInBookmark(BookmarkRequestDto bookmarkRequestDto) {

        boolean checkExist = bookmarkRepository.existsByMember_MemberNoAndProduct_ProductNo(
            bookmarkRequestDto.getMemberNo(),
            bookmarkRequestDto.getProductNo()
        );

        if (checkExist) {
            return false;
        }

        Member member = memberRepository.findById(bookmarkRequestDto.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);

        Product product = productRepository.findById(bookmarkRequestDto.getProductNo())
            .orElseThrow(ProductNotFoundException::new);

        if (product.getIsDeleted()) {
            throw new InvalidProductException();
        }

        Bookmark bookmark = new Bookmark(member, product);
        bookmarkRepository.save(bookmark);

        return true;
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteProductInBookmark(BookmarkRequestDto bookmarkRequestDto) {
        bookmarkRepository.deleteByMemberNoAndProductNo(
            bookmarkRequestDto.getMemberNo(),
            bookmarkRequestDto.getProductNo()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAllProductInBookmark(Long memberNo) {
        bookmarkRepository.deleteAllByMemberNo(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BookmarkResponseDto> getAllProductInBookmark(Pageable pageable,
                                                             Long memberNo) {

        if (!memberRepository.existsById(memberNo)) {
            throw new MemberNotFoundException();
        }

        return bookmarkRepository.findAllProductDetailsByMemberNo(pageable, memberNo);
    }
}
