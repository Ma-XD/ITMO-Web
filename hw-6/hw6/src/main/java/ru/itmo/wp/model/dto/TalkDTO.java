package ru.itmo.wp.model.dto;

import java.util.Date;

public class TalkDTO {
    private long id;
    private String fromUser;
    private String toUser;
    private String text;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public TalkDTO(long id, String fromUser, String toUser, String text, Date creationTime) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
        this.creationTime = creationTime;
    }
}
