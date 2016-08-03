package Main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	public int doJob(Sheet sheet, CellStyle style, String url, int countRow)
			throws Exception {
		String webcontent = Utils.getText(url);
		Document mainDocument = Jsoup.parse(webcontent);
		int startRow = countRow;
		for (Element entryElement : mainDocument.select("div.entry")) {
			Elements headerElement = entryElement.select("h3");
			Elements explainElement = entryElement.select("div.crayon-plain-wrap");
			System.out.println("size header :"+headerElement.size());
			System.out.println("size explain :"+explainElement.size());
			for(int i=0;i<headerElement.size();i++){
				Row row = sheet.createRow(startRow);
				Cell cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(headerElement.get(i).text());
				
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue(explainElement.get(i).text());
				startRow++;
			}

		}
		
		return startRow;

	}
}
