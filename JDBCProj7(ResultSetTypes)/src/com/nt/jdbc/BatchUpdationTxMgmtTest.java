package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchUpdationTxMgmtTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		int result[]=null;
		int sum=0;
		boolean flag=false;
		try {
			//load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//Begin Tx
			if(con!=null)
			   con.setAutoCommit(false);
			
			//create Statement object
			if(con!=null)
				st=con.createStatement();  
					
			//add SQL queries to the batch
			if(st!=null) {
				st.addBatch("INSERT INTO ALL_STUDENT VALUES(1006,'rana',98,87,65)");
				st.addBatch("UPDATE ALL_STUDENT SET M3=M3+10 WHERE M2=35");
				st.addBatch("DELETE FROM ALL_STUDENT WHERE M2=55");
			}
			//execute the batch
			if(st!=null)
				result=st.executeBatch();
			
			//perform Tx Mgmt
			for(int i=0;i<result.length;++i) {
				if(result[i]==0)
					flag=true;
				    break;
			}
				
			}//try
		catch(SQLException se) {
			flag=true;
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			flag=true;
			cnf.printStackTrace();
		}
		catch(Exception e) {
			flag=true;
			e.printStackTrace();
		}
		finally {
			//perform Tx Mgmt
			if(con!=null) {
				try {
				if(flag==true) {
					con.rollback();
					System.out.println("Tx rolled back");
				}
				else {
					con.commit();
					System.out.println("Tx commited");
				}
			}//try
				catch(SQLException se) {
					se.printStackTrace();
				}
			}
			
			
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
