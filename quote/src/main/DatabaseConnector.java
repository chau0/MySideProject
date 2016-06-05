package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private Connection conn;
	public DatabaseConnector(String databaseName) {
		// TODO Auto-generated constructor stub
		initDatabaseConnection(databaseName);
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
	
	public Quote getRandomQuote(){
		String query = "SELECT * FROM danhngon ORDER BY RAND() LIMIT 1";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
	            String content= resultSet.getString("content");
	            String author = resultSet.getString("author");
	            Quote quote = new Quote();
	            quote.setAuthor(author);
	            quote.setContent(content);
	            return quote;
	            
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
