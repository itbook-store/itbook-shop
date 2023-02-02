package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface PointIncreaseDecreaseContentRepository
    extends JpaRepository<PointIncreaseDecreaseContent, Long> {

}
