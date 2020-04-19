package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchUpdationTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		int result[]=null;
		int sum=0;
		try {
			//load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create Statement object
			if(con!=null)
				st=con.createStatement();      
			
			//add SQL queries to the batch
			if(st!=null) {
				st.addBatch("INSERT INTO ALL_STUDENT VALUES(1005,'rani',98,87,65)");
				st.addBatch("UPDATE ALL_STUDENT SET M3=M3+5 WHERE M2=35");
				st.addBatch("DELETE FROM ALL_STUDENT WHERE M2=34");
			}
			//execute the batch
			if(st!=null)
				result=st.executeBatch();
			
			//process the result
			for(int i=0;i<result.length;++i)
				sum=sum+result[i];
			
			System.out.println("No of records that are affected::"+sum);
				
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
