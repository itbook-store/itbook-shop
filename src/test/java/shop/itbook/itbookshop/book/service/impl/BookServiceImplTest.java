package shop.itbook.itbookshop.book.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookBooleanResponseDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.dummy.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.AladinApiService;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.file.service.FileService;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * @author ?????????
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
        modifyBookRequestDto = BookDummy.getBookModifyRequest();
        ReflectionTestUtils.setField(modifyBookRequestDto, "productName", "??????????????? ????????? ??????");
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void addBookTest_success() {
        Product product = ProductTransfer.dtoToEntity(productBookRequestDto);
        Book book = BookTransfer.dtoToEntityAdd(productBookRequestDto, product.getProductNo());
        given(mockProductService.addProduct(
            mockProductService.toProductRequestDto(productBookRequestDto), mockImageFile))
            .willReturn(product.getProductNo());
        given(mockBookRepository.save(any(Book.class)))
            .willReturn(book);
        Long actualProductNo =
            bookService.addBook(productBookRequestDto, mockImageFile, mockPdfFile);
        Assertions.assertThat(actualProductNo).isEqualTo(product.getProductNo());
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void modifyProductTest_success() {
        Product product = mock(Product.class);
        Book book = mock(Book.class);

//        ReflectionTestUtils.setField(modifyBookRequestDto, "fileEbookUrl", "url");
//        ReflectionTestUtils.setField(modifyBookRequestDto, "isEbook", Boolean.TRUE);


        given(mockProductService.updateProduct(any(BookModifyRequestDto.class), anyLong()))
            .willReturn(product);

        given(mockBookRepository.findById(product.getProductNo())).willReturn(Optional.of(book));

        bookService.modifyBook(product.getProductNo(), modifyBookRequestDto, mockImageFile,
            mockPdfFile);

        then(mockProductService).should().updateProduct(any(BookModifyRequestDto.class), anyLong());
        verify(mockFileService, times(2)).uploadFile(any(MultipartFile.class), anyString());

        then(book).should().setPublisherName(anyString());
        then(book).should().setAuthorName(anyString());
    }

    @Test
    @DisplayName("?????? ?????? ????????? - thumbnail??? null??? ?????? ???")
    void modifyProductTestExistsEbook_success() {
        Product product = mock(Product.class);
        Book book = mock(Book.class);

        given(mockProductService.updateProduct(any(BookModifyRequestDto.class), anyLong()))
            .willReturn(product);

        given(mockBookRepository.findById(product.getProductNo())).willReturn(Optional.of(book));

        bookService.modifyBook(product.getProductNo(), modifyBookRequestDto, null,
            mockPdfFile);

        then(mockProductService).should().updateProduct(any(BookModifyRequestDto.class), anyLong());
        then(mockFileService).should().uploadFile(any(MultipartFile.class), anyString());
        then(book).should().setPublisherName(anyString());
        then(book).should().setAuthorName(anyString());
        then(mockBookRepository).should().save(book);
    }

    @Test
    @DisplayName("DB??? isbn?????? ?????? ?????? ?????? ?????? ??? ??????")
    void findBookExistsInDBTest_failure() {

        BookBooleanResponseDto bookBooleanResponseDto = new BookBooleanResponseDto(true);
        String isbn = "testIsbn";

        given(mockBookRepository.existsBookByIsbn(anyString())).willReturn(true);

        BookBooleanResponseDto existsInDBByIsbn = bookService.isExistsInDBByIsbn(isbn);

        Assertions.assertThat(existsInDBByIsbn.getIsExists())
            .isEqualTo(bookBooleanResponseDto.getIsExists());
    }

    @Test
    @DisplayName("???????????? isbn?????? ?????? ?????? ?????? ?????? ??? ??????")
    void findBookExistsInAladinTest_success() {
        String isbn = "testIsbn";

        bookService.isExistsInAladinByIsbn(isbn);
        then(mockAladinApiService).should().getBookDetails(anyString());
    }

//    @Test
//    @DisplayName("???????????? ???????????? ?????? isbn?????? ?????? ?????? ?????? ?????? ??? ??????")
//    void findBookExistsInAladinTest_failure() {
//        String isbn = "---";
//
//        bookService.isExistsInAladinByIsbn(isbn);
//
//        given(mockAladinApiService.getBookDetails(anyString())).willThrow(
//            BookNotFoundException.class);
//
//        Assertions.assertThatThrownBy(() -> bookService.isExistsInAladinByIsbn(isbn))
//            .isInstanceOf(BookNotFoundException.class)
//            .hasMessage(BookNotFoundException.MESSAGE);
//        then(mockAladinApiService).should().getBookDetails(anyString());
//    }


    @Test
    @DisplayName("?????? ?????? ?????? ??? ?????? ????????? - ????????? ?????? ??? ?????? ??????")
    void findBookTest_failure() {
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> bookService.findBook(1L))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage(BookNotFoundException.MESSAGE);
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ?????????")
    void findBookEntityTest_success() {

        Product product = ProductTransfer.dtoToEntity(productBookRequestDto);
        Book book = BookTransfer.dtoToEntityAdd(productBookRequestDto, product.getProductNo());
        given(mockProductService.addProduct(
            mockProductService.toProductRequestDto(productBookRequestDto), mockImageFile))
            .willReturn(product.getProductNo());
        given(mockProductService.findProductEntity(anyLong())).willReturn(product);
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.of(book));

        Product actualProduct = mockProductService.findProductEntity(1L);
        Optional<Book> actualBook = mockBookRepository.findById(1L);

        Assertions.assertThat(actualProduct).isEqualTo(product);
        Assertions.assertThat(actualBook).isPresent();
        Assertions.assertThat(actualBook.get().getProductNo())
            .isEqualTo(product.getProductNo());
        Assertions.assertThat(actualBook.get().getProduct()).isEqualTo(book.getProduct());
        Assertions.assertThat(actualBook.get().getPageCount()).isEqualTo(book.getPageCount());
        Assertions.assertThat(actualBook.get().getBookCreatedAt())
            .isEqualTo(book.getBookCreatedAt());
        Assertions.assertThat(actualBook.get().getIsEbook()).isEqualTo(book.getIsEbook());
        Assertions.assertThat(actualBook.get().getEbookUrl()).isEqualTo(book.getEbookUrl());
        Assertions.assertThat(actualBook.get().getPublisherName())
            .isEqualTo(book.getPublisherName());
        Assertions.assertThat(actualBook.get().getAuthorName()).isEqualTo(book.getAuthorName());
    }

    @Test
    @DisplayName("?????? ???????????? ?????? ?????? ?????????")
    void findBookTest_success() {

        Product product = ProductTransfer.dtoToEntity(productBookRequestDto);
        BookDetailsResponseDto book = BookDummy.getBookDetailsResponseDto();
        given(mockProductService.addProduct(
            mockProductService.toProductRequestDto(productBookRequestDto), mockImageFile))
            .willReturn(product.getProductNo());
        given(mockBookRepository.findBook(anyLong())).willReturn(Optional.of(book));

        Optional<BookDetailsResponseDto> actualBook = mockBookRepository.findBook(1L);

        Assertions.assertThat(actualBook).isPresent();
        Assertions.assertThat(actualBook.get().getProductNo())
            .isEqualTo(product.getProductNo());
        Assertions.assertThat(actualBook.get().getProductName())
            .isEqualTo(product.getName());
        Assertions.assertThat(actualBook.get().getSimpleDescription())
            .isEqualTo(product.getSimpleDescription());
        Assertions.assertThat(actualBook.get().getDetailsDescription())
            .isEqualTo(product.getDetailsDescription());
        Assertions.assertThat(actualBook.get().getIsSelled())
            .isEqualTo(product.getIsSelled());
        Assertions.assertThat(actualBook.get().getIsForceSoldOut())
            .isEqualTo(product.getIsForceSoldOut());
        Assertions.assertThat(actualBook.get().getIsPointApplying())
            .isEqualTo(product.getIsPointApplying());
        Assertions.assertThat(actualBook.get().getStock())
            .isEqualTo(product.getStock());
        Assertions.assertThat(actualBook.get().getIncreasePointPercent())
            .isEqualTo(product.getIncreasePointPercent());
        Assertions.assertThat(actualBook.get().getRawPrice())
            .isEqualTo(product.getRawPrice());
        Assertions.assertThat(actualBook.get().getFixedPrice())
            .isEqualTo(product.getFixedPrice());
        Assertions.assertThat(actualBook.get().getDiscountPercent())
            .isEqualTo(product.getDiscountPercent());
        Assertions.assertThat(actualBook.get().getFileThumbnailsUrl())
            .isEqualTo(product.getThumbnailUrl());
        Assertions.assertThat(actualBook.get().getIsbn())
            .isEqualTo(book.getIsbn());
        Assertions.assertThat(actualBook.get().getPageCount())
            .isEqualTo(book.getPageCount());
        Assertions.assertThat(actualBook.get().getBookCreatedAt())
            .isEqualTo(book.getBookCreatedAt());
        Assertions.assertThat(actualBook.get().getIsEbook())
            .isEqualTo(book.getIsEbook());
        Assertions.assertThat(actualBook.get().getIsSubscription())
            .isEqualTo(book.getIsSubscription());
        Assertions.assertThat(actualBook.get().getIsPointApplyingBasedSellingPrice())
            .isEqualTo(book.getIsPointApplyingBasedSellingPrice());
        Assertions.assertThat(actualBook.get().getFileEbookUrl())
            .isEqualTo(book.getFileEbookUrl());
        Assertions.assertThat(actualBook.get().getPublisherName())
            .isEqualTo(book.getPublisherName());
        Assertions.assertThat(actualBook.get().getAuthorName())
            .isEqualTo(book.getAuthorName());
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ?????? ????????? - ?????? ?????? BookNotFoundException ?????? ??????")
    void findBookEntityTest_failure() {

        given(mockBookRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findBookEntity(1L))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage(BookNotFoundException.MESSAGE);

    }


}