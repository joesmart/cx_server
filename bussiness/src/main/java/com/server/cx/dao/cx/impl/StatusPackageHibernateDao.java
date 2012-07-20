package com.server.cx.dao.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.StatusPackageDao;
import com.server.cx.entity.cx.StatusPackage;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.MessageHelp;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("statusPackageDao")
@Transactional
public class StatusPackageHibernateDao implements StatusPackageDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageHelp messageHelp;
    @Override
    public StatusPackage getDefaultStatusPackage() throws SystemException {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusPackage.class);
        
        detachedCriteria.add(Restrictions.eq("type", 1));
        Criteria criteria = detachedCriteria.getExecutableCriteria((Session) em.getDelegate());
        StatusPackage statuspackage ;
        try {
            statuspackage = (StatusPackage) criteria.uniqueResult();
        } catch (HibernateException e) {
            SystemException exception = new CXServerBussinessException(e, messageHelp.getZhMessage(Constants.SERVER_RUNTIME_DATA_QUERY_ERROR));
            throw exception;
        }
        
        return statuspackage;
    }

}
