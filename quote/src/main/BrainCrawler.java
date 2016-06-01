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

//import net.sf.jcarrierpigeon.WindowPosition;
//import net.sf.jtelegraph.Telegraph;
//import net.sf.jtelegraph.TelegraphQueue;
//import net.sf.jtelegraph.TelegraphType;

public class BrainCrawler {
	public static final String ALONE = "Alone";
	public static final String AMAZING = "Amazing";
	public static final String ART = "Art";
	public static final String ATTITUDE = "Attitude";
	public static final String BEAUTY = "Beauty";
	public static final String BEST = "Best";
	public static final String BIRTHDAY = "Birthday";
	public static final String BRAINY = "Brainy";
	public static final String BUSINESS = "Business";
	public static final String CHANCE = "Chance";
	public static final String CHANGE = "Change";
	public static final String COMMUNICATION = "Communication";
	public static final String COMPUTERS = "Computers";
	public static final String COOL = "Cool";
	public static final String COURAGE = "Courage";
	public static final String DATING = "Dating";
	public static final String DEATH = "Death";
	public static final String DESIGN = "Design";
	public static final String DREAMS = "Dreams";
	public static final String EDUCATION = "Education";
	public static final String EQUALITY = "Equality";
	public static final String EXPERIENCE = "Experience";
	public static final String FAILURE = "Failure";
	public static final String FAITH = "Faith";
	public static final String FAMOUS = "Famous";
	public static final String FEAR = "Fear";
	public static final String FITNESS = "Fitness";
	public static final String FORGIVENESS = "Forgiveness";
	public static final String FREEDOM = "Freedom";
	public static final String FRIENDSHIP = "Friendship";
	public static final String FUNNY = "Funny";
	public static final String FUTURE = "Future";
	public static final String GREAT = "Great";
	public static final String HAPPINESS = "Happiness";
	public static final String HEALTH = "Health";
	public static final String HOPE = "Hope";
	public static final String HUMOR = "Humor";
	public static final String IMAGINATION = "Imagination";
	public static final String INDEPENDENCE = "Independence";
	public static final String INSPIRATIONAL = "Inspirational";
	public static final String INTELLIGENCE = "Intelligence";

	public static final String[] TOPICS = { ATTITUDE, COURAGE, DREAMS, FAILURE, FEAR, FRIENDSHIP, GREAT, HAPPINESS,
			INSPIRATIONAL };

	public static void main(String[] args) throws Exception {
		NotificationBox notificationBox = new NotificationBox();
		Thread runThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Random random = new Random();
					String web = "http://www.brainyquote.com";
					String htmlContent = getText(web + "/quotes/topics.html");
					// log(htmlContent);
					Document doc = Jsoup.parse(htmlContent);
					// Elements topicColumnElements =
					// doc.select("div.bq_fl.content");
					// Elements listTopicElements =
					// topicColumnElements.get(random.nextInt(topicColumnElements.size()))
					// .select("div.bqLn");
					String topicName = TOPICS[random.nextInt(TOPICS.length)];
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
					System.out.println("topic id :" + topicName);
					// log(topicElement.text());
					// log(topicElement.select("a").attr("href"));
					// String topicLink = web +
					// topicElement.select("a").attr("href");
					String topicLink = web + "/quotes/topics/topic_" + topicName + ".html";
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
					// Telegraph telegraph = new Telegraph(author, quote,
					// TelegraphType.HOME, WindowPosition.BOTTOMRIGHT,
					// 20000);
					// TelegraphQueue queue = new TelegraphQueue();
					// queue.add(telegraph);

					notificationBox.create(quote, author, 30000);
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
