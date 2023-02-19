package shop.itbook.itbookshop.membergroup.memberstatus.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class MemberStatusRepositoryImplTest {

    @Autowired
    MemberStatusRepository memberStatusRepository;

    MemberStatus normalMemberStatus;

    @BeforeEach
    void setUp() {
        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        normalMemberStatus.setMemberStatusNo(1);
        memberStatusRepository.save(normalMemberStatus);
    }

    @Test
    void querydslFindByName() {
        MemberStatusResponseDto memberStatusResponseDto =
            memberStatusRepository.findByMemberStatusName("정상회원").orElseThrow();

        assertThat(memberStatusResponseDto.getMemberStatusNo()).isEqualTo(2);
    }

    @Test
    void querydslFindAll() {
        List<MemberStatusResponseDto> memberStatusResponseDtoList =
            memberStatusRepository.findMemberStatusResponseAll();

        assertThat(memberStatusResponseDtoList.size()).isEqualTo(1);
    }
}