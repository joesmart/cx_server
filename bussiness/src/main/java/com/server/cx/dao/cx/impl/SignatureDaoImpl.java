package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.custom.SignatureCustomDao;
import com.server.cx.entity.cx.Signature;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("signatureDao")
@Transactional
public class SignatureDaoImpl extends BasicDao implements SignatureCustomDao {

    public SignatureDaoImpl() {
    }

    @SuppressWarnings("unchecked")
    public Signature findSignatureByContent(String v) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Signature.class);
        criteria = criteria.add(Restrictions.eq("content", v));
        List<Signature> list = criteria.getExecutableCriteria(getSession()).list();
        if (list != null && list.size() > 0) {
            Signature signature = list.get(0);
            return signature;
        }
        return null;
    }

}
