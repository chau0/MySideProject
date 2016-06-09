package com.idea.prototype.translator;

import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Translator {

	public static String translate(String to_translate, String to_langage, String from_langage) {
		String page, hl, sl, q;
		String charset = "UTF-8";

		try {
			hl = URLEncoder.encode(to_langage, charset);
			sl = URLEncoder.encode(from_langage, charset);
			q = URLEncoder.encode(to_translate, charset);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		String query = String.format("https://translate.google.com/m?hl=%s&sl=%s&q=%s", hl, sl, q);

		try {
			page = URLConnectionReader.getText(query);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		Document pageHtml = Jsoup.parse(page);
		Element result = pageHtml.select("div.t0").first();
		if (result == null) {
			return to_translate;
		}
		return result.text();
	}

	public static void main(String[] args) {
		String text = "";
		System.out.println(text);
		System.out.println(" >> " + translate(text, "en", "jp"));
	}
}