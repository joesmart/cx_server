package com.server.cx.dao.cx;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.server.cx.constants.Constants;
import com.server.cx.exception.CXServerBussinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.MessageHelp;
import com.server.cx.util.business.ValidationUtil;

public class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private Class<T> persistentClass;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private MessageHelp messageHelp;
    
    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }

    public GenericDaoHibernate(){
        
    }
    
    /**
     * Constructor that takes in a class to see which type of entity to persist
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return super.getHibernateTemplate().loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAllDistinct() {
        return Lists.newArrayList(Sets.newLinkedHashSet(getAll()));
    }

    /**
     * {@inheritDoc}
     */
    public T getById(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     * @throws com.server.cx.exception.SystemException
     */
    public T merge(T object) throws SystemException {
        T result = null;
        try {
            result = (T) super.getHibernateTemplate().merge(object);
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, messageHelp.getZhMessage( Constants.SERVER_RUNTIME_DATA_UPDATE_ERROR ) );
            throw exception;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        super.getHibernateTemplate().delete(this.getById(id));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];
        int index = 0;
        Iterator<String> i = queryParams.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next();
            params[index] = key;
            values[index++] = queryParams.get(key);
        }
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
    }

    @Override
    public boolean isExists(final Long id) {
        Integer queryResult = null; //(Integer)super.getHibernateTemplate().findByCriteria(criteria);
        queryResult = super.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session s) throws HibernateException, SQLException {
                Criteria criteria = s.createCriteria(persistentClass).add(Restrictions.eq("id", id))
                    .setProjection(Projections.projectionList().add(Projections.rowCount()));
                int o = ((Long) criteria.uniqueResult()).intValue();
                return Integer.valueOf(o);
            }
        });

        if (queryResult == null || queryResult.intValue() == 0) {
            return false;
        }
        if (queryResult.intValue() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update(T entity) throws SystemException {
        try {
            getHibernateTemplate().update(entity);
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, messageHelp.getZhMessage(Constants.SERVER_RUNTIME_DATA_UPDATE_ERROR));
            throw exception;
        }
    }

    @Override
    public void save(T entity) throws SystemException {
        try {
            getHibernateTemplate().save(entity);
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, messageHelp.getZhMessage( Constants.SERVER_RUNTIME_DATA_STORE_ERROR ) );
            throw exception;
        }
    }

    @Override
    public void delet(T entity) throws SystemException {
        try {
            getHibernateTemplate().delete(entity);
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, Constants.SERVER_RUNTIME_DATA_DELETE_ERROR);
            throw exception;
        }

    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void removeAll() {
        String hql = "delete from " + this.persistentClass.getName();
        this.getHibernateTemplate().bulkUpdate(hql);
    }

    public void removeAll(List<Long> ids) {
        String stringIds = ValidationUtil.join(ids, ",");
        String hql = "delete from " + this.persistentClass.getName() + " where id in (" + stringIds + ")";
        this.getHibernateTemplate().bulkUpdate(hql);
    }

    @Override
    public void persist(T entity) throws SystemException {
        try {
            super.getHibernateTemplate().persist(entity);
        } catch (DataAccessException e) {
            SystemException exception = new CXServerBussinessException(e, Constants.SERVER_RUNTIME_DATA_DELETE_ERROR);
            throw exception;
        }
    }

    public MessageHelp getMessageHelp() {
        return messageHelp;
    }

    public void setMessageHelp(MessageHelp messageHelp) {
        this.messageHelp = messageHelp;
    }
}
