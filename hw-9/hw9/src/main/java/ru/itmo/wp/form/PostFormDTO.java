package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostFormDTO {

    @NotNull
    @Size(min = 1, max = 60)
    @NotBlank
    private String title;

    @NotNull
    @Size(min = 1, max = 65000)
    @NotBlank
    private String text;

    @NotNull
    @Size(max = 65000)
    private String tagsLine;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTagsLine() {
        return tagsLine;
    }

    public void setTagsLine(String tagsLine) {
        this.tagsLine = tagsLine;
    }
}
