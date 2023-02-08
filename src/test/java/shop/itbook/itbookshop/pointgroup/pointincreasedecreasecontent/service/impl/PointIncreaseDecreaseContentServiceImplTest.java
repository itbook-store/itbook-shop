package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.exception.PointContentNotFoundException;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service.PointIncreaseDecreaseContentService;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(PointIncreaseDecreaseContentServiceImpl.class)
class PointIncreaseDecreaseContentServiceImplTest {

    @Autowired
    PointIncreaseDecreaseContentService pointIncreaseDecreaseContentService;

    @MockBean
    PointIncreaseDecreaseContentRepository pointIncreaseDecreaseContentRepository;

    PointIncreaseDecreaseContentEnum orderEnum;

    PointIncreaseDecreaseContent pointIncreaseDecreaseContent;

    @BeforeEach
    void setUp() {
        orderEnum = PointIncreaseDecreaseContentEnum.ORDER;
        pointIncreaseDecreaseContent = new PointIncreaseDecreaseContent(null, orderEnum);
        pointIncreaseDecreaseContent.setPointIncreaseDecreaseContentNo(1);
    }

    @DisplayName("포인트적립 내용 주문이 잘 들어간다.")
    @Test
    void findPointIncreaseDecreaseContentThroughContentEnum() {
        // given
        given(pointIncreaseDecreaseContentRepository.findPointIncreaseDecreaseContentByContentEnum(
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(Optional.of(pointIncreaseDecreaseContent));

        // when
        PointIncreaseDecreaseContent actual =
            pointIncreaseDecreaseContentService.findPointIncreaseDecreaseContentThroughContentEnum(
                orderEnum);

        // then
        assertThat(actual.getPointIncreaseDecreaseContentNo())
            .isEqualTo(pointIncreaseDecreaseContent.getPointIncreaseDecreaseContentNo());
        assertThat(actual.getContentEnum())
            .isEqualTo(pointIncreaseDecreaseContent.getContentEnum());
    }

    @DisplayName("포인트적립 내용이 잘못들어와서 찾지 못한경우 PointContentNotFoundException이 발생한다.")
    @Test
    void findPointIncreaseDecreaseContentThroughContentEnum_fail_PointContentNotFoundException() {
        // given
        given(pointIncreaseDecreaseContentRepository.findPointIncreaseDecreaseContentByContentEnum(
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(Optional.empty());

        // when then
        assertThatExceptionOfType(PointContentNotFoundException.class)
            .isThrownBy(
                () -> pointIncreaseDecreaseContentService.findPointIncreaseDecreaseContentThroughContentEnum(
                    null))
            .withMessageContaining(PointContentNotFoundException.MESSAGE);
    }
}