package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.exception.PointContentNotFoundException;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service.PointIncreaseDecreaseContentService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PointIncreaseDecreaseContentServiceImpl
    implements PointIncreaseDecreaseContentService {

    private final PointIncreaseDecreaseContentRepository pointIncreaseDecreaseContentRepository;

    @Override
    public PointIncreaseDecreaseContent findPointIncreaseDecreaseContentThroughContentEnum(
        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        return pointIncreaseDecreaseContentRepository.findPointIncreaseDecreaseContentByContentEnum(
            pointIncreaseDecreaseContentEnum).orElseThrow(PointContentNotFoundException::new);
    }
}
