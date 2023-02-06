package shop.itbook.itbookshop.membergroup.memberdestination.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDestinationRequestDto {

    @NotNull(message = "멤버번호는 null 일 수 없습니다.")
    Long memberNo;

    @Length(max = 20, message = "수렁인 이름은 최대 20자까지 허용합니다.")
    @NotBlank(message = "수령인 이름은 null값 및 공백을 허용하지 않습니다.")
    String recipientName;

    @Pattern(regexp = "^[0-9]{11}$", message = "전화번호 형식에 맞춰 입력해주세요. 숫자만 입력할 수 있습니다.")
    @NotBlank(message = "핸드폰 번호는 null값 및 공백을 허용하지 않습니다.")
    String recipientPhoneNumber;

    @NotNull(message = "우편번호를 입력해 주세요.")
    Integer postcode;

    @Length(max = 255, message = "도로명 주소는 최대 255자까지 허용합니다.")
    @NotBlank(message = "도로명 주소는 null값 및 공백을 허용하지 않습니다.")
    String roadNameAddress;

    @Length(max = 255, message = "상세 주소는 최대 255자까지 허용합니다.")
    @NotBlank(message = "상세 주소는 null값 및 공백을 허용하지 않습니다.")
    String recipientAddressDetails;
}
