package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTest {

	public static void main(String[] args) {
		Scanner sc=null;
		int empno=0;
		String ename=null;
		String job=null;
		float sal=0.0f;
		Connection con=null;
		Statement st=null;
		int result=0;
		String query=null;
		try {
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter employee number");
				empno=sc.nextInt();
				System.out.println("Enter employee name");
				ename=sc.next();
				System.out.println("Enter employee job");
				job=sc.next();
				System.out.println("Enter employee salary");
				sal=sc.nextFloat();
			}//if
			//Convert input values as required for the SQL Query
				ename="'"+ename+"'";
				job="'"+job+"'";
				//register JDBC driver
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//establish the collection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
				//create Statement object
				if(con!=null)
				st=con.createStatement();
				//Prepare the SQL Query
				query="INSERT INTO EMP2 VALUES("+empno+","+ename+","+job+","+sal+")";
				System.out.println(query);
				//send & execute SQL Query in database s/w
				if(st!=null)
					result=st.executeUpdate(query);
				//process the result
				if(result==0)
					System.out.println("Records insertion failed");
				else
					System.out.println("Records insertion Succeed");
				
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
			//close JDBC objects
			try {
				if(sc!=null)
					sc.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
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
		}//finally
	}//main
}//class