package com.ajr.process.service.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InvokeInitCharge {

	private static String dbURL = "jdbc:derby://localhost:1527/ProcessServiceServerDB;create=true;user=PROCESSSERVICESCHEMA;password=ajr";

	// jdbc Connection
	private static Connection conn = null;
	private static Statement stmt = null;

	public static void main(String[] args) {
		createConnection();
		insertMenus();
		shutdown();
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	private static void insertMenus() {
		for (int k = 1; k < 7; k++) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					
					// System.out.println("Data: " + k + ", " + i + ", " + j + ".");

					try {
						stmt = conn.createStatement();
						stmt.execute("INSERT INTO MENUSDATA (PROJECT, MENUNUM, SUBMENU1NUM, SUBMENU2NUM, LABEL) VALUES ("
							+ " 'XPTO', " + Integer.toString(k) + ", " + Integer.toString(i) + ", " + Integer.toString(j) + ", 'Label')");
						stmt.close();
					} catch (SQLException sqlExcept) {
						sqlExcept.printStackTrace();
					}
				}
			}
		}

	}

	private static void shutdown() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(dbURL + ";shutdown=true");
				conn.close();
			}
		} catch (SQLException sqlExcept) {

		}

	}

}
