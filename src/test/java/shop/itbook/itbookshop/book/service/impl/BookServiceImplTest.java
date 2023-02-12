package shop.itbook.itbookshop.book.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.service.AladinApiService;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @MockBean
    ProductService mockProductService;

    @MockBean
    ProductCategoryService mockProductCategoryService;

    @MockBean
    AladinApiService mockAladinApiService;

    @MockBean
    FileService mockFileService;


    @MockBean
    BookRepository mockBookRepository;


    ProductBookRequestDto productBookRequestDto;
    BookModifyRequestDto modifyBookRequestDto;

    MockMultipartFile mockImageFile;
    MockMultipartFile mockPdfFile;

    @BeforeEach
    void setUp() throws IOException {
        String imageContentType = "image/png";
        String pdfContentType = "application/pdf";
        String path = "src/test/resources/";

        mockImageFile = new MockMultipartFile("image", "test.png", imageContentType,
            new FileInputStream(path + "test.png"));
        mockPdfFile = new MockMultipartFile("pdf", "test.pdf", pdfContentType,
            new FileInputStream(path + "test.pdf"));

        productBookRequestDto = ProductBookRequestDummy.getProductBookRequest();
        modifyBookRequestDto = ProductBookRequestDummy.getBookModifyRequest();
        ReflectionTestUtils.setField(modifyBookRequestDto, "productName", "객체지향의 거짓과 오해");
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void addBookTest_success() {
        Product product = ProductTransfer.dtoToEntityAdd(productBookRequestDto);
        Book book = BookTransfer.dtoToEntityAdd(productBookRequestDto, product.getProductNo());
        given(mockProductService.addProduct(
            mockProductService.toProductRequestDto(productBookRequestDto), mockImageFile))
            .willReturn(product.getProductNo());
        given(mockBookRepository.save(any(Book.class)))
            .willReturn(book);

        Long actual = bookService.addBook(productBookRequestDto, mockImageFile, mockPdfFile);

        Assertions.assertThat(actual).isEqualTo(product.getProductNo());
    }

//    @Test
//    @DisplayName("도서 수정 테스트")
//    void modifyProductTest_success() {
//        Product product = mock(Product.class);
//        Book book = mock(Book.class);
//
//        given(mockBookRepository.findById(anyLong()))
//            .willReturn(Optional.of(book));
//
//        bookService.modifyBook(product.getProductNo(), modifyBookRequestDto, mockImageFile,
//            mockPdfFile);
//
//        then(mockBookRepository).should().findById(anyLong());
//        then(book).should().setProduct(product);
//        then(book).should().setIsbn(anyString());
//        then(book).should().setPageCount(anyInt());
//        then(book).should().setIsEbook(anyBoolean());
//        then(book).should().setBookCreatedAt(any(LocalDateTime.class));
//    }


    @Test
    @DisplayName("도서 단건 조회 시 실패 테스트 - 도서가 없을 시 예외 발생")
    void findBookTest_failure() {
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> bookService.findBook(1L))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage(BookNotFoundException.MESSAGE);
    }

    @Test
    @DisplayName("도서 엔티티 단건 조회 테스트")
    void findBookEntityTest_success() {

        Product product = ProductTransfer.dtoToEntityAdd(productBookRequestDto);
        Book book = BookTransfer.dtoToEntityAdd(productBookRequestDto, product.getProductNo());
        given(mockProductService.addProduct(
            mockProductService.toProductRequestDto(productBookRequestDto), mockImageFile))
            .willReturn(product.getProductNo());
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.of(book));

        Optional<Book> actualBook = mockBookRepository.findById(1L);

        Assertions.assertThat(actualBook).isPresent();
        Assertions.assertThat(actualBook.get().getProductNo())
            .isEqualTo(product.getProductNo());
    }


}