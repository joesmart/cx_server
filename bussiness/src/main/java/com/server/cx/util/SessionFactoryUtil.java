package com.server.cx.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;
    public static final ThreadLocal session = new ThreadLocal();

    public static Session currentSession() {
        Session s = (Session) session.get();
        if (s == null) {
            if (sessionFactory == null) {
                sessionFactory = getSessionFactory();
            }
            s = sessionFactory.getCurrentSession();
            session.set(s);
        }
        return s;
    }

    public static SessionFactory getSessionFactory() {
//		if (sf == null) {
//			sf = new AnnotationConfiguration().configure()
//					.buildSessionFactory();
//		}
        return sessionFactory;
    }


    @Resource(name = "entityManagerFactory")
    public static void setSessionFactory(SessionFactory sessionFactory) {
        SessionFactoryUtil.sessionFactory = sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void closeSession() {
        Session s = (Session) session.get();
        try {
            if (s != null && s.isConnected()) {
                s.getTransaction().commit();
            }
        } catch (Exception e) {
            s.getTransaction().rollback();
            e.printStackTrace();
        }
    }

}
