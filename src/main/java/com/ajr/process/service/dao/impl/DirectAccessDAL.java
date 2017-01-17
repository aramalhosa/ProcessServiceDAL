package com.ajr.process.service.dao.impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.sql.Select;

import com.ajr.process.service.entity.ChainProjComponent;
import com.ajr.process.service.entity.ComponentRelation;

public class DirectAccessDAL {

	//private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
	private static String dbURL = "jdbc:derby:C://AJR_DataBases/ProcessServiceDB;create=true;user=PROCESSSERVICESCHEMA;password=";
	
	private static String tableName = "CHAINPROJRELATION";
	// jdbc Connection
	private static java.sql.Connection conn = null;
	private static Statement stmt = null;

	public static void main(String[] args) {
		//createConnection();
		//selectRelation();
		selectRelationJPA();
		//selectRelationJPA1();
		//shutdown();
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	
	private static void selectRelationJPA() {
		
		List<ComponentRelation> resultQuery = new ArrayList<ComponentRelation>();
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "ProcessServiceDAL" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Query q = entityManager.createQuery(
		        "Select c from ComponentRelation c"); 		
		
		resultQuery = q.getResultList();
		
		for (ComponentRelation l : resultQuery) {

			System.out.println("Relation-> Project: " + l.getProjectId() + " Component1 - " + l.getChainProjectComponent().getDescription() + " Component2 - " + l.getChainProjectComponent2().getAttribute());

		}
		
		entityManager.close();
	}
	
	private static void selectRelationJPA1() {
		
		List<ChainProjComponent> resultQuery = new ArrayList<ChainProjComponent>();
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "ProcessServiceDAL" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Query q = entityManager.createQuery(
		        "Select c from ChainProjComponent c"); 		
	
		resultQuery = q.getResultList();
		
		for (ChainProjComponent l : resultQuery) {

			System.out.println("Component -> Project: " + l.getAttribute() + " Component1 - " + l.getDescription());
		}
		
		entityManager.close();
	}	
	
	private static void selectRelation() {
		try {

			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select * from " + tableName);
			ResultSetMetaData rsmd = results.getMetaData();
			int numberCols = rsmd.getColumnCount();
			for (int i = 1; i <= numberCols; i++) {
				// print Column Names
				System.out.print(rsmd.getColumnLabel(i) + "\t\t");
			}

			System.out
					.println("\n-------------------------------------------------");

			while (results.next()) {
				int id = results.getInt(1);
				String restName = results.getString(2);
				String cityName = results.getString(3);
				System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
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
