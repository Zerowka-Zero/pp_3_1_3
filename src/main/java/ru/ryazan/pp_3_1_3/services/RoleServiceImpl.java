package ru.ryazan.pp_3_1_3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryazan.pp_3_1_3.exceprion.RolenameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.models.Role;
import ru.ryazan.pp_3_1_3.repositors.RoleRepository;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role getAdmin() throws RolenameAlreadyExistsException {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RolenameAlreadyExistsException("Роль ADMIN не найдена в базе данных"));
    }

    @Override
    public Role getUser() throws RolenameAlreadyExistsException {
        return roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RolenameAlreadyExistsException("Роль USER не найдена в базе данных"));
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
