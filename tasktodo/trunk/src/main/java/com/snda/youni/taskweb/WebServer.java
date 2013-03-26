package com.snda.youni.taskweb;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snda.youni.taskweb.common.AppSettings;

public class WebServer {

	private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

	private static Server server = null;

	public void run() throws Exception {
		if (server != null && server.isRunning()) {
			logger.warn("server has been start.");
			return;
		}

		server = new Server();
		server.setConnectors(getConnectors());
		server.setThreadPool(getThreadPool());
		server.setStopAtShutdown(true);
		server.setSendServerVersion(true);
		server.setHandler(getWebAppContext());
		server.start();
		logger.info("server launches, success.");
		server.join();
	}

	private Connector[] getConnectors() {
		return new Connector[] { new WebConnector() };
	}

	private ThreadPool getThreadPool() {
		final int corePoolSize = AppSettings.getInstance().getInt("jetty.threadPool.corePoolSize", 50);
		final int maxPoolSize = AppSettings.getInstance().getInt("jetty.threadPool.maxPoolSize", 100);
		logger.info("Jetty Server thread pool:[corePoolSize={}, maxPoolSize={}]", corePoolSize, maxPoolSize);
		return new ExecutorThreadPool(corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS);
	}

	private WebAppContext getWebAppContext() {

		String webapppath = AppSettings.getInstance().getString("webapp.path", "/src/main/webapp");
		String path = WebServer.class.getResource("/").getFile().replaceAll("/target/(.*)", "");
		path = path.replaceAll("/conf/(.*)", "")+ webapppath;
		
		logger.debug("web server path = "+path);
		
		return new WebAppContext(path, "/");
	}

	public void stop() throws Exception {
		if (server != null) {
			server.stop();
			server = null;
		}
	}
}
