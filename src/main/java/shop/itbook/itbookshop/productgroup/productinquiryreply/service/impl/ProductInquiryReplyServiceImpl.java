package shop.itbook.itbookshop.productgroup.productinquiryreply.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidInputException;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;
import shop.itbook.itbookshop.productgroup.productinquiryreply.repository.ProductInquiryReplyRepository;
import shop.itbook.itbookshop.productgroup.productinquiryreply.service.ProductInquiryReplyService;
import shop.itbook.itbookshop.productgroup.productinquiryreply.transfer.ProductInquiryReplyTransfer;

/**
 * 상품 문의 답글 구현 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductInquiryReplyServiceImpl implements ProductInquiryReplyService {

    private final ProductInquiryService productInquiryService;

    private final MemberService memberService;
    private final ProductInquiryReplyRepository productInquiryReplyRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer addProductInquiryReply(ProductInquiryReplyRequestDto requestDto) {
        ProductInquiryReply productInquiryReply =
            ProductInquiryReplyTransfer.dtoToEntity(requestDto);

        ProductInquiry productInquiry = productInquiryService.findProductInquiryByProductInquiryNo(
            requestDto.getProductInquiryNo());

        Member member = memberService.findMemberByMemberNo(requestDto.getMemberNo());

        productInquiryReply.setProductInquiry(productInquiry);
        productInquiryReply.setMember(member);

        Integer productInquiryReplyNo;

        try {
            productInquiryReplyNo =
                productInquiryReplyRepository.save(productInquiryReply).getProductInquiryReplyNo();

            productInquiry.setIsReplied(true);

        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

        return productInquiryReplyNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductInquiryReplyResponseDto> findProductInquiryReply(Long productInquiryNo) {

        return productInquiryReplyRepository.findProductInquiryReply(productInquiryNo);
    }
}
