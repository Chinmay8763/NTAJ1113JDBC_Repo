package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PstInsertTest {
	public static void main(String[] args) {
		Scanner sc=null;
		int count=0;
		Connection con=null;
		String query=null;
		PreparedStatement ps=null;
		int no=0;
		String name=null;
		String addrs=null;
		int result=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			System.out.println("Enter Student count:");
			if(sc!=null)
				count=sc.nextInt();
			//register JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the coonection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//prepare the SQL Query
			query="INSERT INTO STUDENT1 VALUES(?,?,?)";
			if(con!=null)
				ps=con.prepareStatement(query);
			//read multiple sets of input values from enduser
			if(sc!=null) {
				for(int i=1;i<=count;i++) {
					System.out.println("Enter "+i+" student details");
					System.out.println("Enter Number:");
					no=sc.nextInt();
					System.out.println("Enter Name:");
					name=sc.next();
					System.out.println("Enter Address");
					addrs=sc.next();
					//set the input values read from enduser to query params
					ps.setInt(1,no);
					ps.setString(2,name);
					ps.setString(3, addrs);
					//execute the query
					result=ps.executeUpdate();
					if(result==0)
						System.out.println(i+" Student details are not inserted");
					else
						System.out.println(i+" Student details are inserted");
				}//for
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
