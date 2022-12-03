package ru.itmo.wp.dto;

import java.util.Date;

public class UserPageDTO {
    private long id;
    private String login;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public UserPageDTO(long id, String login, Date creationTime) {
        this.id = id;
        this.login = login;
        this.creationTime = creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
