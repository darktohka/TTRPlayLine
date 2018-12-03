package com.denialmc.ttrplayline;

import com.denialmc.ttrplayline.http.NanoHTTPD;

public class HttpServer extends NanoHTTPD {

	public HttpServer(int port) {
		super(port);
	}
	
	@Override
	public Response serve(IHTTPSession session) {
		String path = session.getUri().substring(1);
		
		if (path.isEmpty() || path.equals("favicon.ico")) {
			return new Response("Error 404: No username specified.");
		} else {
			String extension = Utils.getExtension(path);
			
			if (extension != null) {
				String content = Utils.readFile(path);
				
				if (content != null) {
					return new Response(content, extension);
				} else {
					return new Response(String.format("Error 404: File '%s' not found.", path));
				}
			} else {
				User user = TTRPlayLine.getUser(path);
				
				if (user != null) {
					String content = Utils.readFile("index.html");
					
					if (content != null) {
						content = content.replace("[status]", user.getStatus()).replace("[user]", user.getName());
						
						return new Response(content, "html");
					} else {
						return new Response(String.format("System error. Please report this to DenialMC."));
					}
				} else {
					return new Response(String.format("Error 404: Username '%s' not in system.", path));
				}
			}
		}
	}
}