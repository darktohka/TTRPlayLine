package com.denialmc.ttrplayline;

public class User {

	private String name;
	private String uuid;
	private String session;
	private String cookie;
	private String status = "Unknown";
	
	public User(String name, String uuid, String session) {
		this.name = name;
		this.uuid = uuid;
		this.session = session;
		this.cookie = String.format("__cfduid=%s; _ga=GA1.2.1721761837.1401837986; disclaimer=1; _dc=1; p=1; PLAY_SESSION=\"%s\"", uuid, session);
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}

	public String getSession() {
		return session;
	}

	public String getCookie() {
		return cookie;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}