package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.data.SuggestionData;
import com.server.cx.entity.cx.Suggestion;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class SuggestionDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private SuggestionDao suggestionDao;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Test
    public void test_add_suggestion() {
        Suggestion suggestion = SuggestionData.buildSuggestion();
        suggestionDao.save(suggestion);
        entityManager.flush();
        
        Suggestion dbSuggestion = suggestionDao.findOne(suggestion.getId());
        assertThat(suggestion).isEqualTo(dbSuggestion);
    }
    
    @Test
    public void test_get_all_suggestion() {
        Suggestion suggestion = SuggestionData.buildSuggestion();
        suggestionDao.save(suggestion);
        
        suggestion = SuggestionData.buildSuggestion();
        suggestionDao.save(suggestion);
        entityManager.flush();
        
        List<Suggestion> suggestions = (List<Suggestion>) suggestionDao.findAll(new Sort(Direction.ASC, "id"));
        assertThat(suggestions.size()).isEqualTo(2);
    }
}
