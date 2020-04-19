//SelectTest4.java
package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*JDBC App to get Highest Salary of employee details
 * version:1.0
 * author:Team-Natraz
 * Date:2020/02/28
 */
public class SelectTest4 {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		boolean flag=false;
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			//create Statement
			if(con!=null)
				st=con.createStatement();
			//send & execute SQL Query in Database software
			if(st!=null)
				rs=st.executeQuery("SELECT EMPNO,ENAME,SAL,JOB FROM EMP WHERE SAL=(SELECT MAX(SAL)FROM EMP)");
			//process the Resultset
			if(rs!=null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
					flag=true;
				}//while
				if(flag==false)
					System.out.println("No Records Found");
			}//if
		}//try
		catch(SQLException se) {//known Exception
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {//known Exception
			cnf.printStackTrace();
		}
		catch(Exception e) {////Unknown Exception
			e.printStackTrace();
		}
		finally {
			//close objects
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