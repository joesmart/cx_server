package com.server.cx.dao.cx.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import com.server.cx.dao.cx.custom.ContactsCustomDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;

@Repository("contactsDao")
public class ContactsDaoImpl extends BasicDao implements ContactsCustomDao {

    @Override
    public void batchInsertContacts(final List<Contacts> contacts) throws SystemException {
        if (contacts == null || (contacts != null && contacts.size() == 0)) {
            return;
        }

        final int size = contacts.size();
        try {
            jdbcTemplate.batchUpdate(
                "insert into user_catacts(name,phone_no,user_id,self_user_id,created_on) values(?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int index) throws SQLException {
                        Contacts contact = contacts.get(index);
                        ps.setString(1, contact.getName());
                        ps.setString(2, contact.getPhoneNo());
                        ps.setString(3, contact.getUserInfo().getId());
                        if (contact.getSelfUserInfo() != null) {
                            ps.setString(4, contact.getSelfUserInfo().getId());
                        } else {
                            ps.setNull(4, Types.INTEGER);
                        }
                        ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    }

                    @Override
                    public int getBatchSize() {
                        return size;
                    }
                });
        } catch (DataAccessException e) {
            throw new SystemException(e);
        }

    }

    @Override
    public List<Contacts> getContactsByUserId(String userId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Contacts.class);
        criteria.add(Restrictions.eq("userInfo.id", userId));
        Session session = (Session) em.getDelegate();
        List<Contacts> contacts = criteria.getExecutableCriteria(session).list();
        return contacts;
    }

    public List<String> retrieveExistsMobiles(String userId, List<String> phoneNos) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Contacts.class);
        criteria.add(Restrictions.eq("userInfo.id", userId)).add(Restrictions.in("phoneNo", phoneNos))
            .setProjection(Projections.property("phoneNo"));
        Session session = (Session) em.getDelegate();

        List<String> mobiles = criteria.getExecutableCriteria(session).list();
        return mobiles;
    }

    @Override
    public List<Contacts> getContactsByUserIdAndSelfUserInfoNotNull(String userId) throws SystemException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Contacts.class);
        criteria.add(Restrictions.eq("userInfo.id", userId)).add(Restrictions.isNotNull("selfUserInfo"));
        Session session = (Session) em.getDelegate();
        List<Contacts> contacts = criteria.getExecutableCriteria(session).list();
        return contacts;
    }

    @Override
    public void updateContactsSelfUserInfo(UserInfo userinfo) throws SystemException {
        String hql = "update Contacts c set c.self_user_id = ? where c.phoneNo = ?";
        Query query = em.createQuery(hql);
        query.setParameter(1, userinfo.getId());
        query.setParameter(2, userinfo.getPhoneNo());
        query.executeUpdate();
    }

    @Override
    public void batchUpdateContactsPhoneNo(final List<Contacts> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            return;
        }
        final int size = contacts.size();
        try {
            jdbcTemplate.batchUpdate("update user_catacts set self_user_id = ? ,created_on = ? where id = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int index) throws SQLException {
                        Contacts contact = contacts.get(index);
                        ps.setString(1, contact.getSelfUserInfo().getId());
                        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        ps.setLong(3, contact.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return size;
                    }
                });
        } catch (DataAccessException e) {
            throw new SystemException(e);
        }

    }
}
