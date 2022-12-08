package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.UrlUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;


    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable String id, Model model, HttpServletResponse response) {
        Post post = postService.findById(UrlUtil.getLongIdOrNull(id));
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "PostPage";
        }
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @AnyRole({Role.Name.ADMIN, Role.Name.WRITER})
    @GetMapping("/post/{id}/comment")
    public String getWriteComment(@PathVariable String id) {
        return "redirect:/post/{id}";
    }

    @AnyRole({Role.Name.ADMIN, Role.Name.WRITER})
    @PostMapping("/post/{id}/comment")
    public String postWriteComment(@PathVariable String id,
                                   @Valid @ModelAttribute("comment") Comment comment,
                                   BindingResult bindingResult,
                                   Model model,
                                   HttpSession httpSession,
                                   HttpServletResponse response) {
        Post post = postService.findById(UrlUtil.getLongIdOrNull(id));
        model.addAttribute("post", post);
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "PostPage";
        }
        postService.writeComment(post, getUser(httpSession), comment);
        putMessage(httpSession, "You published new comment");
        return "redirect:/post/{id}";
    }
}
