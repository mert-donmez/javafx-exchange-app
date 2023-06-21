package com.CoinbyDatabase.util;
import java.sql.*;

public class DatabaseUtil {
	static Connection conn = null;
	
	public static Connection connect() {
		try {
			//
			conn = DriverManager.getConnection("jdbc:mysql://localhost/CoinbyJava","root","mysql");
			return conn;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().toString());
			return null;
		}
	}
	

}
