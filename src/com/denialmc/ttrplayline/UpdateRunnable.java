package com.denialmc.ttrplayline;

public class UpdateRunnable implements Runnable {

	private static String firstStart = "<h4>You have somewhere around</h4><h2 class='backshadow'>";
	private static String firstEnd = "</h2><h4>left before you can enter Toontown.</h4>";
	private static String secondContains = "Say, you're in the final stretch!";
	private static String thirdContains = "Hey, it looks like your PlayTime session is active!";
	
	@Override
	public void run() {
		while (true) {
			for (User user : TTRPlayLine.getUsers().values()) {
				String status = Utils.readWithCookie("https://www.toontownrewritten.com/account/queue/update", user.getCookie());
				
				if (status != null) {
					String realStatus = "Unknown";
					
					if (status.startsWith(firstStart) && status.endsWith(firstEnd)) {
						realStatus = status.replace(firstStart, "").replace(firstEnd, "");
					} else if (status.contains(secondContains)) {
						realStatus = "Less than a hour";
					} else if (status.contains(thirdContains)) {
						realStatus = "Gameplay in progress";
					}
					
					user.setStatus(realStatus);
				}
			}
			
			try {
				Thread.sleep(60000L);
			} catch (Exception e) {
				// I don't care
			}
		}
	}
}