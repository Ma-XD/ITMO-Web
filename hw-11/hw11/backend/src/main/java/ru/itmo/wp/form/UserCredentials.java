package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserCredentials {
    @NotBlank
    @Size(min = 2, max = 24)
    @Pattern(regexp = "[a-zA-Z]+", message = "Expected letters A-Z or a-z")
    private String login;
    private String name;

    @NotBlank
    @Size(min = 3, max = 60)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
