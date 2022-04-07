package application.controller;

import application.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import application.service.UserService;
import application.model.User;

import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("users", userService.allUsers());
        return "adminPage";
    }

    @GetMapping("/edit/{id}")
    public String showAndEdit(ModelMap model, @PathVariable long id) {
        model.addAttribute("user", userService.getById(id));
        return "editPage";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(required = false, name = "ADMIN") String ADMIN,
                           @RequestParam(required = false, name = "USER") String USER) {
        user.setRoles(roleSet(ADMIN, USER));
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String addPage(ModelMap model) {
        model.addAttribute("user", new User());
        return "addPage";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("User") User user,
                          @RequestParam(required = false, name = "ADMIN") String ADMIN,
                          @RequestParam(required = false, name = "USER") String USER) {
        user.setRoles(roleSet(ADMIN, USER));
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.remove(userService.getById(id));
        return "redirect:/admin";
    }

    private static Collection<Role> roleSet(String ADMIN, String USER) {
        Collection<Role> roles = new HashSet<>();
        if (ADMIN != null) {
            roles.add(new Role(1L, "ADMIN"));
        }
        if (USER != null) {
            roles.add(new Role(2L, "USER"));
        }
        if (ADMIN == null && USER == null) {
            roles.add(new Role(2L, "USER"));
        }
        return roles;
    }
}
