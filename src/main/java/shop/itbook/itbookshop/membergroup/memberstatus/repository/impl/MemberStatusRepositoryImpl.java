package shop.itbook.itbookshop.membergroup.memberstatus.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.QMemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.CustomMemberStatusRepository;

/**
 * MemberStatusRepositoryCustom 인터페이스를 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberStatusRepositoryImpl implements CustomMemberStatusRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberStatusRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    QMemberStatus qMemberStatus = QMemberStatus.memberStatus;

    @Override
    public Optional<MemberStatusResponseDto> findByMemberStatusName(String memberStatusName) {
        return Optional.of(jpaQueryFactory.select(
                Projections.constructor(MemberStatusResponseDto.class,
                    qMemberStatus.memberStatusNo, qMemberStatus.memberStatusEnum.stringValue())
            ).from(qMemberStatus)
            .where(qMemberStatus.memberStatusEnum.stringValue().eq(memberStatusName)).fetchOne());
    }

    @Override
    public List<MemberStatusResponseDto> findMemberStatusResponseAll() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberStatusResponseDto.class,
                    qMemberStatus.memberStatusNo, qMemberStatus.memberStatusEnum.stringValue()))
            .from(qMemberStatus).fetch();
    }
}
