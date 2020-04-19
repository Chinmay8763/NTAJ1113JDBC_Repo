package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlSelectTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			//register JDBC driver
			//Class.forName("org.git.mm.mysql.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");              //com.mysql.jdbc.Driver is now deprecated
			//Establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///ntaj413", "root", "admin");
			
			//create Statement object
			if(con!=null)
				st=con.createStatement();
			//send and execute SQL Query in Database s/w
			if(st!=null)
				rs=st.executeQuery("select * from product");
			//process the ResultSet
			if(rs!=null) {
				while(rs.next()){
					System.out.println(rs.getInt(1)+"    "+rs.getString(2)+"    "+rs.getString(3));
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
			//close the JDBC objects
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
