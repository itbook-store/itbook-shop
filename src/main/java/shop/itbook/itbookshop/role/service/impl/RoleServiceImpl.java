package shop.itbook.itbookshop.role.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.exception.RoleNotFoundException;
import shop.itbook.itbookshop.role.repository.RoleRepository;
import shop.itbook.itbookshop.role.service.RoleService;
import shop.itbook.itbookshop.role.transfer.RoleTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRole(String roleName) {
        return RoleTransfer.dtoToEntity(
            roleRepository.findByRoleName(roleName).orElseThrow(RoleNotFoundException::new));
    }
}
