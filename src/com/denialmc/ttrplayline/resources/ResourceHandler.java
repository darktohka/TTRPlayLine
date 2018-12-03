package com.denialmc.ttrplayline.resources;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Logger;

import com.denialmc.ttrplayline.TTRPlayLine;
import com.denialmc.ttrplayline.Utils;

public class ResourceHandler {

	private static final String[] resources = new String[] {"github-markdown.css", "index.html", "settings.json"};

	public static String[] getResources() {
		return resources;
	}
	
	public static void extractResources() {
		Logger logger = TTRPlayLine.getLogger();
		ClassLoader loader = TTRPlayLine.class.getClassLoader();
		
		logger.info("Extracting resources...");
		
		for (String resource : resources) {
			try {
				File file = new File(resource);
				
				if (!file.exists()) {
					Files.copy(loader.getResourceAsStream(resource), file.getAbsoluteFile().toPath());
				}
			} catch (Exception e) {
				Utils.logAndExit(String.format("Couldn't extract resource '%s'!", resource), e);
			}
		}
		
		logger.info("Extracted resources.");
	}
}