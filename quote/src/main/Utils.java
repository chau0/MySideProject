package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
	public static String getText(String url) {
		String charset = "UTF-8";
		URLConnection connection;
		try {
			Authenticator authenticator = new Authenticator() {

				public PasswordAuthentication getPasswordAuthentication() {
					return (new PasswordAuthentication("chaulh", "123456".toCharArray()));
				}
			};
			Authenticator.setDefault(authenticator);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.tsdv.com.vn", 3128));
			connection = new URL(url).openConnection(proxy);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			StringBuilder response = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);

			in.close();
			return response.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
	public static void log(String message) {
		System.out.println(message);
	}

}
