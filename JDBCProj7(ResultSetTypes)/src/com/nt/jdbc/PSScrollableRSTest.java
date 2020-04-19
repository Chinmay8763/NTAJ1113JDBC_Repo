package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSScrollableRSTest {

	private static final String GET_ALL_EMPLOYEES_QUERY="SELECT EMPNO,ENAME,JOB,SAL FROM EMP";
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create Statement object
			if(con!=null)
				ps=con.prepareStatement(GET_ALL_EMPLOYEES_QUERY,
						                                            ResultSet.TYPE_SCROLL_SENSITIVE,
						                                            ResultSet.CONCUR_UPDATABLE);
			
			//send & execute SQL query to Db s/w
			if(ps!=null)
				rs=ps.executeQuery(GET_ALL_EMPLOYEES_QUERY); 
			if(rs!=null) {
				//Display Records top-bottom
				System.out.println("Top-Bottom records");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				}//while
				//display Records bottom-top
				System.out.println();
				System.out.println("Bottom-Top records");
				while(rs.previous()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				}//while
				
				System.out.println(".....................................................");
				rs.first();
				System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				System.out.println("......................................................");
				rs.last();
				System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				System.out.println("......................................................");
				rs.absolute(3);
				System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				System.out.println("......................................................");
				rs.absolute(-2);
				System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				System.out.println("......................................................");
				rs.relative(-3);
				System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				System.out.println("......................................................");
				rs.relative(2);
				System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				
				System.out.println("Is cursor is there in First record ::"+rs.isFirst());
				System.out.println("......................................................");
				System.out.println("Is cursor is there in Last record ::"+rs.isLast());
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
				if(ps!=null)
					ps.close();
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
