package shop.itbook.itbookshop.paymentgroup.easypay.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.paymentgroup.easypay.transfer.EasypayTransfer;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidInputException;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EasypayServiceImpl implements EasypayService {
    private final EasypayRepository easypayRepository;

    @Override
    @Transactional
    public Easypay addEasyPay(PaymentResponseDto.PaymentDataResponseDto response) {
        Easypay easyPay = EasypayTransfer.dtoToEntity(response);
        Easypay saveEasyPay;
        try {
            saveEasyPay = easypayRepository.save(easyPay);
        } catch (
            DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }
        return saveEasyPay;
    }
}
