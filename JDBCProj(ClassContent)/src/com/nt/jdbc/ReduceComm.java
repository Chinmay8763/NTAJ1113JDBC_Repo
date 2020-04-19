package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ReduceComm {

	public static void main(String[] args) {
		
		Connection con=null;
		Statement st=null;
		int result=0,count=1;
		String query1=null;
		try {
			//load jdbc object
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			query1="UPDATE EMP1 SET COMM=COMM-(COMM*0.1) WHERE COMM IS NOT NULL";
			System.out.println(query1);
			//create statement
			if(con!=null)
				st=con.createStatement();
			if(st!=null)
				{
				result=st.executeUpdate(query1);
				count=count++;
				}
			if(result==0)
				System.out.println("record are not found");
			else
				System.out.println(count+" record are found and update");
		}//try
		catch (SQLException se) {
			se.printStackTrace();
		}
		catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				if(st!=null)
					st.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//main
}//class