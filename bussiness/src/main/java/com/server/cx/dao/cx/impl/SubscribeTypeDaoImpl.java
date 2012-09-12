package com.server.cx.dao.cx.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;
import com.server.cx.dao.cx.custom.SubscribeTypeDaoCustom;
import com.server.cx.entity.cx.CustomSubscribeType;
import com.server.cx.entity.cx.HolidaySubscribeType;
import com.server.cx.entity.cx.StatusSubscribeType;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.exception.SystemException;

@Component
public class SubscribeTypeDaoImpl extends BasicDao implements SubscribeTypeDaoCustom {
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

    @Override
    public void batchSaveSubscribeType(final List<SubscribeType> subscribeTypes) {
        String sql = "insert into subscribe_type(name, price, cast_type, created_by, created_on, updated_by, updated_on) values(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SubscribeType subscribeType = subscribeTypes.get(i);
                ps.setString(1, subscribeType.getName());
                ps.setDouble(2, subscribeType.getPrice());
                ps.setString(3, getTypeString(subscribeType.getName()));
                ps.setString(4, "Anonymous");
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                ps.setString(6, "Anonymous");
                ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            }
            
            private String getTypeString(String name) {
                if("节日包".equals(name)) {
                    return "holiday";
                } 
                if("状态包".equals(name)) {
                    return "status";
                }
                if("自主包".equals(name)) {
                    return "custom";
                }
                throw new SystemException("类型不匹配");
            }

            @Override
            public int getBatchSize() {
                return subscribeTypes.size();
            }
        });
    }
    

}
