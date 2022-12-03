package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeForm;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String noticeGet(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            setMessage(httpSession, "You should enter to use notice");
            return "redirect:/";
        }
        model.addAttribute("noticeForm", new NoticeForm ());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            setMessage(httpSession, "You should enter to use notice");
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            unsetMessage(httpSession);
            return "NoticePage";
        }
        noticeService.add(noticeForm);
        setMessage(httpSession, "Notice added");

        return "redirect:/";
    }
}
