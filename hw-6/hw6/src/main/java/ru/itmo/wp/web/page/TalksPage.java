package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class TalksPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            setMessage("You should enter to use talks");
            throw new RedirectException("/index");
        }
    }

    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User sourceUser = getUser();
        String stringTargetUserId = request.getParameter("target_user_id");
        String text = request.getParameter("text");
        userService.validateSend(sourceUser, stringTargetUserId, text);

        Talk talk = new Talk();
        talk.setSourceUserId(sourceUser.getId());
        talk.setTargetUserId(Long.parseLong(stringTargetUserId));
        talk.setText(text);
        userService.send(talk);
    }

    @Override
    protected void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
        User user = getUser();
        view.put("users", userService.findAll());
        view.put("talks", userService.findTalks(user.getId()));
    }
}
