package shop.itbook.itbookshop.chattinghistory.entity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.chattingroom.entity.ChattingRoom;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 채팅방 기록 테이블에 대한 엔티티 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatting_history")
public class ChattingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_history_no")
    private Long chattingHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_room_no", nullable = false)
    private ChattingRoom chattingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "chatting_created_at", nullable = false, columnDefinition = "default now()")
    private LocalDateTime chattingCreatedAt;

    /**
     * 채팅 기록 엔티티에 대한 생성자 입니다.
     *
     * @param chattingRoom the chatting room
     * @param member       the member
     * @param content      the content
     * @author 강명관
     */
    @Builder
    public ChattingHistory(ChattingRoom chattingRoom, Member member, String content) {
        this.chattingRoom = chattingRoom;
        this.member = member;
        this.content = content;
    }
}
