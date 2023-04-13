package ru.ryazan.pp_3_1_3.services;

import ru.ryazan.pp_3_1_3.exceprion.RolenameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.models.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
    Role getAdmin() throws RolenameAlreadyExistsException;
    Role getUser() throws RolenameAlreadyExistsException;
    void save(Role role);

}
