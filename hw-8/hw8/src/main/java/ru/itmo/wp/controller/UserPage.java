package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.dto.UserPageDTO;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model, HttpServletResponse response) {
        UserPageDTO userDTO = null;
        try {
            long numberId = Long.parseLong(id);
            User user = userService.findById(numberId);
            if (user != null) {
                userDTO = new UserPageDTO(
                        user.getId(),
                        user.getLogin(),
                        user.getCreationTime());
            }
        } catch (NumberFormatException ignored) {
        }
        if (userDTO == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        model.addAttribute("userDTO", userDTO);
        return "UserPage";
    }
}
