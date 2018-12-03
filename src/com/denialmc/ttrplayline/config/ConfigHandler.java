package com.denialmc.ttrplayline.config;

import java.util.logging.Logger;

import com.denialmc.ttrplayline.TTRPlayLine;
import com.denialmc.ttrplayline.User;
import com.denialmc.ttrplayline.Utils;

public class ConfigHandler {

	private static Config config;

	public static Config getConfig() {
		return config;
	}
	
	public static void loadConfig() {
		Logger logger = TTRPlayLine.getLogger();
		
		logger.info("Loading config...");
		String json = Utils.readFile("settings.json");
		
		if (json != null) {
			try {
				config = Utils.getGson().fromJson(json, Config.class);
				TTRPlayLine.clearUsers();
				
				for (ConfigUser user : config.getUsers()) {
					String name = user.getName();
					
					TTRPlayLine.setUser(name, new User(name, user.getUuid(), user.getSession()));
				}
				
				logger.info("Loaded config.");
			} catch (Exception e) {
				Utils.logAndExit("Couldn't read config: Incorrect JSON syntax!", e);
			}
		} else {
			Utils.logAndExit("Couldn't read config: File missing.");
		}
	}
}	