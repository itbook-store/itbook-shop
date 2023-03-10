package shop.itbook.itbookshop.productgroup.productinquiry.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidInputException;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.exception.ProductInquiryComeCloseOtherMemberException;
import shop.itbook.itbookshop.productgroup.productinquiry.exception.ProductInquiryNotFoundException;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookshop.productgroup.productinquiry.transfer.ProductInquiryTransfer;

/**
 * 상품 문의 서비스 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductInquiryServiceImpl implements ProductInquiryService {

    private final ProductInquiryRepository productInquiryRepository;

    private final ProductService productService;

    private final MemberService memberService;

    private final MemberRoleService memberRoleService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductInquiry findProductInquiryByProductInquiryNo(Long productInquiryNo) {
        return productInquiryRepository.findById(productInquiryNo).orElseThrow(
            ProductInquiryNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductInquiryResponseDto> findProductInquiryList(Pageable pageable) {

        return productInquiryRepository.findProductInquiryList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addProductInquiry(ProductInquiryRequestDto requestDto) {
        ProductInquiry productInquiry = ProductInquiryTransfer.dtoToEntity(requestDto);

        Product product = productService.findProductEntity(requestDto.getProductNo());

        Member member = memberService.findMemberByMemberNo(requestDto.getMemberNo());

        productInquiry.setProduct(product);
        productInquiry.setMember(member);
        productInquiry.setIsReplied(false);
        productInquiry.setIsDeleted(false);

        Long productInquiryNo;

        try {
            productInquiryNo = productInquiryRepository.save(productInquiry).getProductInquiryNo();
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

        return productInquiryNo;
    }

    @Override
    @Transactional
    public void deleteProductInquiry(Long productInquiryNo) {

        ProductInquiry productInquiry = findProductInquiryByProductInquiryNo(productInquiryNo);
        productInquiry.setIsDeleted(true);


    }

    @Override
    @Transactional
    public void modifyProductInquiry(Long productInquiryNo,
                                     ProductInquiryRequestDto productInquiryRequestDto) {

        ProductInquiry productInquiry = findProductInquiryByProductInquiryNo(productInquiryNo);

        productInquiry.modifyProductInquiry(productInquiryRequestDto.getTitle(),
            productInquiryRequestDto.getContent(), productInquiryRequestDto.getIsPublic());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductInquiryCountResponseDto countProductInquiry() {

        return productInquiryRepository.productInquiryCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductInquiryOrderProductResponseDto> findProductInquiryOrderProductList(
        Pageable pageable, Long memberNo) {

        return productInquiryRepository.ProductInquiryListOfPossibleOrderProducts(pageable,
            memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductInquiryResponseDto findProductInquiry(Long productInquiryNo) {

        return productInquiryRepository.findProductInquiry(productInquiryNo);
    }

    @Override
    public ProductInquiryResponseDto findProductInquiryByMemberNo(Long memberNo,
                                                                  Long productInquiryNo) {
        ProductInquiryResponseDto productInquiryResponseDto =
            productInquiryRepository.findProductInquiry(productInquiryNo);

        if (memberNo == -1L && !productInquiryResponseDto.getIsPublic()) {
            throw new ProductInquiryComeCloseOtherMemberException();
        }

        if (memberNo == -1L && productInquiryResponseDto.getIsPublic()) {
            return productInquiryResponseDto;
        }

        List<MemberRoleResponseDto> memberRoles =
            memberRoleService.findMemberRoleWithMemberNo(memberNo);

        for (MemberRoleResponseDto memberRole : memberRoles) {
            if (memberRole.getRole().equals("ADMIN")) {

                return productInquiryResponseDto;
            }
        }

        if (memberService.findMemberByMemberNo(memberNo).getIsWriter()) {

            return productInquiryResponseDto;
        }

        if (!productInquiryResponseDto.getIsPublic() &&
            !Objects.equals(productInquiryResponseDto.getMemberNo(), memberNo)) {
            throw new ProductInquiryComeCloseOtherMemberException();
        }

        return productInquiryResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductInquiryResponseDto> findProductInquiryListByMemberNo(Pageable pageable,
                                                                            Long memberNo) {

        return productInquiryRepository.findProductInquiryListByMemberNo(pageable, memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductInquiryResponseDto> findProductInquiryListByProductNo(Pageable pageable,
                                                                             Long productNo) {

        return productInquiryRepository.findProductInquiryListByProductNo(pageable, productNo);
    }
}
