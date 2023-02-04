package shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
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
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

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
    public Page<PointHistoryListDto> findPointHistoryListDto(Pageable pageable,
                                                             PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        List<PointHistoryListDto> pointHistoryList =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<PointHistoryListDto> countJpql =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum);

        return PageableExecutionUtils.getPage(pointHistoryList, pageable,
            countJpql.fetch()::size);
    }

    @Override
    public Page<PointHistoryListDto> findPointHistoryListDtoThroughSearch(Pageable pageable,
                                                                          PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
                                                                          String searchWord) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        List<PointHistoryListDto> pointHistoryList =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum)
                .where(qMember.memberId.startsWith(searchWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<PointHistoryListDto> countJpql =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum)
                .where(qMember.memberId.startsWith(searchWord));

        return PageableExecutionUtils.getPage(pointHistoryList, pageable,
            countJpql.fetch()::size);
    }

    @Override
    public Page<PointHistoryListDto> findMyPointHistoryListDto(Long memberNo, Pageable pageable,
                                                               PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        List<PointHistoryListDto> myPointHistoryList =
            this.getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                    pointIncreaseDecreaseContentEnum)
                .where(qPointHistory.member.memberNo.eq(memberNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<PointHistoryListDto> countJpql =
            this.getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                    pointIncreaseDecreaseContentEnum)
                .where(qPointHistory.member.memberNo.eq(memberNo));

        return PageableExecutionUtils.getPage(myPointHistoryList, pageable,
            countJpql.fetch()::size);
    }


    private JPQLQuery<PointHistoryListDto> getPointHistoryList(QPointHistory qPointHistory,
                                                               QMember qMember,
                                                               QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent,
                                                               PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        JPQLQuery<PointHistoryListDto> jpqlQuery = from(qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .innerJoin(qPointHistory.pointIncreaseDecreaseContent, qPointIncreaseDecreaseContent)
            .orderBy(qPointHistory.pointHistoryNo.desc())
            .select(Projections.fields(PointHistoryListDto.class,
                qMember.memberNo,
                qMember.memberId,
                qMember.name.as("memberName"),
                qPointHistory.pointHistoryNo,
                qPointHistory.isDecrease,
                qPointHistory.increaseDecreasePoint,
                qPointHistory.pointIncreaseDecreaseContent.contentEnum.stringValue().as("content"),
                qPointHistory.remainedPoint,
                qPointHistory.historyCreatedAt));

        if (Objects.nonNull(pointIncreaseDecreaseContentEnum)) {
            jpqlQuery
                .where(
                    qPointIncreaseDecreaseContent.contentEnum.eq(pointIncreaseDecreaseContentEnum));
        }

        return jpqlQuery;
    }
}
