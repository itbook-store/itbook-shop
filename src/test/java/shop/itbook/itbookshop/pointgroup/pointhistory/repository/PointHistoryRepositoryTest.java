package shop.itbook.itbookshop.pointgroup.pointhistory.repository;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class PointHistoryRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    PointIncreaseDecreaseContentRepository pointIncreaseDecreaseContentRepository;

    Member member1;
    Membership membership1;
    MemberStatus normalMemberStatus1;
    PointIncreaseDecreaseContent orderIncreasePointIncreaseDecreaseContent;
    PointHistory dummyPointHistory1;
    PointHistory dummyPointHistory2;
    PointHistory dummyPointHistory3;

    @BeforeEach
    void setUp() {
        member1 = MemberDummy.getMember1();

        orderIncreasePointIncreaseDecreaseContent =
            PointIncreaseDecreaseContentDummy.getOrderIncreasePointIncreaseDecreaseContent();

        pointIncreaseDecreaseContentRepository.save(orderIncreasePointIncreaseDecreaseContent);

        membership1 = MembershipDummy.getMembership();
        membershipRepository.save(membership1);

        normalMemberStatus1 = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus1);

        member1.setMemberStatus(normalMemberStatus1);
        member1.setMembership(membership1);
        memberRepository.save(member1);

        dummyPointHistory1 = PointHistoryDummy.getPointHistory();
        dummyPointHistory1.setMember(member1);
        dummyPointHistory1.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        dummyPointHistory2 = PointHistoryDummy.getPointHistory();
        dummyPointHistory2.setMember(member1);
        dummyPointHistory2.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        dummyPointHistory3 = PointHistoryDummy.getPointHistory();
        dummyPointHistory3.setMember(member1);
        dummyPointHistory3.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);


        pointHistoryRepository.save(dummyPointHistory1);
        pointHistoryRepository.save(dummyPointHistory2);
        pointHistoryRepository.save(dummyPointHistory3);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("포인트 내역 저장이 잘 이루어진다.")
    @Test
    void save() {

        PointHistory pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setMember(member1);
        pointHistory.setPointIncreaseDecreaseContent(orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(pointHistory);

        entityManager.flush();
        entityManager.clear();

        PointHistory actualPointHistory =
            entityManager.find(PointHistory.class, pointHistory.getPointHistoryNo());

        assertThat(actualPointHistory.getPointHistoryNo())
            .isEqualTo(pointHistory.getPointHistoryNo());
        assertThat(actualPointHistory.getRemainedPoint())
            .isEqualTo(pointHistory.getRemainedPoint());
        assertThat(actualPointHistory.getHistoryCreatedAt())
            .isEqualTo(pointHistory.getHistoryCreatedAt());
    }

    @DisplayName("저장된 포인트내역을 잘 불러온다.")
    @Test
    void find() {
        PointHistory actual =
            pointHistoryRepository.findById(dummyPointHistory1.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(dummyPointHistory1.getPointHistoryNo());
        assertThat(actual.getHistoryCreatedAt())
            .isEqualTo(dummyPointHistory1.getHistoryCreatedAt());
        assertThat(actual.getIncreaseDecreasePoint())
            .isEqualTo(dummyPointHistory1.getIncreaseDecreasePoint());
    }

    @DisplayName("특정멤버의 가장 나중에 등록된 포인트내역을 내림차순으로 첫번재 것을 잘 가져온다.")
    @Test
    void findFirstByMemberOrderByPointHistoryNoDesc() {

        PointHistory actual = pointHistoryRepository.findFirstByMemberOrderByPointHistoryNoDesc(
            member1).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(dummyPointHistory3.getPointHistoryNo());
        assertThat(actual.getHistoryCreatedAt())
            .isEqualTo(dummyPointHistory3.getHistoryCreatedAt());
        assertThat(actual.getIncreaseDecreasePoint())
            .isEqualTo(dummyPointHistory3.getIncreaseDecreasePoint());
    }

    @DisplayName("포인트 내역을 PointHistoryListResponseDto 형태로 잘 받아온다.")
    @Test
    void findPointHistoryListResponseDto() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<PointHistoryListResponseDto> page =
            pointHistoryRepository.findPointHistoryListResponseDto(pageRequest, null);

        List<PointHistoryListResponseDto> content = page.getContent();
        assertThat(content).hasSize(3);
        assertThat(content.get(0).getPointHistoryNo())
            .isEqualTo(dummyPointHistory3.getPointHistoryNo());
        assertThat(content.get(1).getPointHistoryNo())
            .isEqualTo(dummyPointHistory2.getPointHistoryNo());
        assertThat(content.get(2).getPointHistoryNo())
            .isEqualTo(dummyPointHistory1.getPointHistoryNo());
    }

    @DisplayName("주문 포인트 내역을 PointHistoryListResponseDto 형태로 잘 받아온다.")
    @Test
    void findPointHistoryListResponseDto_order() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<PointHistoryListResponseDto> page =
            pointHistoryRepository.findPointHistoryListResponseDto(pageRequest,
                orderIncreasePointIncreaseDecreaseContent.getContentEnum());

        List<PointHistoryListResponseDto> content = page.getContent();
        assertThat(content).hasSize(3);
        assertThat(content.get(0).getPointHistoryNo())
            .isEqualTo(dummyPointHistory3.getPointHistoryNo());
        assertThat(content.get(1).getPointHistoryNo())
            .isEqualTo(dummyPointHistory2.getPointHistoryNo());
        assertThat(content.get(2).getPointHistoryNo())
            .isEqualTo(dummyPointHistory1.getPointHistoryNo());
    }

    @DisplayName("user1000으로 검색한 포인트 내역을 잘 가져온다.")
    @Test
    void findPointHistoryListResponseDtoThroughSearch() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<PointHistoryListResponseDto> page =
            pointHistoryRepository.findPointHistoryListResponseDtoThroughSearch(pageRequest, null,
                "user1000");

        List<PointHistoryListResponseDto> content = page.getContent();
        assertThat(content).hasSize(3);
        assertThat(content.get(0).getPointHistoryNo())
            .isEqualTo(dummyPointHistory3.getPointHistoryNo());
        assertThat(content.get(1).getPointHistoryNo())
            .isEqualTo(dummyPointHistory2.getPointHistoryNo());
        assertThat(content.get(2).getPointHistoryNo())
            .isEqualTo(dummyPointHistory1.getPointHistoryNo());
    }


    @DisplayName("특정회원에 대한 포인트내역을 잘가져온다.")
    @Test
    void findMyPointHistoryListResponseDto() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<PointHistoryListResponseDto> page =
            pointHistoryRepository.findMyPointHistoryListResponseDto(member1.getMemberNo(),
                pageRequest, null);

        List<PointHistoryListResponseDto> content = page.getContent();
        assertThat(content).hasSize(3);
        assertThat(content.get(0).getPointHistoryNo())
            .isEqualTo(dummyPointHistory3.getPointHistoryNo());
        assertThat(content.get(1).getPointHistoryNo())
            .isEqualTo(dummyPointHistory2.getPointHistoryNo());
        assertThat(content.get(2).getPointHistoryNo())
            .isEqualTo(dummyPointHistory1.getPointHistoryNo());
    }
}