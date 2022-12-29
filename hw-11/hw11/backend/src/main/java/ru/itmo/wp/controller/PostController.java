package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1/posts")
public class PostController {

    private final JwtService jwtService;
    private final PostService postService;
    private final UserService userService;

    public PostController(JwtService jwtService, PostService postService, UserService userService) {
        this.jwtService = jwtService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("writePost")
    public void writePostPost(@RequestParam String jwt, @Valid @RequestBody Post post,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = jwtService.find(jwt);
        if (user == null) {
            bindingResult.addError(new ObjectError("no-user", "You should be logged"));
            throw new ValidationException(bindingResult);
        }

        userService.writePost(post, user);
    }
}
