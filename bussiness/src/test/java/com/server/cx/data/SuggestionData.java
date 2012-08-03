package com.server.cx.data;

import com.server.cx.entity.cx.Suggestion;

public class SuggestionData {

    public static Suggestion buildSuggestion() {
        Suggestion suggestion = new Suggestion();
        suggestion.setContent("nice to meet you");
        return suggestion;
    }

}
