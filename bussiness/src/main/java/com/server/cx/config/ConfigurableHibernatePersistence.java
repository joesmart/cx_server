package com.server.cx.config;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernatePersistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Map;

/**
 * User: yanjianzou
 * Date: 12-7-20
 * Time: 下午1:26
 * FileName:ConfigurableHibernatePersistence
 * support interception in JPA2
 */
public class ConfigurableHibernatePersistence extends HibernatePersistence {
    private Interceptor interceptor;

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map properties) {
        Ejb3Configuration cfg = new Ejb3Configuration();
        Ejb3Configuration configured = cfg.configure(info, properties);
        postprocessConfiguration(info, properties, configured);
        return configured != null ? configured.buildEntityManagerFactory() : null;
    }

    private void postprocessConfiguration(PersistenceUnitInfo info, Map properties, Ejb3Configuration configured) {
        if (this.interceptor != null) {
            if (configured.getInterceptor() == null || EmptyInterceptor.class.equals(configured.getInterceptor().getClass())) {
                configured.setInterceptor(this.interceptor);
            } else {
                throw new IllegalStateException("Hibernate interceptor already set in persistence.xml (" + configured.getInterceptor() + ")");
            }
        }
    }


}
