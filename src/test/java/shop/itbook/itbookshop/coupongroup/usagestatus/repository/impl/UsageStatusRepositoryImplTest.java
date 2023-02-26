package shop.itbook.itbookshop.coupongroup.usagestatus.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.usagestatus.dummy.UsageStatusDummy;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class UsageStatusRepositoryImplTest {

    @Autowired
    UsageStatusRepository usageStatusRepository;
    @Autowired
    TestEntityManager testEntityManager;

    UsageStatus availableUsageStatus;

    @BeforeEach
    void setUp() {
        availableUsageStatus = UsageStatusDummy.getAvailableUsageStatus();
        availableUsageStatus = usageStatusRepository.save(availableUsageStatus);
        testEntityManager.flush();
        testEntityManager.clear();
    }
    @Test
    void findByUsageStatusName() {

        UsageStatus usageStatus =
            usageStatusRepository.findByUsageStatusName(UsageStatusEnum.AVAILABLE.getUsageStatus()).orElseThrow();

        assertThat(usageStatus.getUsageStatusName()).isEqualTo(UsageStatusEnum.AVAILABLE);

    }
}