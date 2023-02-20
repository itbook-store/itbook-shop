package shop.itbook.itbookshop.bookmark.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookshop.bookmark.service.BookmarkService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;

/**
 * @author 강명관
 * @since 1.0
 */
@WebMvcTest(controllers = BookmarkController.class)
class BookmarkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookmarkService bookmarkService;

    @Autowired
    ObjectMapper objectMapper;

    BookmarkRequestDto bookmarkRequestDto;

    @BeforeEach
    void setUp() {
        bookmarkRequestDto = new BookmarkRequestDto();
        ReflectionTestUtils.setField(bookmarkRequestDto, "memberNo", 100L);
        ReflectionTestUtils.setField(bookmarkRequestDto, "productNo", 100L);
    }

    @DisplayName("즐겨찾기 상품을 추가 성공 테스트")
    @Test
    void bookmarkAddProduct() throws Exception {
        // given
        given(bookmarkService.addProductInBookmark(any())).willReturn(Boolean.TRUE);

        // when, then
        mockMvc.perform(post("/api/bookmark")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookmarkRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isSuccessful", equalTo(true)));
    }

    @DisplayName("즐겨찾기 상품 추가_그러나 이미 등록된 상품 테스트")
    @Test
    void bookmarkAddProduct_AlreadyRegistrationProduct_thenReturnFalse() throws Exception {
        // given
        given(bookmarkService.addProductInBookmark(any())).willReturn(Boolean.FALSE);

        // when, then
        mockMvc.perform(post("/api/bookmark")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookmarkRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isSuccessful", equalTo(false)));
    }

    @DisplayName("즐겨찾기 상품 삭제 테스트")
    @Test
    void bookmarkDeleteProduct() throws Exception {
        // given, when, then
        mockMvc.perform(delete("/api/bookmark")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookmarkRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    }

    @DisplayName("즐겨찾기 해당 회원에 대한 모든 상품 삭제 테스트")
    @Test
    void bookmarkDeleteAllProduct() throws Exception {
        // given
        Long memberNo = 100L;

        // when, then
        mockMvc.perform(delete("/api/bookmark/{memberNo}", memberNo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @DisplayName("즐겨찾기에 등록된 모든 상품의 상세 정보를 반환 테스트")
    @Test
    void bookmarkProductList() throws Exception {

        // given
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();

        BookmarkResponseDto bookmark1 =
            new BookmarkResponseDto(LocalDateTime.now(), product1);
        BookmarkResponseDto bookmark2 =
            new BookmarkResponseDto(LocalDateTime.now(), product2);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page page = new PageImpl(List.of(bookmark1, bookmark2), pageRequest, 100);

        Long memberNo = 100L;

        given(bookmarkService.getAllProductInBookmark(any(), anyLong())).willReturn(page);

        // when, then
        mockMvc.perform(get("/api/bookmark/{memberNo}", memberNo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.productName",
                equalTo("객체지향의 사실과 오해")))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.simpleDescription",
                equalTo("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.detailsDescription",
                equalTo("상세 설명")))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.isSelled", equalTo(true)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.isForceSoldOut",
                    equalTo(false)))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.stock", equalTo(1)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.increasePointPercent",
                    equalTo(1.0)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.rawPrice", equalTo(12000)))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.fixedPrice",
                equalTo(20000)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.discountPercent",
                    equalTo(10.0)))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.fileThumbnailsUrl",
                equalTo("testUrl")))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.isbn", equalTo("isbn")))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.pageCount", equalTo(10)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.isEbook", equalTo(false)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.fileEbookUrl",
                    equalTo(null)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.publisherName",
                    equalTo("출판사")))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.authorName", equalTo("작가")))
            .andExpect(
                jsonPath(
                    "$.result.content[0].productDetailsResponseDto.isPointApplyingBasedSellingPrice",
                    equalTo(false)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.isPointApplying",
                    equalTo(true)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.isSubscription",
                    equalTo(false)))
            .andExpect(jsonPath("$.result.content[0].productDetailsResponseDto.selledPrice",
                equalTo(null)))
            .andExpect(
                jsonPath("$.result.content[0].productDetailsResponseDto.thumbnailsName",
                    equalTo(null)))
            .andExpect(jsonPath("$.result.content", hasSize(2)));
    }
}