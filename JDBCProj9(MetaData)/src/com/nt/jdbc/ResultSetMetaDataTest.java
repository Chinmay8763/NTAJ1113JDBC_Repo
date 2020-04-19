package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetMetaDataTest {

	private static final String GET_ALL_EMPLOYEES_QUERY="SELECT EMPNO,ENAME,JOB,SAL FROM EMP";
	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		int colCount=0;
		try {
			//load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create Statement object
			if(con!=null)
				st=con.createStatement();
			//send & execute SQL query to Db s/w
			if(st!=null)
				rs=st.executeQuery(GET_ALL_EMPLOYEES_QUERY); 
			//create ResultSet Metadata object
			if(rs!=null)
				rsmd=rs.getMetaData();
			//get columns count
			if(rsmd!=null) {
				colCount=rsmd.getColumnCount();
			//Print colnames
			if(rsmd!=null) {
				for(int i=1;i<=colCount;++i)
					System.out.print(rsmd.getColumnLabel(i)+" ("+rsmd.getColumnTypeName(i)+")    ");
			}
			}
			
			System.out.println();
			
			if(rs!=null) {
				
				//Display Records top-bottom
				while(rs.next()) {
					for(int i=1;i<=colCount;++i) {
						System.out.print(rs.getString(i)+"                                 ");
					}
					System.out.println();
				}//while
				
			}//if
			
	}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close JDBC objects
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
		
	}//main

}//class
