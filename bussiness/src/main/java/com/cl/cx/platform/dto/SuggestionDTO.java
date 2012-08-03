package com.cl.cx.platform.dto;

import lombok.ToString;

@ToString
public class SuggestionDTO {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
