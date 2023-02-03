package shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.QPointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom.CustomPointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.QPointIncreaseDecreaseContent;

/**
 * @author 최겸준
 * @since 1.0
 */
public class PointHistoryRepositoryImpl extends QuerydslRepositorySupport
    implements CustomPointHistoryRepository {

    public PointHistoryRepositoryImpl() {
        super(PointHistory.class);
    }

    @Override
    public Page<PointHistoryListDto> findPointHistoryListDto(Pageable pageable) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;


        JPQLQuery<PointHistoryListDto> jpqlQuery =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent);

        List<PointHistoryListDto> pointHistoryList = jpqlQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(pointHistoryList, pageable,
            () -> from(qPointHistory).fetchCount());
    }

    @Override
    public Page<PointHistoryListDto> findMyPointHistoryListDto(Long memberNo, Pageable pageable) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        JPQLQuery<PointHistoryListDto> jpqlQuery =
            this.getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent)
                .where(qPointHistory.member.memberNo.eq(memberNo));

        List<PointHistoryListDto> myPointHistoryList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(myPointHistoryList, pageable,
            () -> from(qPointHistory)
                .where(qPointHistory.member.memberNo.eq(memberNo)).fetchCount());
    }


    private JPQLQuery<PointHistoryListDto> getPointHistoryList(QPointHistory qPointHistory,
                                                               QMember qMember,
                                                               QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent) {
        return from(qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .innerJoin(qPointHistory.pointIncreaseDecreaseContent, qPointIncreaseDecreaseContent)
            .select(Projections.fields(PointHistoryListDto.class,
                qMember.memberNo,
                qMember.memberId,
                qMember.name.as("memberName"),
                qPointHistory.pointHistoryNo,
                qPointIncreaseDecreaseContent.isDecrease,
                qPointHistory.increaseDecreasePoint,
                qPointHistory.pointIncreaseDecreaseContent.contentEnum.stringValue().as("content"),
                qPointHistory.remainedPoint,
                qPointHistory.historyCreatedAt));
    }
}
