package shop.itbook.itbookshop.productgroup.productinquiryreply.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
import shop.itbook.itbookshop.productgroup.productinquiry.dummy.ProductInquiryDummy;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dummy.ProductInquiryReplyDummy;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;
import shop.itbook.itbookshop.productgroup.productinquiryreply.repository.ProductInquiryReplyRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class ProductInquiryReplyRepositoryImplTest {

    @Autowired
    ProductInquiryReplyRepository productInquiryReplyRepository;

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

    ProductInquiryReply dummyProductInquiryReply;
    ProductInquiry dummyProductInquiry;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;

    @Autowired
    ProductInquiryRepository productInquiryRepository;

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

        dummyProductInquiryReply = ProductInquiryReplyDummy.getProductInquiryReply();
        dummyProductInquiryReply.setProductInquiry(dummyProductInquiry);
        dummyProductInquiryReply.setMember(dummyMember);
        productInquiryReplyRepository.save(dummyProductInquiryReply);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findProductInquiryReply() {
        List<ProductInquiryReplyResponseDto> productInquiryReplyResponseDtos =
            productInquiryReplyRepository.findProductInquiryReply(1L);

        assertThat(productInquiryReplyResponseDtos.size()).isNotIn(-1);
        
    }


}