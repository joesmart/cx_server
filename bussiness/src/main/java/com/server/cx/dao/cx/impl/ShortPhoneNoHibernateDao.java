package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.ShortPhoneNoDao;
import com.server.cx.exception.SystemException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("shortPhoneNoDao")
@Transactional
public class ShortPhoneNoHibernateDao implements ShortPhoneNoDao {

    public ShortPhoneNoHibernateDao() {

    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteAllOldShortPhonNos(Long userId) throws SystemException {
        String hql = "delete from ShortPhoneNo d where d.user.id=? ";
        int updatedRecord = em.createQuery(hql).setParameter(0, userId).executeUpdate();
    }

}
