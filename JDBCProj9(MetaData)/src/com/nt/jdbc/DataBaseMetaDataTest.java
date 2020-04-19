package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseMetaDataTest {

	public static void main(String[] args) {
		Connection con=null;
		DatabaseMetaData dbmd=null;
		try {
			//load jdbc driver class
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			  //establish the connection
			 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott", "tiger");
			 //create DatabaseMeta object
			dbmd=con.getMetaData();
			System.out.println("dbmd class name::"+con.getClass());
			System.out.println("DB s/w Name::"+dbmd.getDatabaseProductName());
			System.out.println("DB s/w Version::"+dbmd.getDatabaseProductVersion());
			System.out.println("Oracle Major Version::"+dbmd.getDatabaseMajorVersion());
			System.out.println("Oracle Minor Version::"+dbmd.getDatabaseMinorVersion());
			System.out.println("JDBC Major Version::"+dbmd.getJDBCMajorVersion());
			System.out.println("JDBC Minor Version::"+dbmd.getJDBCMinorVersion());
			System.out.println("JDBC Driver Version::"+dbmd.getDriverMajorVersion()+"."+dbmd.getDriverMinorVersion());
			System.out.println("All SQL Keywords::"+dbmd.getSQLKeywords());
			System.out.println("All Numeric Functions::"+dbmd.getNumericFunctions());
			System.out.println("All System Functions::"+dbmd.getSystemFunctions());
			
			System.out.println("Max DB table in SELECT Query::"+dbmd.getMaxTablesInSelect());
			System.out.println("Max CHARS in DB table name::"+dbmd.getMaxTableNameLength());
			System.out.println("Max Row Size::"+dbmd.getMaxRowSize());
			System.out.println("Supports Procedures::"+dbmd.supportsStoredProcedures());
			
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close the JDBC objects
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
		
	}//main(-)

}//class
