package shop.itbook.itbookshop.productgroup.productinquiry.controller.serviceapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dummy.ProductInquiryDummy;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookshop.productgroup.productinquiry.transfer.ProductInquiryTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@WebMvcTest(ProductInquiryController.class)
class ProductInquiryControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProductInquiryController productInquiryController;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductInquiryService productInquiryService;

    @MockBean
    ProductInquiryRepository productInquiryRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    MemberRepository memberRepository;


    ProductInquiry dummyProductInquiry;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;

    ProductInquiryRequestDto productInquiryRequestDto;

    ProductInquiryResponseDto productInquiryResponseDto;

    ProductInquiryOrderProductResponseDto productInquiryOrderProductResponseDto;

    @BeforeEach
    void setUp() {
        productInquiryRequestDto = ProductInquiryRequestDto
            .builder()
            .memberNo(1L)
            .productNo(1L)
            .title("문의합")
            .content("문의내용")
            .isPublic(true)
            .build();

        productInquiryResponseDto = ProductInquiryResponseDto
            .builder()
            .productInquiryNo(1L)
            .memberNo(1L)
            .memberId("user")
            .productNo(1L)
            .name("상품이름")
            .thumbnailUrl("testUrl")
            .authorName("테스트이름")
            .title("문의합")
            .content("문의내용")
            .isPublic(true)
            .isReplied(false)
            .build();


        dummyMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(dummyMemberStatus);

        dummyMembership = MembershipDummy.getMembership();
        membershipRepository.save(dummyMembership);

        dummyMember = MemberDummy.getMember1();
        dummyMember.setMemberStatus(dummyMemberStatus);
        dummyMember.setMembership(dummyMembership);
        memberRepository.save(dummyMember);

        dummyProduct = ProductDummy.getProductSuccess();
        productRepository.save(dummyProduct);

        dummyProductInquiry = ProductInquiryDummy.getProductInquiry();
        dummyProductInquiry.setProduct(dummyProduct);
        dummyProductInquiry.setMember(dummyMember);
        productInquiryRepository.save(dummyProductInquiry);

        productInquiryOrderProductResponseDto = ProductInquiryOrderProductResponseDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .name("테스트상품")
            .thumbnailUrl("testUrl")
            .orderCreatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    void productInquiryAdd() throws Exception {
        ProductInquiry productInquiry =
            ProductInquiryTransfer.dtoToEntity(productInquiryRequestDto);
        productInquiry.setMember(dummyMember);
        productInquiry.setProduct(dummyProduct);

        given(productInquiryRepository.save(ArgumentMatchers.any(ProductInquiry.class)))
            .willReturn(productInquiry);

        productInquiryService.addProductInquiry(productInquiryRequestDto);

        mvc.perform(post("/api/product-inquiries/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productInquiryRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void productInquiryDelete() throws Exception {
        productInquiryService.deleteProductInquiry(1L);

        mvc.perform(put("/api/product-inquiries/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void productInquiryModify() throws Exception {

        productInquiryService.modifyProductInquiry(1L, productInquiryRequestDto);

        mvc.perform(put("/api/product-inquiries/1/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productInquiryRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void productInquiryOrderProductList() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(
            List.of(productInquiryOrderProductResponseDto, pageRequest, 10));

        given(
            productInquiryService.findProductInquiryOrderProductList(any(), anyLong())).willReturn(
            page);

        mvc.perform(get("/api/product-inquiries/writable/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void productInquiryDetails() throws Exception {
        ProductInquiry productInquiry =
            ProductInquiryTransfer.dtoToEntity(productInquiryRequestDto);
        productInquiry.setMember(dummyMember);
        productInquiry.setProduct(dummyProduct);

        given(productInquiryService.findProductInquiry(any())).willReturn(
            productInquiryResponseDto);

        mvc.perform(get("/api/product-inquiries/view/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void productInquiryDetailsInProductDetails() throws Exception {
        given(productInquiryService.findProductInquiryByMemberNo(anyLong(), any())).willReturn(
            productInquiryResponseDto);

        mvc.perform(get("/api/product-inquiries/view/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void productInquiryListByMemberNo() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(
            List.of(productInquiryResponseDto, pageRequest, 10));

        given(
            productInquiryService.findProductInquiryListByMemberNo(any(), anyLong())).willReturn(
            page);

        mvc.perform(get("/api/product-inquiries/list/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void productInquiryListByProductNo() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(
            List.of(productInquiryResponseDto, pageRequest, 10));

        given(
            productInquiryService.findProductInquiryListByProductNo(any(), anyLong())).willReturn(
            page);

        mvc.perform(
                get("/api/product-inquiries/product/list/1").contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}