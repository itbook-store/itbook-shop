package shop.itbook.itbookshop.productgroup.productinquiry.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dummy.ProductInquiryDummy;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class ProductInquiryRepositoryImplTest {


    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager entityManager;

    ProductInquiry dummyProductInquiry;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;
    @Autowired
    private ProductInquiryRepository productInquiryRepository;

    @BeforeEach
    void setUp() {
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

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findProductInquiryList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductInquiryResponseDto> page =
            productInquiryRepository.findProductInquiryList(pageRequest);

        List<ProductInquiryResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isEqualTo(1);

    }

    @Test
    void productInquiryCount() {
        ProductInquiryCountResponseDto productInquiryCountResponseDto =
            productInquiryRepository.productInquiryCount();

        assertThat(productInquiryCountResponseDto.getProductInquiryCount()).isEqualTo(1);
    }

    @Test
    void productInquiryListOfPossibleOrderProducts() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductInquiryOrderProductResponseDto> page =
            productInquiryRepository.ProductInquiryListOfPossibleOrderProducts(pageRequest, 1L);

        List<ProductInquiryOrderProductResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isEqualTo(0);
    }

    @Test
    void findProductInquiryListByProductNo() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductInquiryResponseDto> page =
            productInquiryRepository.findProductInquiryListByProductNo(pageRequest, 1L);

        List<ProductInquiryResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isNotIn(-1);
    }

    @Test
    void findProductInquiryListByMemberNo() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductInquiryResponseDto> page =
            productInquiryRepository.findProductInquiryListByMemberNo(pageRequest, 1L);

        List<ProductInquiryResponseDto> productInquiryList = page.getContent();

        assertThat(productInquiryList.size()).isEqualTo(0);
    }
}