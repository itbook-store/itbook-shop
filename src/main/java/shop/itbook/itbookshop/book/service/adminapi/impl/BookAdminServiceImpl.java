package shop.itbook.itbookshop.book.service.adminapi.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.adminapi.BookAdminService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.fileservice.FileService;
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
    private final FileService fileService;
    @Value("${object.storage.folder-path.thumbnail}")
    private String folderPathThumbnail;

    @Value("${object.storage.folder-path.ebook}")
    private String folderPathEbook;


    /**
     * {@inheritDoc}
     */

    @Override
    @Transactional
    public Long addBook(AddProductBookRequestDto requestDto, MultipartFile thumbnails,
                        MultipartFile ebook) {
        String thumbnailUrl = fileService.uploadFile(thumbnails, folderPathThumbnail);
        requestDto.setFileThumbnailsUrl(thumbnailUrl);
        Product product = ProductTransfer.dtoToEntityAdd(requestDto);
        productRepository.save(product);
        Long productNo = product.getProductNo();
        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);
        if (!Objects.isNull(ebook)) {
            String ebookUrl =
                fileService.uploadFile(ebook, folderPathEbook);
            requestDto.setFileEbookUrl(ebookUrl);
        }
        return book.getProductNo();
    }

    @Override
    public List<FindBookResponseDto> findBookList() {
        List<FindBookResponseDto> bookList = bookRepository.findBookList();
        for (FindBookResponseDto b : bookList) {
            b.setSelledPrice((long) (b.getFixedPrice() * ((100 - b.getDiscountPercent()) * 0.01)));
            String fileThumbnailsUrl = b.getFileThumbnailsUrl();
            b.setThumbnailsName(
                fileThumbnailsUrl.substring(fileThumbnailsUrl.lastIndexOf("/") + 1));
        }
        return bookList;
    }

    @Override
    public FindBookResponseDto findBook(Long id) {
        return bookRepository.findBook(id);
    }
}
