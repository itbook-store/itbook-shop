package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class PointIncreaseDecreaseContentRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointIncreaseDecreaseContentRepository pointIncreaseDecreaseContentRepository;

    PointIncreaseDecreaseContent giftPointIncreaseDecreaseContent;

    @BeforeEach
    void before() {
        PointIncreaseDecreaseContent pointIncreaseDecreaseContent =
            new PointIncreaseDecreaseContent(
                PointIncreaseDecreaseContentEnum.ORDER_CANCEL_INCREASE, false);

        giftPointIncreaseDecreaseContent =
            pointIncreaseDecreaseContentRepository.save(pointIncreaseDecreaseContent);

        entityManager.flush();
        entityManager.clear();
    }


    @DisplayName("선물적립에 관한 포인트 내용저장을 잘 수행한다.")
    @Test
    void save() {
        PointIncreaseDecreaseContent pointIncreaseDecreaseContent =
            new PointIncreaseDecreaseContent(
                PointIncreaseDecreaseContentEnum.GIFT_INCREASE, false);

        pointIncreaseDecreaseContentRepository.save(pointIncreaseDecreaseContent);
        entityManager.flush();
        entityManager.clear();

        PointIncreaseDecreaseContent actualContent =
            entityManager.find(PointIncreaseDecreaseContent.class,
                pointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo());

        assertThat(pointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo())
            .isEqualTo(actualContent.getPointIncreaseDecreaseContentNo());

        assertThat(pointIncreaseDecreaseContent.getIsDecrease())
            .isEqualTo(actualContent.getIsDecrease());

        assertThat(pointIncreaseDecreaseContent.getContentEnum().getStatusName())
            .isEqualTo(actualContent.getContentEnum().getStatusName());
    }

    @DisplayName("enum 관계없이 잘 조회하여 필드들을 받아온다.")
    @Test
    void find() {

        PointIncreaseDecreaseContent actualContent =
            pointIncreaseDecreaseContentRepository.findById(
                giftPointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo()).get();

        assertThat(giftPointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo())
            .isEqualTo(actualContent.getPointIncreaseDecreaseContentNo());

        assertThat(giftPointIncreaseDecreaseContent.getIsDecrease())
            .isEqualTo(actualContent.getIsDecrease());

        assertThat(giftPointIncreaseDecreaseContent.getContentEnum().getStatusName())
            .isEqualTo(actualContent.getContentEnum().getStatusName());
    }

}