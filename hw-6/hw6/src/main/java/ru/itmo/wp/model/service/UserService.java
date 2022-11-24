package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.dto.TalkDTO;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String PASSWORD_SALT = "1174f9d7bc21e00e9a5fd0a783a44c9a9f73413c";

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (!user.getEmail().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,6}")) {
            throw new ValidationException("Email format is incorrect");
        }
        if (user.getEmail().length() > 254) {
            throw new ValidationException("Login can't be longer than 254 letters");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 64) {
            throw new ValidationException("Password can't be longer than 64 characters");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords don't match");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    public void addEvent(long userId, Event.EventType type) {
        Event event = new Event();
        event.setUserId(userId);
        event.setType(type);
        eventRepository.save(event);
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User validateEnter(String loginOrEmail, String password) throws ValidationException {
        String passwordSha = getPasswordSha(password);
        User user = userRepository.findByLoginAndPasswordSha(loginOrEmail, passwordSha);
        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, passwordSha);
            if (user == null) {
                throw new ValidationException("Invalid login/email or password");
            }
        }
        return user;
    }

    public int findCount() {
        return userRepository.countAll();
    }

    public List<TalkDTO> findTalks(long id) {
        List<Talk> talks = talkRepository.findByUserId(id);
        List<TalkDTO> talkDTOS = new ArrayList<>();
        for (Talk talk : talks) {
            talkDTOS.add(new TalkDTO(
                    talk.getId(),
                    userRepository.find(talk.getSourceUserId()).getLogin(),
                    userRepository.find(talk.getTargetUserId()).getLogin(),
                    talk.getText(),
                    talk.getCreationTime()
            ));
        }
        return talkDTOS;
    }

    public void validateSend(User sourceUser, String stringTargetUserId, String text) throws ValidationException {
        if (sourceUser == null) {
            throw new RedirectException("/index");
        }
        try {
            long id = Long.parseLong(stringTargetUserId);
            if (userRepository.find(id) == null) {
                throw new ValidationException("Can't find target user");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Target user's id can be be only long type");
        }
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Text is required");
        }
        if (text.trim().isEmpty()) {
            throw new ValidationException("Text is required");
        }
        if (text.length() > 1024) {
            throw new ValidationException("Text can't be longer than 1024 characters");
        }
    }

    public void send(Talk talk) {
        talkRepository.save(talk);
    }
}
