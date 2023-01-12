package shop.itbook.itbookshop.membergroup.memberstatus.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.QMemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepositoryCustom;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

/**
 * MemberStatusRepositoryCustom 인터페이스를 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Repository
public class MemberStatusRepositoryCustomImpl implements MemberStatusRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberStatusRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    QMemberStatus qMemberStatus = QMemberStatus.memberStatus;

    @Override
    public Optional<MemberStatusResponseDto> querydslFindByName(String memberStatusName) {
        return Optional.of(jpaQueryFactory.select(
            Projections.constructor(MemberStatusResponseDto.class, qMemberStatus.memberStatusEnum)
        ).from(qMemberStatus).where(qMemberStatus.memberStatusEnum.eq(
            MemberStatusEnum.valueOf(memberStatusName))).fetch().get(0));
    }

    @Override
    public List<MemberStatusResponseDto> querydslFindAll() {
        return jpaQueryFactory.select(
                Projections.constructor(MemberStatusResponseDto.class, qMemberStatus.memberStatusEnum))
            .from(qMemberStatus).fetch();
    }
}
