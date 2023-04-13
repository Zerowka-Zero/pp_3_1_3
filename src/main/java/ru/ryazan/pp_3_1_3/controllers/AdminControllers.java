package ru.ryazan.pp_3_1_3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ryazan.pp_3_1_3.exceprion.RolenameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.exceprion.UsernameAlreadyExistsException;
import ru.ryazan.pp_3_1_3.models.User;
import ru.ryazan.pp_3_1_3.services.RoleService;
import ru.ryazan.pp_3_1_3.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminControllers {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminControllers(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPanel(Model model) throws InterruptedException {
        model.addAttribute("users", userService.findAll());
        return "admin/admin_panel";
    }

    @GetMapping("/{id}/edit")
    public String editUserPage(@PathVariable("id") Long id, Model model) throws UsernameAlreadyExistsException {
        User user = userService.findById(id)
                .orElseThrow(() -> new UsernameAlreadyExistsException("Пользователь с таким id для редактирования не найден"));
        user.setPassword("");
        model.addAttribute("user", user);
        return "admin/edit_page";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User userEdit) throws UsernameAlreadyExistsException {
        User user = userService.findById(userEdit.getId())
                .orElseThrow(() -> new UsernameAlreadyExistsException("Пользователь с таким id для редактирования не найден"));
        user.setName(userEdit.getName());
        user.setLastname(userEdit.getLastname());
        user.setPassword(userEdit.getPassword()
                .equals("") ? user.getPassword() : passwordEncoder.encode(userEdit.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/role/{id}")
    public String upAdminRole(@PathVariable("id") Long id) throws UsernameAlreadyExistsException, RolenameAlreadyExistsException {
        User user = userService.findById(id)
                .orElseThrow(() -> new UsernameAlreadyExistsException("Пользователь с таким id для выдачи прав администратора не найден"));
        if (!user.getRoles().contains(roleService.getAdmin())) {
            user.getRoles().add(roleService.getAdmin());
            userService.save(user);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }

    @DeleteMapping("/role/{id}")
    public String downAdminRole(@PathVariable("id") Long id) throws UsernameAlreadyExistsException, RolenameAlreadyExistsException {
        User user = userService.findById(id)
                .orElseThrow(() -> new UsernameAlreadyExistsException("Пользователь с таким id для выдачи прав администратора не найден"));
        if (user.getRoles().contains(roleService.getAdmin())) {
            user.getRoles().remove(roleService.getAdmin());
            userService.save(user);
        }
        return "redirect:/admin";
    }
}
