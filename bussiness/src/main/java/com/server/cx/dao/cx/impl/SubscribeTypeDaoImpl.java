package com.server.cx.dao.cx.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.server.cx.dao.cx.custom.SubscribeTypeDaoCustom;
import com.server.cx.entity.cx.CustomSubscribeType;
import com.server.cx.entity.cx.HolidaySubscribeType;
import com.server.cx.entity.cx.StatusSubscribeType;
import com.server.cx.entity.cx.SubscribeType;

@Component
public class SubscribeTypeDaoImpl implements SubscribeTypeDaoCustom {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public HolidaySubscribeType findHolidaySubscribeType() {
        String hql = "from HolidaySubscribeType item";
        Query query  = em.createQuery(hql);
        List<HolidaySubscribeType> list = query.getResultList();
        if(list == null || list.isEmpty()) 
            return null;
        return list.get(0);
    }

    @Override
    public StatusSubscribeType findStatusSubscribeType() {
        String hql = "from StatusSubscribeType item";
        Query query  = em.createQuery(hql);
        List<StatusSubscribeType> list = query.getResultList();
        if(list == null || list.isEmpty()) 
            return null;
        return list.get(0);
    }

    @Override
    public CustomSubscribeType findCustomSubscribeType() {
        String hql = "from CustomSubscribeType item";
        Query query  = em.createQuery(hql);
        List<CustomSubscribeType> list = query.getResultList();
        if(list == null || list.isEmpty()) 
            return null;
        return list.get(0);
    }

    @Override
    public SubscribeType findSubscribeType(String type) {
        if("holiday".equals(type)) {
            return findHolidaySubscribeType();
        }
        if("status".equals(type)) {
            return findStatusSubscribeType();
        }
        if("custom".equals(type)) {
            return findCustomSubscribeType();
        }
        return null;
    }

}
