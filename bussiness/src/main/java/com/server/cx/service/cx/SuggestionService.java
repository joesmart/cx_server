package com.server.cx.service.cx;

import java.util.List;
import com.server.cx.entity.cx.Suggestion;
import com.server.cx.exception.SystemException;

public interface SuggestionService {
    public Suggestion addSuggestion(String imsi, String content) throws SystemException;
     
    public List<Suggestion> getAllSuggestion() throws SystemException;
}
