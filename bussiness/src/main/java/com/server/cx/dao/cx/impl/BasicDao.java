package com.server.cx.dao.cx.impl;

import com.server.cx.util.MessageHelp;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 上午11:12
 * FileName:BasicDao
 */
public class BasicDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected MessageHelp messageHelp;

    public Session getSession() {
        return (Session) em.getDelegate();
    }

    public MessageHelp getMessageHelp() {
        return messageHelp;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
