package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTest7 {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		int count=0;
		try {
			//Register JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			//Create Statement object
			if(con!=null)
			st=con.createStatement();
			//send & execute SQL Query in database software
			if(st!=null)
				rs=st.executeQuery("SELECT COUNT(*) FROM EMP");
			//process the ResultSet
			if(rs!=null) {
				if(rs.next()) {
					count=rs.getInt("count(*)");
					//System.out.println("count"+rs.getInt(1));
				}//if
				}//if
			System.out.println("Records count:"+count);
			}//try
		catch(SQLException se){//known Exception
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){//known Exception
			cnf.printStackTrace();
		}
		catch(Exception e){//unknown Exception
			e.printStackTrace();
		}
		finally{
			//close objects
			try{
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(st!=null)
					st.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(con!=null)
					con.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}//finally
		}//main
	}//class

