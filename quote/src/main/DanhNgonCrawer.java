package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.OutputWriter.WriterType;

public class DanhNgonCrawer {
	String web = "http://danhngoncuocsong.vn";
	OutputWriter writer = new OutputWriter(WriterType.DATABASE,"quote_crawler");
	public DanhNgonCrawer() {
	 writer.setTableName("danhngon");
	}
	public void onCrawler() {
		String webUrl = web + "/tac-gia.html";
		Document webPageDocument = Jsoup.parse(Utils.getText(webUrl));
		for (Element rowElement : webPageDocument.select("div.row")) {
			for (Element authorElement : rowElement.select("div.bqLn")) {
				Utils.log(authorElement.text());
				onCrawlerTopic(authorElement.select("a").attr("href"), authorElement.text());
			}
		}
	}

	public void onCrawlerTopic(String authorLink, String author) {
		String topicPage = Utils.getText(web + authorLink);
		Document topicPageDocument = Jsoup.parse(topicPage);
		Elements navElements = topicPageDocument.select("div.pagination.bqNPgn.pagination-small").first().select("li");
		int numberPage = navElements.size() <= 2 ? 0 : Integer.parseInt(navElements.get(navElements.size() - 2).text());
		Utils.log("" + numberPage);

		for (int i = 0; i < numberPage; i++) {
			String visitLink = i == 0 ? authorLink : authorLink.substring(0, authorLink.length() - 5) + i + ".html";
			Utils.log(web + visitLink);
			topicPage = Utils.getText(web + visitLink);
			topicPageDocument = Jsoup.parse(topicPage);
			Elements boxElements = topicPageDocument.select("div.boxyPaddingBig");
			for (Element boxEl : boxElements) {
				String quote = boxEl.select("span.bqQuoteLink").first().text();
				writer.write(quote, author, "Other");
//				Utils.log(quote);
			}

		}
	}

	public static void main(String[] args) {
		DanhNgonCrawer crawer = new DanhNgonCrawer();
		crawer.onCrawler();

	}

}
