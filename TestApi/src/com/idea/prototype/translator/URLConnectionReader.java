package com.idea.prototype.translator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionReader {
	public static String getText(String url) throws Exception {
		// String charset =
		// java.nio.charset.StandardCharsets.UTF_8.name();//"UTF-8"
		String charset = "UTF-8";
		Authenticator authenticator = new Authenticator() {

			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("chaulh", "123456".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.tsdv.com.vn", 3128));

		URLConnection connection = new URL(url).openConnection(proxy);

		connection.setRequestProperty("Accept-Charset", charset);
		connection.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}

	public static void main(String[] args) throws Exception {
		String content = URLConnectionReader.getText(args[0]);
		System.out.println(content);
	}
}
