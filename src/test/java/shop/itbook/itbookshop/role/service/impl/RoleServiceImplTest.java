package shop.itbook.itbookshop.role.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.role.dto.RoleResponseDto;
import shop.itbook.itbookshop.role.dummy.RoleDummy;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.repository.RoleRepository;
import shop.itbook.itbookshop.role.service.RoleService;
import shop.itbook.itbookshop.role.transfer.RoleTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(RoleServiceImpl.class)
class RoleServiceImplTest {

    @Autowired
    RoleService roleService;

    @MockBean
    RoleRepository roleRepository;

    Role dummyRole;

    @BeforeEach
    void setUp() {
        dummyRole = RoleDummy.getAdminRole();
        roleRepository.save(dummyRole);
    }

    @Test
    void findRole() {
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        ReflectionTestUtils.setField(roleResponseDto, "roleNo", 1);
        ReflectionTestUtils.setField(roleResponseDto, "roleName", "ADMIN");

        given(roleRepository.findByRoleName(anyString())).willReturn(Optional.of(roleResponseDto));

        assertThat(roleService.findRole("ADMIN").getRoleNo())
            .isEqualTo(RoleTransfer.dtoToEntity(roleResponseDto).getRoleNo());
    }
}