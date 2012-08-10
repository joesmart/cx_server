package com.server.cx.functional.resource;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.context.ContextLoaderListener;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;

public class BasicJerseyTest extends JerseyTest {
	protected WebResource resource;

	private static final AppDescriptor APP_DESCRIPTOR = new WebAppDescriptor.Builder("com.server.cx.webservice.rs.server")
			.contextPath("bussiness")
			.contextParam(
					"contextConfigLocation",
					"classpath*:/applicationContext.xml\n" + "classpath*:/applicationContext-shiro.xml\n"
							+ "classpath*:/applicationContext-rs-server.xml")
			.contextParam("spring.profiles.default", "functional").servletClass(SpringServlet.class)
			.addFilter(org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter.class, "openEntityManagerInViewFilter")
			.contextListenerClass(ContextLoaderListener.class).build();

	public BasicJerseyTest() throws TestContainerException {
		super(APP_DESCRIPTOR);
	}

	@Override
	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
		return new OnePerAppDescriptorTestContainerFactory(super.getTestContainerFactory());
	}

	@Before
	public void setUp() throws Exception {
		resource = resource();
		System.out.println("into setup");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("into tearDown");
	}

}
