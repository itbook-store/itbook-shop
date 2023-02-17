package shop.itbook.itbookshop.coupongroup.membershipcoupon.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomMembershipCouponRepository {
    List<MembershipCouponResponseDto> findAvailableMembershipCouponList(String  membershipGrade);
}
