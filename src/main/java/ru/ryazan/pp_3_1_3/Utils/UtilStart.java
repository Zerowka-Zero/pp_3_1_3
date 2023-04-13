package ru.ryazan.pp_3_1_3.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ryazan.pp_3_1_3.exceprion.RolenameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.exceprion.UsernameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.models.Role;
import ru.ryazan.pp_3_1_3.models.User;
import ru.ryazan.pp_3_1_3.services.RoleService;
import ru.ryazan.pp_3_1_3.services.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class UtilStart {
    private final RoleService roleService;
    private final UserService userService;
    @Autowired
    public UtilStart(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() throws UsernameAlreadyExistsException, RolenameAlreadyExistsException {
        if (roleService.findByName("ROLE_USER").isEmpty()) {
            roleService.save(new Role("ROLE_USER"));
        }
        if (roleService.findByName("ROLE_ADMIN").isEmpty()) {
            roleService.save(new Role("ROLE_ADMIN"));
        }
        if (userService.findByName("admin").isEmpty()) {
            userService.register(new User("admin", "admin", "admin", List.of(roleService.getUser(), roleService.getAdmin())));
        }
        if (userService.findByName("test").isEmpty()) {
            userService.register(new User("test", "test", "test", List.of(roleService.getUser(), roleService.getAdmin())));
        }
    }
}

