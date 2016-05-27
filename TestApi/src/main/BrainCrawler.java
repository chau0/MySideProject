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
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

public class BrainCrawler {

	public static void main(String[] args) throws Exception {
		Thread runThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Random random = new Random();
					String web = "http://www.brainyquote.com";
					String htmlContent = getText(web + "/quotes/topics.html");
					// log(htmlContent);
					Document doc = Jsoup.parse(htmlContent);
					Elements topicColumnElements = doc.select("div.bq_fl.content");
					Elements listTopicElements = topicColumnElements.get(random.nextInt(topicColumnElements.size()))
							.select("div.bqLn");
					Element topicElement = listTopicElements.get(random.nextInt(listTopicElements.size()));
					log(topicElement.text());
					log(topicElement.select("a").attr("href"));
					String topicLink = web + topicElement.select("a").attr("href");
					String topicHtmlContent = getText(topicLink);
					Document topicDoc = Jsoup.parse(topicHtmlContent);
					Elements navElements = topicDoc.select("div.row.paginationContainer").first().select("li");
					int numberPage = Integer.parseInt(navElements.get(navElements.size() - 2).text());
					int pageChoose = random.nextInt(numberPage) + 1;
					if (pageChoose > 1) {
						topicLink = topicLink.substring(0, topicLink.length() - 5) + pageChoose + ".html";
						topicHtmlContent = getText(topicLink);
						topicDoc = Jsoup.parse(topicHtmlContent);
					}
					log("page choose :" + pageChoose);
					Elements boxElements = topicDoc.select("div.boxyPaddingBig");
					Element quoteElement = boxElements.get(random.nextInt(boxElements.size()));
					String quote = quoteElement.select("span.bqQuoteLink").first().text();
					String author = quoteElement.select("div.bq-aut").first().text();
					Telegraph telegraph = new Telegraph(author, quote, TelegraphType.HOME, WindowPosition.BOTTOMRIGHT,
							20000);
					TelegraphQueue queue = new TelegraphQueue();
					queue.add(telegraph);
					log(quote);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		runThread.start();

	}

	public static void log(String message) {
		System.out.println(message);
	}

	public static String getText(String url) {
		String charset = "UTF-8";
		Authenticator authenticator = new Authenticator() {

			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("chaulh", "123456".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.tsdv.com.vn", 3128));

		URLConnection connection;
		try {
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

}
