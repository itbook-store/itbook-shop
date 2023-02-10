package shop.itbook.itbookshop.membergroup.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * rawPassword 를 담고있는 요청 객체 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EncodeRequestDto {

    private String rawPassword;

}
