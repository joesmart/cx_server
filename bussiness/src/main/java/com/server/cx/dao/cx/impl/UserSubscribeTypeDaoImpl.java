package com.server.cx.dao.cx.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.server.cx.dao.cx.custom.UserSubscribeTypeCustom;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.util.DateUtil;
import com.server.cx.util.business.SubscribeStatus;

@Component
public class UserSubscribeTypeDaoImpl extends BasicDao implements UserSubscribeTypeCustom {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public int updateValidateMonth(int month, Long id) {
        String hql = "update UserSubscribeType u set u.validateMonth = ? where id = ?";
        Query query = em.createQuery(hql);
        query.setParameter(1, month);
        query.setParameter(2, id);
        int rows = query.executeUpdate();
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<UserSubscribeType> findUserSubscribeTypes(UserInfo userInfo, String type) {
        String hql = "select u from UserSubscribeType u inner join u.subscribeType item where cast_type = ? and u.userInfo = ? and u.subscribeStatus = ?";
        Query query = em.createQuery(hql);
        query.setParameter(1, type);
        query.setParameter(2, userInfo);
        query.setParameter(3, SubscribeStatus.SUBSCRIBED);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public UserSubscribeType findMonthValidateAndNotSubscribedType(UserInfo userInfo, String queryCondition) {
        String hql = "select u from UserSubscribeType u inner join u.subscribeType item where cast_type = ? and u.validateMonth = ? and u.subscribeStatus != ? and u.userInfo = ?";
        Query query = em.createQuery(hql);
        query.setParameter(1, queryCondition);
        query.setParameter(2, DateUtil.getCurrentMonth());
        query.setParameter(3, SubscribeStatus.SUBSCRIBED);
        query.setParameter(4, userInfo);
        List<UserSubscribeType> userSubscribeTypes = query.getResultList();
        return (userSubscribeTypes == null || userSubscribeTypes.isEmpty()) ? null : userSubscribeTypes.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserSubscribeType> findAllSubscribeTypes() {
        String hql = "select u from UserSubscribeType u inner join u.subscribeType item where u.subscribeStatus = ? order by u.createdOn asc";
        Query query = em.createQuery(hql);
        query.setParameter(1, SubscribeStatus.SUBSCRIBED);
        return query.getResultList();
    }

}
