package Main;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static final String[] URL = new String[] {
			"http://daytiengnhatban.com/ngu-phap-n5-01-10",
			"http://daytiengnhatban.com/ngu-phap-n5-p111-20",
			"http://daytiengnhatban.com/ngu-phap-tieng-nhat-ngu-phap-n5-mau-cau-21-30",
			"http://daytiengnhatban.com/ngu-phap-tieng-nhat-ngu-phap-n5-mau-cau-31-40",
			"http://daytiengnhatban.com/ngu-phap-n5-mau-cau-41-50",
			"http://daytiengnhatban.com/ngu-phap-n5-mau-cau-51-60-phan-cuoi" };

	public static void main(String[] args) throws Exception {
		Crawler crawler = new Crawler();
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("test/workbook.xlsx");
		Sheet sheet = wb.createSheet("new sheet");
		CellStyle style = wb.createCellStyle(); // Create new style
		style.setWrapText(true); // Set wordwrap
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		int rowCount = 0;
		Row headerRow = sheet.createRow(rowCount);
		Cell headerCell = headerRow.createCell(1);
		headerCell.setCellValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		sheet.autoSizeColumn(1);
		rowCount ++;
		for (String u : URL) {
			int nextRow = crawler.doJob(sheet, style, u, rowCount);
			rowCount = nextRow;
		}
		sheet.setColumnWidth(0, 100);
		sheet.autoSizeColumn(0);

		wb.write(fileOut);
		fileOut.close();
		wb.close();
	}
}
