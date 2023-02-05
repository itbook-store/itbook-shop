package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointIncreaseDecreaseContentRepository
    extends JpaRepository<PointIncreaseDecreaseContent, Integer> {

    Optional<PointIncreaseDecreaseContent> findPointIncreaseDecreaseContentByContentEnum(
        PointIncreaseDecreaseContentEnum content);
}
