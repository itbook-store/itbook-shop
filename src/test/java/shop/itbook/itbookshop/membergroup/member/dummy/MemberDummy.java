package shop.itbook.itbookshop.membergroup.member.dummy;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * MemberDummy 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberDummy {

    public static Member getMember1() {

        return Member.builder()
            .memberId("user1000")
            .nickname("감자")
            .name("신짱구")
            .isMan(true)
            .birth(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
            .password("1234")
            .phoneNumber("010-9999-9999")
            .email("user1000@test.com")
            .memberCreatedAt(LocalDateTime.now())
            .isSocial(false)
            .isWriter(true)
            .build();
    }

    public static Member getMember2() {
        return Member.builder()
            .memberId("user2")
            .nickname("유저2")
            .name("김철수")
            .isMan(true)
            .birth(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
            .password("2345")
            .phoneNumber("010-1000-0000")
            .email("user2@test.com")
            .memberCreatedAt(LocalDateTime.now())
            .isSocial(false)
            .isWriter(false)
            .build();
    }
}
