package com.denialmc.ttrplayline;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import lombok.Getter;

import com.denialmc.ttrplayline.config.ConfigHandler;
import com.denialmc.ttrplayline.resources.ResourceHandler;

public class TTRPlayLine implements Runnable {

	@Getter private static Logger logger = Logger.getLogger("TTRPlayLine");
	private static Thread updateThread;
	private static HttpServer webServer;
	@Getter private static Map<String, User> users = new HashMap<String, User>();
	
	public static synchronized User getUser(String name) {
		return users.get(name.toLowerCase());
	}
	
	public static synchronized void setUser(String name, User user) {
		users.put(name.toLowerCase(), user);
	}
	
	public static synchronized void clearUsers() {
		users.clear();
	}
	
	public static void startUpdateThread() {
		if (updateThread == null || !updateThread.isAlive()) {
			logger.info("Starting up update thread...");
			updateThread = new Thread(new UpdateRunnable());
			updateThread.start();
			logger.info("Started up update thread.");
		} else {
			logger.warning("The update thread is already running!");
		}
	}
	
	public static void stopUpdateThread() {
		if (updateThread != null && updateThread.isAlive()) {
			logger.info("Stopping update thread...");
			updateThread.stop();
			updateThread = null;
			logger.info("Stopped update thread.");
		} else {
			logger.warning("The update thread is not running!");
		}
	}
	
	public static void startWebServer() {
		if (webServer == null || !webServer.isAlive()) {
			logger.info("Starting web server...");
			
			webServer = new HttpServer(ConfigHandler.getConfig().getPort());
			
			try {
				webServer.start();
				logger.info("Started web server!");
			} catch (Exception e) {
				Utils.logAndExit("Couldn't start the web server!", e);
			}
		} else {
			logger.warning("The web server is already running!");
		}
	}
	
	public static void stopWebServer() {
		if (webServer != null && webServer.isAlive()) {
			logger.info("Stopping web server...");
			webServer.stop();
			logger.info("Stopped web serer.");
		} else {
			logger.warning("The web server is not running!");
		}
	}
	
	@Override
	public void run() {
		ConsoleHandler handler = new ConsoleHandler();

		handler.setFormatter(new ConsoleLogFormatter());
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
		
		logger.info("Welcome to TTRPlayLine by DenialMC!");
		ResourceHandler.extractResources();
		ConfigHandler.loadConfig();
		startUpdateThread();
		startWebServer();
		
		Scanner scanner = new Scanner(System.in);
		String line;

		while ((line = scanner.next()) != null) {
			if (line.equalsIgnoreCase("stop")) {
				logger.info("TTRPlayLine is stopping!");
				System.exit(0);
			} else if (line.equalsIgnoreCase("startUpdate")) {
				startUpdateThread();
			} else if (line.equalsIgnoreCase("stopUpdate")) {
				stopUpdateThread();
			} else if (line.equalsIgnoreCase("startWeb")) {
				startWebServer();
			} else if (line.equalsIgnoreCase("stopWeb")) {
				stopWebServer();
			} else if (line.equalsIgnoreCase("reload")) {
				ConfigHandler.loadConfig();
			}
		}
		
		scanner.close();
	}
}