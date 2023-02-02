package shop.itbook.itbookshop.coupongroup.usagestatus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.exception.UsageStatusNotFoundException;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsageStatusServiceImpl implements UsageStatusService {

    private final UsageStatusRepository usageStatusRepository;

    @Override
    public UsageStatus findUsageStatus(String usageStatus) {
        return usageStatusRepository.findByUsageStatusName(usageStatus).orElseThrow(
            UsageStatusNotFoundException::new);
    }
}
