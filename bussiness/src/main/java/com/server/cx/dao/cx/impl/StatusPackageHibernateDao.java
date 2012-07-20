package com.server.cx.dao.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.StatusPackageDao;
import com.server.cx.entity.cx.StatusPackage;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("statusPackageDao")
@Transactional
public class StatusPackageHibernateDao extends GenericDaoHibernate<StatusPackage, Long> implements StatusPackageDao {

    @Override
    public StatusPackage getDefaultStatusPackage() throws SystemException {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusPackage.class);
        
        detachedCriteria.add(Restrictions.eq("type", 1));
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        StatusPackage statuspackage ;
        try {
            statuspackage = (StatusPackage) criteria.uniqueResult();
        } catch (HibernateException e) {
            SystemException exception = new CXServerBussinessException(e, getMessageHelp().getZhMessage(Constants.SERVER_RUNTIME_DATA_QUERY_ERROR)); 
            throw exception;
        }
        
        return statuspackage;
    }

}
