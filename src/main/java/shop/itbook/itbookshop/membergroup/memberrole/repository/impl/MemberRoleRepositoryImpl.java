package shop.itbook.itbookshop.membergroup.memberrole.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;
import shop.itbook.itbookshop.membergroup.memberrole.entity.QMemberRole;
import shop.itbook.itbookshop.membergroup.memberrole.repository.CustomMemberRoleRepository;
import shop.itbook.itbookshop.role.entity.QRole;

/**
 * 회원 권한에 대한 QueryDsl 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class MemberRoleRepositoryImpl extends QuerydslRepositorySupport
    implements CustomMemberRoleRepository {

    public MemberRoleRepositoryImpl() {
        super(MemberRole.class);
    }

    @Override
    public List<MemberRoleResponseDto> findMemberRoleWithMemberNo(Long memberNo) {
        QMemberRole qMemberRole = QMemberRole.memberRole;
        QRole qRole = QRole.role;

        return from(qMemberRole)
            .leftJoin(qMemberRole.role, qRole)
            .select(
                Projections.constructor(
                    MemberRoleResponseDto.class,
                    qRole.roleType.stringValue()
                )
            )
            .where(qMemberRole.pk.memberNo.eq(memberNo))
            .fetch();
    }
}

