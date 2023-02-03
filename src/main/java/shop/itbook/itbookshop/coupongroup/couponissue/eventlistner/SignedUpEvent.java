package shop.itbook.itbookshop.coupongroup.couponissue.eventlistner;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public class SignedUpEvent extends ApplicationEvent {

    private final Member member;
    public SignedUpEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }
}
