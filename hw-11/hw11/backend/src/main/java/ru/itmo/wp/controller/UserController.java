package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.UserCredentialsRegisterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1/users")
public class UserController {
    private final JwtService jwtService;

    private final UserService userService;

    private final UserCredentialsRegisterValidator registerValidator;

    public UserController(JwtService jwtService, UserService userService, UserCredentialsRegisterValidator registerValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.registerValidator = registerValidator;
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(registerValidator);
    }


    @GetMapping("auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("register")
    public String register(@Valid @RequestBody UserCredentials userCredentials,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return jwtService.create(userService.register(userCredentials));
    }

}
