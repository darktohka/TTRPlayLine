package com.denialmc.ttrplayline;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

public class Utils {

	private static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Charset charset = StandardCharsets.UTF_8;
	private static Gson gson = new Gson();

	public static SimpleDateFormat getDate() {
		return date;
	}

	public static Charset getCharset() {
		return charset;
	}

	public static Gson getGson() {
		return gson;
	}
	
	public static String readWithCookie(String link, String cookie) {
		try {
			HttpsURLConnection connection = (HttpsURLConnection) new URL(link).openConnection();
			
			connection.setAllowUserInteraction(false);
			connection.setDoOutput(true);
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");
			connection.addRequestProperty("Cookie", cookie);
			connection.connect();
			
			Scanner scanner = new Scanner(connection.getInputStream());
			String result = scanner.useDelimiter("\\A").next();
			
			scanner.close();
			return result;
		} catch (Exception e) {
			log(String.format("Couldn't read link '%s'!", link), e);
			return null;
		}
	}
	
	public static String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), charset);
		} catch (Exception e) {
			log(String.format("Couldn't read file '%s'!", path), e);
			return null;
		}
	}
	
	public static String getExtension(String name) {
		int i = name.lastIndexOf('.');
		
		if (i > 0) {
			return name.substring(i + 1);
		} else {
			return null;
		}
	}
	
	public static void log(String message) {
		TTRPlayLine.getLogger().severe(message);
	}
	
	public static void log(String message, Throwable throwable) {
		log(message);
		throwable.printStackTrace();
	}
	
	public static void logAndExit(String message) {
		log(message);
		System.exit(0);
	}
	
	public static void logAndExit(String message, Throwable throwable) {
		log(message, throwable);
		System.exit(0);
	}
	
	public static String formatDate(long milliseconds, String level, String message) {
		return String.format("%s [%s] %s\n", date.format(milliseconds), level, message);
	}
}