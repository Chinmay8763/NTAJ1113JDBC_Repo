//SELECT COUNT(*) FROM USERLIST WHERE UNAME='RAJA' AND PWD='RANI';
                  //COUNT(*)
                  //1(VALID CREDENTIALS)

//SELECT COUNT(*) FROM USERLIST WHERE UNAME='RAJA1' AND PWD='RANI';
                  //COUNT(*)
                  //0(INVALID CREDENTIALS)

package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginApp {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String query=null;
		String user=null,pass=null;
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Username::");
				user=sc.nextLine();
				System.out.println("Enter Password::");
				pass=sc.nextLine();
			}
			//convert input values as required for the sql query
			user="'"+user+"'";
			pass="'"+pass+"'";
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create the Statement object
			if(con!=null)
				st=con.createStatement();
			//Prepare SQL Query
			//select count(*) from userlist where uname='RAJA' and pwd='RANI';
			query="SELECT COUNT(*) FROM USERLIST WHERE UNAME= "+user+" AND PWD="+pass;
			System.out.println(query);
			//execute the query
			if(st!=null)
				rs=st.executeQuery(query);
			//Process the ResultSet
			if(rs!=null) {
				if(rs.next())
					count=rs.getInt(1);        //1(valid credentials)
					System.out.println(count);
				}
				if(count==0)                      //0(invalid credentials)
					System.out.println("Invalid Credentials,Login Failed");
				else
					System.out.println("Valid Credentials,Login Successful");
			}//try
		
		catch(SQLException se) {  //To handle known exception
			se.printStackTrace();
			System.out.println("Record Insertion Failed");
		}
		catch(ClassNotFoundException cnf) {     //To handle known exception
			System.out.println("Record Insertion Failed");
			cnf.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("Record Insertion Failed");
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
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
		
	}//main
	
}//class
