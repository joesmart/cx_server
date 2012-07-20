package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.SignatureDao;
import com.server.cx.entity.cx.Signature;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("signatureDao")
@Transactional
public class SignatureHibernateDao extends GenericDaoHibernate<Signature,Long> implements SignatureDao {

    public SignatureHibernateDao(){
        super(Signature.class);
    }
    
    @SuppressWarnings("unchecked")
    public Signature findSignatureByContent(String v) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Signature.class);
        criteria = criteria.add(Restrictions.eq("content", v));
        List<Signature> list = getHibernateTemplate().findByCriteria(criteria);
        if(list != null&& list.size()>0){
            Signature signature = list.get(0);
            return signature;
        }
        return null;
    }
    
}
