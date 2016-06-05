package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OutputWriter {
	PrintWriter textWriter;
	Connection conn = null;
	private String tableName;

	public enum WriterType {
		TEXT, DATABASE, EXCEL
	};

	WriterType writerType;

	public OutputWriter(WriterType type, String dataName) {
		writerType = type;
		switch (type) {
		case TEXT:
			initTextWriter(dataName);
			break;
		case DATABASE:
			initDatabaseConnection(dataName);
			break;

		default:
			break;
		}
	}

	private void initTextWriter(String fileName) {
		try {
			textWriter = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void initDatabaseConnection(String databaseName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, "root", "123456");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(String quote, String author, String topic) {
		switch (writerType) {
		case TEXT:
			textWriter.write(quote);
			break;
		case DATABASE:
			writeToDatabase(quote, author, topic);
		default:
			break;
		}
	}

	private void writeToDatabase(String quote, String author, String topic) {
		String query = " insert into "+tableName+" (content, author, topic)  values (?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, quote);
			preparedStmt.setString(2, author);
			preparedStmt.setString(3, topic);
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void closeOutput() {
		switch (writerType) {
		case TEXT:
			textWriter.close();
			break;
		case DATABASE:
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;
		}
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
