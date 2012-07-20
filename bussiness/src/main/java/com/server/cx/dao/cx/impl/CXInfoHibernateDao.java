package com.server.cx.dao.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.CXInfoDao;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.util.MessageHelp;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("cxInfoDao")
@Transactional
public class CXInfoHibernateDao implements CXInfoDao {

    public CXInfoHibernateDao() {
    }

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageHelp messageHelp;

    @SuppressWarnings("unchecked")
    @Override
    public List<CXInfo> browserAllData(final Map<String, String> params) throws SystemException {

        //TODO 需要转换为 criteria 方式减少 repeat.
        final String type = params.get("type");
        final String category = params.get("category");

        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(CXInfo.class);

        int requestPage = 0;
        int requestPageSize = 0;

        try {
            requestPage = Integer.parseInt(params.get("begingPage"));
            requestPageSize = Integer.parseInt(params.get("pageSize"));
        } catch (NumberFormatException e) {
            requestPage = 0;
            requestPageSize = Constants.DEFAULT_SIZE;
        }

        int perPageSize = requestPageSize == 0 ? Constants.DEFAULT_SIZE : requestPageSize;
        int begineRecord = requestPage == 0 ? 0 : (requestPage - 1) * perPageSize;

        criteria.add(Restrictions.eq("isServer",1));
        if(type != null && !"".equals(type) ){
            criteria.add(Restrictions.eq("type",type));
        }
        if (category != null && !"".equals(category)) {
            criteria.add(Restrictions.eq("category",category));
        }
        criteria.addOrder(Order.desc("uploadTime"));
        criteria.setMaxResults(perPageSize);
        criteria.setFirstResult(begineRecord);
        List<CXInfo> result  = criteria.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<CXInfo> getCMCCCXInfos() {
        List<CXInfo> list = Lists.newArrayList();
        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(CXInfo.class);
        criteria.add(Restrictions.eq("isServer", 10086));
        list = criteria.list();
        return list;
    }

}