package com.server.cx.dao.cx.impl;

import com.server.cx.dao.cx.custom.ContactsCustomDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.exception.SystemException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

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
                    "insert into user_catacts(name,phoneNo,user_id,self_user_id,createdOn) values(?,?,?,?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int index) throws SQLException {
                            Contacts contact = contacts.get(index);
                            ps.setString(1, contact.getName());
                            ps.setString(2, contact.getPhoneNo());
                            ps.setString(3, contact.getUserInfo().getId()) ;
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
        criteria.add(Restrictions.eq("userInfo.id", userId))
                .add(Restrictions.in("phoneNo", phoneNos)).setProjection(Projections.property("phoneNo"));
        Session session = (Session) em.getDelegate();

        List<String> mobiles = criteria.getExecutableCriteria(session).list();
        return mobiles;
    }

    @Override
    public List<Contacts> getContactsByUserIdAndSelfUserInfoNotNull(Long userId) throws SystemException {
        DetachedCriteria criteria = DetachedCriteria.forClass(Contacts.class);
        criteria.add(Restrictions.eq("userInfo.id", userId))
                .add(Restrictions.isNotNull("selfUserInfo"));
        Session session = (Session) em.getDelegate();
        List<Contacts> contacts = criteria.getExecutableCriteria(session).list();
        return contacts;
    }
}
