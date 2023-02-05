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

    PointIncreaseDecreaseContent orderPointIncreaseDecreaseContent;

    @BeforeEach
    void before() {
        orderPointIncreaseDecreaseContent =
            pointIncreaseDecreaseContentRepository.save(new PointIncreaseDecreaseContent(
                PointIncreaseDecreaseContentEnum.ORDER));

        entityManager.flush();
        entityManager.clear();
    }


    @DisplayName("선물적립에 관한 포인트 내용저장을 잘 수행한다.")
    @Test
    void save() {
        PointIncreaseDecreaseContent pointIncreaseDecreaseContent =
            new PointIncreaseDecreaseContent(
                PointIncreaseDecreaseContentEnum.GIFT);

        pointIncreaseDecreaseContentRepository.save(pointIncreaseDecreaseContent);
        entityManager.flush();
        entityManager.clear();

        PointIncreaseDecreaseContent actualContent =
            entityManager.find(PointIncreaseDecreaseContent.class,
                pointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo());

        assertThat(pointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo())
            .isEqualTo(actualContent.getPointIncreaseDecreaseContentNo());

        assertThat(pointIncreaseDecreaseContent.getContentEnum().getContent())
            .isEqualTo(actualContent.getContentEnum().getContent());
    }

    @DisplayName("enum 관계없이 잘 조회하여 필드들을 받아온다.")
    @Test
    void find() {

        PointIncreaseDecreaseContent actualContent =
            pointIncreaseDecreaseContentRepository.findById(
                orderPointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo()).get();

        assertThat(orderPointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo())
            .isEqualTo(actualContent.getPointIncreaseDecreaseContentNo());

        assertThat(orderPointIncreaseDecreaseContent.getContentEnum().getContent())
            .isEqualTo(actualContent.getContentEnum().getContent());
    }

    @DisplayName("포인트 적립 차감에 대한 내용을 잘 불러온다.")
    @Test
    void findPointIncreaseDecreaseContentByContent() {

        PointIncreaseDecreaseContent giftContent =
            pointIncreaseDecreaseContentRepository.findPointIncreaseDecreaseContentByContentEnum(
                PointIncreaseDecreaseContentEnum.ORDER).get();

        assertThat(giftContent.getContentEnum())
            .isEqualTo(PointIncreaseDecreaseContentEnum.ORDER);
    }
}