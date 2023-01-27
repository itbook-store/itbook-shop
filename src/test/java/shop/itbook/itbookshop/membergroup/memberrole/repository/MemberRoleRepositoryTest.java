package shop.itbook.itbookshop.membergroup.memberrole.repository;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRoleRepositoryTest {

    @Autowired
    MemberRoleRepository memberRoleRepository;

    @Test
    @DisplayName("회원번호를 통해 회원 권한 갖고오기")
    void findMemberRoleWithMemberNo() {
        List<MemberRoleResponseDto> memberRoleWithMemberNo =
            memberRoleRepository.findMemberRoleWithMemberNo(932L);
        memberRoleWithMemberNo.stream()
            .forEach(
                memberRoleResponseDto -> System.out.println(memberRoleResponseDto));

    }
}
