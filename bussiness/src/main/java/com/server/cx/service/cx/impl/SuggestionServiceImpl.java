package com.server.cx.service.cx.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.SuggestionDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.Suggestion;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.SuggestionService;
import com.server.cx.util.business.ValidationUtil;

@Component
@Transactional(readOnly=true)
public class SuggestionServiceImpl implements SuggestionService {
    private final static Logger LOGGER = LoggerFactory.getLogger(SuggestionServiceImpl.class);
    
    @Autowired
    private SuggestionDao suggestionDao;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Override
    @Transactional(readOnly=false)
    public Suggestion addSuggestion(String imsi, String content) throws SystemException {
        LOGGER.info("Into addSuggestion imsi = " + imsi);
        LOGGER.info("content = " + content);
        
        ValidationUtil.checkParametersNotNull(imsi, content);
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        ValidationUtil.checkParametersNotNull(userInfo);
        Suggestion suggestion = new Suggestion();
        suggestion.setContent(content);
        suggestion.setUserInfo(userInfo);
        suggestionDao.save(suggestion);
        return suggestion;
    }

    @Override
    public List<Suggestion> getAllSuggestion() throws SystemException {
        LOGGER.info("Into getAllSuggestion");
        return (List<Suggestion>) suggestionDao.findAll(new Sort(Direction.ASC, "id"));
        
    }

}
