package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.PostFormDTO;
import ru.itmo.wp.form.validator.PostFormDTOValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final PostFormDTOValidator postFormDTOValidator;
    public WritePostPage(UserService userService, PostFormDTOValidator postFormDTOValidator) {
        this.userService = userService;
        this.postFormDTOValidator = postFormDTOValidator;
    }

    @InitBinder("postFormDTO")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postFormDTOValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("postFormDTO", new PostFormDTO());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("postFormDTO") PostFormDTO post,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");

        return "redirect:/myPosts";
    }
}
