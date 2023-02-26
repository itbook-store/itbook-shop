package shop.itbook.itbookshop.role.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.itbook.itbookshop.role.dto.RoleResponseDto;
import shop.itbook.itbookshop.role.dummy.RoleDummy;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.repository.RoleRepository;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class RoleRepositoryImplTest {

    @Autowired
    RoleRepository roleRepository;

    Role dummyRole;

    @BeforeEach
    void setUp() {
        dummyRole = RoleDummy.getAdminRole();
        roleRepository.save(dummyRole);
    }

    @Test
    void findByRoleName() {
        RoleResponseDto roleResponseDto = roleRepository.findByRoleName("ADMIN").orElseThrow();

        assertThat(roleResponseDto.getRoleName()).isEqualTo(dummyRole.getRoleType().getRoleName());
    }
}