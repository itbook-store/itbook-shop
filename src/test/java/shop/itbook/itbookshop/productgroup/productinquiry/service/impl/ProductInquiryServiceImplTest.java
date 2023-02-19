package shop.itbook.itbookshop.productgroup.productinquiry.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dummy.ProductInquiryDummy;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.exception.ProductInquiryComeCloseOtherMemberException;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookshop.productgroup.productinquiry.transfer.ProductInquiryTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(ProductInquiryServiceImpl.class)
class ProductInquiryServiceImplTest {

    @Autowired
    ProductInquiryService productInquiryService;

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

    ProductInquiryRequestDto productInquiryRequestDto;

    ProductInquiryResponseDto productInquiryResponseDto;

    ProductInquiryOrderProductResponseDto productInquiryOrderProductResponseDto;

    ProductInquiry dummyProductInquiry;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;

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

        productInquiryOrderProductResponseDto = ProductInquiryOrderProductResponseDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .name("테스트상품")
            .thumbnailUrl("testUrl")
            .orderCreatedAt(LocalDateTime.now())
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
    }

    @Test
    void findProductInquiryByProductInquiryNo() {
        given(productInquiryRepository.findById(any())).willReturn(
            Optional.ofNullable(dummyProductInquiry));

        ProductInquiry productInquiry =
            productInquiryService.findProductInquiryByProductInquiryNo(1L);

        assertThat(productInquiry.getProductInquiryNo()).isEqualTo(
            dummyProductInquiry.getProductInquiryNo());
    }

    @Test
    void findProductInquiryList() {

        given(productInquiryService.findProductInquiryList(any())).willReturn(
            new PageImpl<>(List.of(productInquiryResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductInquiryResponseDto> page =
            productInquiryService.findProductInquiryList(pageRequest);

        List<ProductInquiryResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isEqualTo(1);
        assertThat(productInquiryList.get(0).getMemberNo()).isEqualTo(1L);
    }

    @Test
    void addProductInquiry() {
        ProductInquiry productInquiry =
            ProductInquiryTransfer.dtoToEntity(productInquiryRequestDto);
        productInquiry.setMember(dummyMember);
        productInquiry.setProduct(dummyProduct);

        given(productInquiryRepository.save(any(ProductInquiry.class)))
            .willReturn(productInquiry);
        Long actual = productInquiryService.addProductInquiry(productInquiryRequestDto);

        assertThat(actual).isEqualTo(productInquiry.getProductInquiryNo());
    }

    @Test
    void deleteProductInquiry() {

        given(productInquiryRepository.findById(any())).willReturn(
            Optional.of(dummyProductInquiry));

        ProductInquiry productInquiry = productInquiryRepository.findById(1L).orElseThrow();

        productInquiryService.deleteProductInquiry(productInquiry.getProductInquiryNo());
    }

    @Test
    void modifyProductInquiry() {
        given(productInquiryRepository.findById(any())).willReturn(
            Optional.of(dummyProductInquiry));

        ProductInquiry productInquiry = productInquiryRepository.findById(1L).orElseThrow();

        productInquiryService.modifyProductInquiry(productInquiry.getProductInquiryNo(),
            productInquiryRequestDto);
    }

    @Test
    void countProductInquiry() {
        ProductInquiryCountResponseDto productInquiryCountResponseDto =
            new ProductInquiryCountResponseDto(1L, 0);

        given(productInquiryRepository.productInquiryCount()).willReturn(
            productInquiryCountResponseDto);

        assertThat(productInquiryService.countProductInquiry().getProductInquiryCount()).isEqualTo(
            1);
    }

    @Test
    void findProductInquiryOrderProductList() {
        given(productInquiryService.findProductInquiryOrderProductList(any(), any())).willReturn(
            new PageImpl<>(List.of(productInquiryOrderProductResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductInquiryOrderProductResponseDto> page =
            productInquiryService.findProductInquiryOrderProductList(pageRequest, 1L);

        List<ProductInquiryOrderProductResponseDto> productInquiryOrderProductResponseDtos =
            page.getContent();

        assertThat(productInquiryOrderProductResponseDtos.size()).isEqualTo(1);
    }

    @Test
    void findProductInquiry() {
        given(productInquiryRepository.findProductInquiry(1L)).willReturn(
            productInquiryResponseDto);

        assertThat(productInquiryService.findProductInquiry(1L).getTitle()).isEqualTo(
            productInquiryResponseDto.getTitle());
    }

    @Test
    void findProductInquiryByMemberNoThrowException() {

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
            .isPublic(false)
            .isReplied(false)
            .build();

        given(productInquiryRepository.findProductInquiry(1L)).willReturn(
            productInquiryResponseDto);

        assertThatThrownBy(() -> {
            productInquiryService.findProductInquiryByMemberNo(-1L, 1L);
        })
            .isInstanceOf(ProductInquiryComeCloseOtherMemberException.class)
            .hasMessage(ProductInquiryComeCloseOtherMemberException.MESSAGE);
    }

    @Test
    void findProductInquiryByMemberNo() {

        given(productInquiryRepository.findProductInquiry(1L)).willReturn(
            productInquiryResponseDto);

        assertThat(
            productInquiryService.findProductInquiryByMemberNo(-1L, 1L).getProductInquiryNo())
            .isEqualTo(productInquiryResponseDto.getProductInquiryNo());
    }

    @Test
    void findProductInquiryListByMemberNo() {
        given(productInquiryService.findProductInquiryListByMemberNo(any(), any())).willReturn(
            new PageImpl<>(List.of(productInquiryResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductInquiryResponseDto> page =
            productInquiryService.findProductInquiryListByMemberNo(pageRequest, 1L);

        List<ProductInquiryResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isEqualTo(1);
        assertThat(productInquiryList.get(0).getMemberNo()).isEqualTo(1L);
    }

    @Test
    void findProductInquiryListByProductNo() {
        given(productInquiryService.findProductInquiryListByProductNo(any(), any())).willReturn(
            new PageImpl<>(List.of(productInquiryResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductInquiryResponseDto> page =
            productInquiryService.findProductInquiryListByProductNo(pageRequest, 1L);

        List<ProductInquiryResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isEqualTo(1);
        assertThat(productInquiryList.get(0).getMemberNo()).isEqualTo(1L);
    }
}