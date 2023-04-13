package ru.ryazan.pp_3_1_3.controllers;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ryazan.pp_3_1_3.exceprion.RolenameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.exceprion.UsernameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.models.Role;
import ru.ryazan.pp_3_1_3.models.User;
import ru.ryazan.pp_3_1_3.services.UserService;

import java.security.Principal;

@Controller
public class ServiceController {

    private final UserService userService;

    public ServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationUserPage(@ModelAttribute("user") User user) {
        return "/registration_user";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("user") User user) throws UsernameAlreadyExistsException, RolenameAlreadyExistsException {
        userService.register(user);
        return "redirect:/login";
    }
    @GetMapping("/user")
    public String userStart(@ModelAttribute("role") Role role,
                            Principal principal, Model model) throws UsernameAlreadyExistsException {
        User user = userService.findByName(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с таким именем не найдено"));
        model.addAttribute("user", user);
        return "user_start";
    }
}
