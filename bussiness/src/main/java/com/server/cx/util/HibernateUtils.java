package com.server.cx.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static SessionFactory factory;
	static {
		try {
			Configuration cfg = new Configuration().configure();
			cfg.setInterceptor(new AuditInterceptor());
			factory = cfg.buildSessionFactory();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}

	public static Session getSession() {
		return factory.openSession();
	}

	public static void closeSession(Session session) {
		if (session != null) {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
}
