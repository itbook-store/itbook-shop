package shop.itbook.itbookshop.productgroup.productinquiryreply.controller.adminapi;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dummy.ProductInquiryDummy;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookshop.productgroup.productinquiry.transfer.ProductInquiryTransfer;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dummy.ProductInquiryReplyDummy;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;
import shop.itbook.itbookshop.productgroup.productinquiryreply.repository.ProductInquiryReplyRepository;
import shop.itbook.itbookshop.productgroup.productinquiryreply.service.ProductInquiryReplyService;
import shop.itbook.itbookshop.productgroup.productinquiryreply.transfer.ProductInquiryReplyTransfer;

/**
 * @author ?????????
 * @since 1.0
 */
@WebMvcTest(ProductInquiryReplyAdminController.class)
class ProductInquiryReplyAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProductInquiryReplyAdminController productInquiryReplyAdminController;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductInquiryService productInquiryService;

    @MockBean
    ProductInquiryReplyService productInquiryReplyService;

    @MockBean
    ProductInquiryRepository productInquiryRepository;

    @MockBean
    MemberService memberService;

    @MockBean
    ProductService productService;

    @MockBean
    MemberRoleService memberRoleService;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    ProductInquiryReplyRepository productInquiryReplyRepository;

    ProductInquiryReplyRequestDto productInquiryReplyRequestDto;

    ProductInquiryReplyResponseDto productInquiryReplyResponseDto;

    ProductInquiryRequestDto productInquiryRequestDto;

    ProductInquiryReply dummyProductInquiryReply;
    ProductInquiry dummyProductInquiry;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;

    @BeforeEach
    void setUp() {
        productInquiryReplyRequestDto = ProductInquiryReplyRequestDto
            .builder()
            .productInquiryNo(1L)
            .memberNo(1L)
            .productInquiryReplyTitle("??????????????????")
            .productInquiryReplyContent("??????????????????")
            .build();

        productInquiryReplyResponseDto = ProductInquiryReplyResponseDto
            .builder()
            .productInquiryReplyNo(1)
            .productInquiryNo(1L)
            .memberNo(1L)
            .name("user")
            .productInquiryReplyTitle("??????????????????")
            .productInquiryReplyContent("??????????????????")
            .build();

        productInquiryRequestDto = ProductInquiryRequestDto
            .builder()
            .memberNo(1L)
            .productNo(1L)
            .title("?????????")
            .content("????????????")
            .isPublic(true)
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

        dummyProductInquiryReply = ProductInquiryReplyDummy.getProductInquiryReply();
        dummyProductInquiryReply.setProductInquiry(dummyProductInquiry);
        dummyProductInquiryReply.setMember(dummyMember);
        productInquiryReplyRepository.save(dummyProductInquiryReply);

    }

    @Test
    void productInquiryReplyAdd() throws Exception {
        ProductInquiry productInquiry =
            ProductInquiryTransfer.dtoToEntity(productInquiryRequestDto);
        productInquiry.setMember(dummyMember);
        productInquiry.setProduct(dummyProduct);

        given(productInquiryService.findProductInquiryByProductInquiryNo(
            productInquiryReplyRequestDto.getProductInquiryNo()))
            .willReturn(productInquiry);

        ProductInquiryReply productInquiryReply =
            ProductInquiryReplyTransfer.dtoToEntity(productInquiryReplyRequestDto);
        productInquiryReply.setProductInquiry(productInquiry);
        productInquiryReply.setMember(dummyMember);

        given(productInquiryReplyRepository.save(ArgumentMatchers.any(ProductInquiryReply.class)))
            .willReturn(productInquiryReply);

        given(memberService.findMemberByMemberNo(
            productInquiryReplyResponseDto.getMemberNo())).willReturn(dummyMember);

        productInquiryReplyService.addProductInquiryReply(productInquiryReplyRequestDto);

        mvc.perform(
                post("/api/admin/product-inquiries/reply/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productInquiryReplyRequestDto))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }
}