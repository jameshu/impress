/*
 * Copyright 2011 sdo.com, Inc. All rights reserved.
 * sdo.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snda.youni.taskweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snda.iyouni.icommon.context.AppSettings;

public class ServerLauncher {

	private static final Logger logger = LoggerFactory.getLogger(ServerLauncher.class);

	public static void main(String[] args) throws Exception {
		AppSettings.getInstance().init("app.properties");
		// launch the ipush server
		WebServer server = new WebServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutdownTask(server)));
		server.run();
	}

	private static class ServerShutdownTask implements Runnable {

		private WebServer server;

		public ServerShutdownTask(WebServer server) {
			this.server = server;
		}

		@Override
		public void run() {
			try {
				server.stop();
			} catch (Exception e) {
				logger.error("Caught exception when stopping dispatch server. Error:[{}]", e.getMessage());
			}
		}
	}
}
