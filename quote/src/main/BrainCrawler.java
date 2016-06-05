package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.OutputWriter.WriterType;

//import net.sf.jcarrierpigeon.WindowPosition;
//import net.sf.jtelegraph.Telegraph;
//import net.sf.jtelegraph.TelegraphQueue;
//import net.sf.jtelegraph.TelegraphType;

public class BrainCrawler {

	String web = "http://www.brainyquote.com";
	OutputWriter writer = new OutputWriter(WriterType.DATABASE,"quote_crawler");

	public static void main(String[] args) throws Exception {
		BrainCrawler brainCrawler = new BrainCrawler();
		brainCrawler.onCrawlerAll();

	}

	public BrainCrawler() {
	}

	public void onCrawlerAll() {
		String listTopicPage = getText(web + "/quotes/topics.html");
		Document listTopicPageDocument = Jsoup.parse(listTopicPage);
		Elements columenElements = listTopicPageDocument.select("div.bq_fl.content");
		for (Element colElement : columenElements) {
			int topicIndex = 0;
			for (Element topicElement : colElement.select("div.bqLn")) {
				topicIndex++;
				if (topicIndex % 2 == 0) {
					log(topicElement.text());
//					log(topicElement.select("a").first().attr("href"));
//					writer.write(quote, author, topic);
					onCrawlerTopic(topicElement.select("a").first().attr("href"),topicElement.text());
				}

			}
		}

	}

	public void onCrawlerTopic(String topicLink,String topic) {
		String topicPage = getText(web + topicLink);
		Document topicPageDocument = Jsoup.parse(topicPage);
		Elements navElements = topicPageDocument.select("div.row.paginationContainer").first().select("li");
		int numberPage = Integer.parseInt(navElements.get(navElements.size() - 2).text());
		log("" + numberPage);
		
		for (int i = 0; i < numberPage; i++) {
		    String	visitLink = i == 0 ? topicLink : topicLink.substring(0, topicLink.length() - 5) + i + ".html";
		    log(web+visitLink);
			topicPage = getText(web + visitLink);
			topicPageDocument = Jsoup.parse(topicPage);
			Elements boxElements = topicPageDocument.select("div.boxyPaddingBig");
			for (Element boxEl : boxElements) {
				String quote = boxEl.select("span.bqQuoteLink").first().text();
				String author = boxEl.select("div.bq-aut").first().text();
				writer.write(quote, author, topic);
				// log(quote);
			}

		}
	}

	// public static void doCrawler() {
	// Random random = new Random();
	// String web = "http://www.brainyquote.com";
	// String htmlContent = getText(web + "/quotes/topics.html");
	// Document doc = Jsoup.parse(htmlContent);
	// Elements topicColumnElements =
	// doc.select("div.bq_fl.content");
	// Elements listTopicElements =
	// topicColumnElements.get(random.nextInt(topicColumnElements.size()))
	// .select("div.bqLn");
	// String topicName = TOPICS[random.nextInt(TOPICS.length)];
	// Element topicElement = listTopicElements.get(topicID);
	// listTopicElements.select(query)
	// ArrayList<String> topicNames = new ArrayList<>();
	// for (int i = 0, l = listTopicElements.size(); i < l; i++)
	// {
	// System.out.println("public static final String " +
	// listTopicElements.get(i).text().toUpperCase()
	// + " = \"" + listTopicElements.get(i).text() + "\";");
	// topicNames.add(listTopicElements.get(i).text());
	// }
	// System.out.println(topicNames.toString().toUpperCase());
	// System.out.println("topic id :" + topicName);
	// log(topicElement.text());
	// log(topicElement.select("a").attr("href"));
	// String topicLink = web +
	// topicElement.select("a").attr("href");
	// String topicLink = web + "/quotes/topics/topic_" + topicName +
	// ".html";
	// String topicHtmlContent = getText(topicLink);
	// Document topicDoc = Jsoup.parse(topicHtmlContent);
	// Elements navElements =
	// topicDoc.select("div.row.paginationContainer").first().select("li");
	//
	// int numberPage = Integer.parseInt(navElements.get(navElements.size()
	// - 2).text());
	// int pageChoose = random.nextInt(numberPage) + 1;
	// if (pageChoose > 1) {
	// topicLink = topicLink.substring(0, topicLink.length() - 5) +
	// pageChoose + ".html";
	// topicHtmlContent = getText(topicLink);
	// topicDoc = Jsoup.parse(topicHtmlContent);
	// }
	// log("page choose :" + pageChoose);
	// Elements boxElements = topicDoc.select("div.boxyPaddingBig");
	// Element quoteElement =
	// boxElements.get(random.nextInt(boxElements.size()));
	// String quote =
	// quoteElement.select("span.bqQuoteLink").first().text();
	// String author = quoteElement.select("div.bq-aut").first().text();
	// // Telegraph telegraph = new Telegraph(author, quote,
	// // TelegraphType.HOME, WindowPosition.BOTTOMRIGHT,
	// // 20000);
	// // TelegraphQueue queue = new TelegraphQueue();
	// // queue.add(telegraph);
	//
	// notificationBox.create(quote, author, 30000);
	// log(quote);
	// }

	public void log(String message) {
		System.out.println(message);
	}

	public String getText(String url) {
		String charset = "UTF-8";
		// Authenticator authenticator = new Authenticator() {
		//
		// public PasswordAuthentication getPasswordAuthentication() {
		// return (new PasswordAuthentication("chaulh",
		// "123456".toCharArray()));
		// }
		// };
		// Authenticator.setDefault(authenticator);
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
		// InetSocketAddress("proxy.tsdv.com.vn", 3128));

		URLConnection connection;
		try {
			connection = new URL(url).openConnection();
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
