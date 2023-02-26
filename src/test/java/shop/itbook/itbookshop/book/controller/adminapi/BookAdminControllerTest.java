package shop.itbook.itbookshop.book.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.service.AladinApiService;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.Item;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.BookResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(BookAdminController.class)
class BookAdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService mockProductService;

    @MockBean
    BookService mockBookService;

    @MockBean
    AladinApiService aladinApiService;

    @MockBean
    ProductSearchService productSearchService;
    ProductBookRequestDto bookRequestDto;
    ProductBookRequestDto modifyProductRequestDto;

    MockMultipartFile thumbnailsPart;
    MockMultipartFile ebookPart;
    MockMultipartFile requestDtoPart;
    MockMultipartFile requestDtoPartFailure;

    @BeforeEach
    void setUp() throws IOException {
        String FILE_PATH = "src/test/resources/";

        bookRequestDto = ProductBookRequestDummy.getProductBookRequest();
        modifyProductRequestDto = ProductBookRequestDummy.getProductBookRequest();
        ReflectionTestUtils.setField(modifyProductRequestDto, "productName", "객체지향의 거짓과 오해");

        String jsonRequestDto = objectMapper.writeValueAsString(bookRequestDto);

        thumbnailsPart = new MockMultipartFile("thumbnails", "test.png",
            MediaType.IMAGE_PNG_VALUE, new FileInputStream(FILE_PATH + "test.png"));
        ebookPart = new MockMultipartFile("ebook", "test.pdf",
            MediaType.APPLICATION_PDF_VALUE, new FileInputStream(FILE_PATH + "test.pdf"));
        requestDtoPart = new MockMultipartFile("requestDto", "",
            MediaType.APPLICATION_JSON_VALUE, jsonRequestDto.getBytes());

        ProductBookRequestDto bookRequestDtoIsbnNull =
            ProductBookRequestDummy.getProductBookRequest();
        bookRequestDtoIsbnNull.setIsbn(null);

        requestDtoPartFailure = new MockMultipartFile("requestDto", "",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(bookRequestDtoIsbnNull).getBytes());
    }

    @Test
    @DisplayName("도서 등록 성공 테스트")
    void addBookTest_success() throws Exception {
        Long testProductNo = 1L;

        given(mockBookService.addBook(any(ProductBookRequestDto.class),
            any(MultipartFile.class), any(MultipartFile.class)))
            .willReturn(testProductNo);

        mockMvc.perform(multipart("/api/admin/products/books")
                .file(thumbnailsPart)
                .file(ebookPart)
                .file(requestDtoPart)
                .characterEncoding("UTF-8"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(BookResultMessageEnum.ADD_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("도서 등록 실패 테스트 - notnull 컬럼인 isbn에 null 값 저장")
    void addBookTest_failure() throws Exception {

        mockMvc.perform(multipart("/api/admin/products/books")
                .file(thumbnailsPart)
                .file(ebookPart)
                .file(requestDtoPartFailure)
                .characterEncoding("UTF-8"))
            .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("도서 수정 성공 테스트")
    void modifyBookTest_success() throws Exception {
        Long productNo = 1L;

        given(mockProductService.findProductEntity(productNo))
            .willReturn(ProductDummy.getProductSuccess());
        given(mockBookService.findBookEntity(productNo))
            .willReturn(BookTransfer.dtoToEntityAdd(bookRequestDto, productNo));

        ReflectionTestUtils.setField(bookRequestDto, "authorName", "testAuthor");
        String json = objectMapper.writeValueAsString(bookRequestDto);
        requestDtoPart = new MockMultipartFile("requestDto", "",
            MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        MockMultipartHttpServletRequestBuilder builder =
            multipart("/api/admin/products/books/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder
                .file(thumbnailsPart)
                .file(requestDtoPart))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(BookResultMessageEnum.MODIFY_SUCCESS.getMessage())));
    }


    @DisplayName("DB에서 Isbn 중복 체크 테스트")
    @Test
    void checkIsbnDuplicatedInDBTest() throws Exception {

        String isbn = "9791156754039";
        mockMvc.perform(get("/api/admin/products/books/check-exist-db")
                .param("isbn", isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(BookResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @DisplayName("알라딘에 Isbn 중복 체크 테스트")
    @Test
    void checkIsbnExistInAladinTest() throws Exception {

        String isbn = "9791156754039";
        mockMvc.perform(get("/api/admin/products/books/check-exist-aladin")
                .param("isbn", isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(BookResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @DisplayName("알라딘API를 이용해 도서 부가정보 검색이 잘 되는지 테스트")
    @Test
    void bookSearchInAladinTest() throws Exception {
        String isbn = "9791156754039";
        Item item = new Item();
        given(aladinApiService.getBookDetails(isbn)).willReturn(item);

        mockMvc.perform(get("/api/admin/products/books/check-exist-aladin")
                .param("isbn", isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(BookResultMessageEnum.GET_SUCCESS.getMessage())));
    }

}