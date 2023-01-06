package shop.itbook.itbookshop.chattingroom.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.member.entity.Member;

/**
 * 채팅방 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatting_room")
public class ChattingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_room_no")
    private Long chattingRoomNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_member_no", nullable = false)
    private Member adminMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "chatting_room_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime chattingRoomCreatedAt;

    /**
     * 채팅방 엔티티에 대한 생성자 입니다.
     *
     * @param adminMember the admin member
     * @param member      the member
     * @author 강명관
     */
    public ChattingRoom(Member adminMember, Member member) {
        this.adminMember = adminMember;
        this.member = member;
    }
}
