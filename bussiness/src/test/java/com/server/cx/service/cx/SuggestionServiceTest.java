package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.SuggestionDao;
import com.server.cx.entity.cx.Suggestion;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class SuggestionServiceTest extends SpringTransactionalTestCase {
    public static final Logger LOGGER = LoggerFactory.getLogger(ContactsServiceTest.class);
    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private SuggestionDao suggestionDao;

    @Test
    public void test_get_all_suggestions() {
        List<Suggestion> suggestions = suggestionService.getAllSuggestion();
        assertThat(suggestions.size()).isEqualTo(3);
        assertThat(suggestions.get(0).getContent()).isEqualTo("I have a dream");
    }

    @Test
    public void should_add_suggestion() {
        Suggestion suggestion = suggestionService.addSuggestion("1512581470", "哈哈，这里的酸辣粉很好吃哈");
        assertThat(suggestion.getId()).isNotEqualTo(0);
        Suggestion dbSuggestion = suggestionDao.findOne(suggestion.getId());
        assertThat(suggestion).isEqualTo(dbSuggestion);
    }
}
