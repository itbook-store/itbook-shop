package shop.itbook.itbookshop.book.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.book.dto.response.FindBookListResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.adminapi.BookAdminService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * ProductService 인터페이스를 구현한 도서 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookAdminServiceImpl implements BookAdminService {

    private final BookRepository bookRepository;
    private final ProductRepository productRepository;


    /**
     * {@inheritDoc}
     */

    @Override
    public Long addBook(AddProductBookRequestDto requestDto, Long productNo) {
        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);
        return book.getProductNo();
    }

    @Override
    @Transactional
    public Long addBook(AddProductBookRequestDto requestDto) {
        Product product = ProductTransfer.dtoToEntityAdd(requestDto);
        productRepository.save(product);
        Long productNo = product.getProductNo();
        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);
        return book.getProductNo();
    }

    @Override
    public List<FindBookListResponseDto> findBookList() {
        List<FindBookListResponseDto> bookList = bookRepository.findBookList();
        for (FindBookListResponseDto b : bookList) {
            b.setSelledPrice((long) (b.getFixedPrice() * ((100 - b.getDiscountPercent()) * 0.01)));
            b.setThumbnailsName(
                b.getFileThumbnailsUrl().substring(b.getFileThumbnailsUrl().lastIndexOf("/") + 1));
        }
        return bookList;
    }
}
