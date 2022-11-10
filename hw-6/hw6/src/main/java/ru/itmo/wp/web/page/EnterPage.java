package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class EnterPage extends Page{
    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("login_or_email");
        String password = request.getParameter("password");

        User user = userService.validateEnter(loginOrEmail, password);
        userService.addEvent(user.getId(), Event.EventType.ENTER);
        setUser(user);
        setMessage("Hello, " + user.getLogin());
        throw new RedirectException("/index");
    }
}
