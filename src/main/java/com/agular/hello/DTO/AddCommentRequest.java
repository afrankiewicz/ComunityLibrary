package com.agular.hello.DTO;

import javax.validation.constraints.NotBlank;

public class AddCommentRequest {
    @NotBlank(message = "Comment must be provided")
    private String text;

    public AddCommentRequest() {
    }
    public AddCommentRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
