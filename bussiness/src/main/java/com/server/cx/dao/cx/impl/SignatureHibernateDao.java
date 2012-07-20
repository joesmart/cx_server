package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.SignatureDao;
import com.server.cx.entity.cx.Signature;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("signatureDao")
@Transactional
public class SignatureHibernateDao implements SignatureDao {

    public SignatureHibernateDao(){
    }

    @PersistenceContext
    private EntityManager em;
    
    @SuppressWarnings("unchecked")
    public Signature findSignatureByContent(String v) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Signature.class);
        criteria = criteria.add(Restrictions.eq("content", v));
        Session session = (Session) em.getDelegate();
        Criteria executableCriteria =  criteria.getExecutableCriteria(session);
        List<Signature> list = executableCriteria.list();
        if(list != null&& list.size()>0){
            Signature signature = list.get(0);
            return signature;
        }
        return null;
    }

    public Signature getById(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
