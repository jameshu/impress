/**
 * project : sirius
 * user created : pippo
 * date created : 2007-7-16-下午03:06:03
 */
package com.snda.youni.taskweb.common;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Init spring WebApplicationContext and init utils class SpringLocator
 *
 * @author pippo
 */
public class SpringInitListener extends ContextLoaderListener {

	private static Logger logger = LoggerFactory.getLogger(SpringInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Begin init spring WebApplicationContext");
		super.contextInitialized(event);
		BeanLocator.setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
		logger.info("Spring WebApplicationContext init successful");
	}
}
