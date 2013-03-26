package com.snda.youni.taskweb;

import org.eclipse.jetty.server.nio.SelectChannelConnector;

import com.snda.youni.taskweb.common.AppSettings;

public class WebConnector extends SelectChannelConnector {

	public static final int DEFAULT_PORT = 8080;

	public static final int DEFAULT_ACCEPTORS = Runtime.getRuntime().availableProcessors() + 1;

	public static final int DEFAULT_ACCEPT_QUEUE_SIZE = 50;

	public static final int DEFAULT_MAX_IDLE_TIME = 3000;

	public WebConnector() {
		super();
		setPort(AppSettings.getInstance().getInt("jetty.connector.port", DEFAULT_PORT));
		setAcceptors(AppSettings.getInstance().getInt("jetty.connector.acceptors", DEFAULT_ACCEPTORS));
		setAcceptQueueSize(AppSettings.getInstance().getInt("jetty.connector.acceptQueueSize",
				DEFAULT_ACCEPT_QUEUE_SIZE));
		setMaxIdleTime(AppSettings.getInstance().getInt("jetty.connector.maxIdleTime", DEFAULT_MAX_IDLE_TIME));
	}

	@Override
	public String toString() {
		return String.format("IPush Connector:[port=%d, acceptors=%d, acceptQueueSize=%d, maxIdleTime=%d]", getPort(),
				getAcceptors(), getAcceptQueueSize(), getMaxIdleTime());
	}

}
