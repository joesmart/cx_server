package com.server.cx.dao.cx;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.server.cx.entity.cx.Suggestion;

public interface SuggestionDao extends PagingAndSortingRepository<Suggestion, Long>{
    
}
