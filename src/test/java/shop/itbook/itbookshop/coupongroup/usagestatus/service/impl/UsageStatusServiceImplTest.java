package shop.itbook.itbookshop.coupongroup.usagestatus.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.usagestatus.dummy.UsageStatusDummy;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
import shop.itbook.itbookshop.coupongroup.usagestatus.service.UsageStatusService;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(UsageStatusServiceImpl.class)
class UsageStatusServiceImplTest {

    @Autowired
    UsageStatusService usageStatusService;

    @MockBean
    UsageStatusRepository usageStatusRepository;

    @Test
    void findUsageStatus() {

        UsageStatus usageStatus = UsageStatusDummy.getAvailableUsageStatus();

        given(usageStatusRepository.findByUsageStatusName(anyString())).willReturn(Optional.of(usageStatus));

        assertThat(usageStatusService.findUsageStatus("test").getUsageStatusName()).isEqualTo(usageStatus.getUsageStatusName());
    }
}