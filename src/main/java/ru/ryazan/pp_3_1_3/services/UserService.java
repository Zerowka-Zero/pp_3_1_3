package ru.ryazan.pp_3_1_3.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ryazan.pp_3_1_3.exceprion.RolenameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.exceprion.UsernameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void register(User user) throws UsernameAlreadyExistsException, RolenameAlreadyExistsException;
    Optional<User> findByName(String name) throws UsernameAlreadyExistsException;
    void save(User user);

    List<User> findAll() throws InterruptedException;
    Optional<User> findById(Long id);
    void delete(Long id);
}
