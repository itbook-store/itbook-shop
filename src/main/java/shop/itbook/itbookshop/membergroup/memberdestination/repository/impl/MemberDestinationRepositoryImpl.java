package shop.itbook.itbookshop.membergroup.memberdestination.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.deliverygroup.deliverydestination.entity.QDeliveryDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.QMemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.CustomMemberDestinationRepository;

/**
 * CustomMemberDestinationRepository 의 구현 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public class MemberDestinationRepositoryImpl extends QuerydslRepositorySupport implements
    CustomMemberDestinationRepository {
    public MemberDestinationRepositoryImpl() {
        super(MemberDestination.class);
    }

    @Override
    public List<MemberDestinationResponseDto> findMemberDestinationResponseDtoByMemberNo(
        Long memberNo) {

        QMemberDestination qMemberDestination = QMemberDestination.memberDestination;
        QDeliveryDestination qDeliveryDestination = QDeliveryDestination.deliveryDestination;

        return from(qMemberDestination)
            .innerJoin(qDeliveryDestination)
            .on(qMemberDestination.deliveryDestination.deliveryDestinationNo.eq(
                qDeliveryDestination.deliveryDestinationNo))
            .fetchJoin()
            .where(qMemberDestination.member.memberNo.eq(memberNo))
            .select(Projections.fields(MemberDestinationResponseDto.class,
                qMemberDestination.recipientName,
                qMemberDestination.recipientPhoneNumber.as("phoneNumber"),
                qDeliveryDestination.postcode,
                qDeliveryDestination.address,
                qMemberDestination.recipientAddressDetails.as("detailAddress")))
            .fetch();
    }
}
