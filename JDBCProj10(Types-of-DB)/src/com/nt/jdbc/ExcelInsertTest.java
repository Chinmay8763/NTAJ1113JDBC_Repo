package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ExcelInsertTest {

	private static final String INSERT_STUDENT_QUERY="INSERT INTO College.Sheet1 VALUES(?,?,?,?)";
	
	public static void main(String[] args) {
		Scanner sc=null;
		int sno=0;
		String sname=null,sadd=null;
		float avg=0.0f;
		Connection con=null;
		PreparedStatement ps=null;
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Student number::");
				sno=sc.nextInt();
				System.out.println("Enter Student Name::");
				sname=sc.next();
				System.out.println("Enter Sudent Address::");
				sadd=sc.next();
				System.out.println("Enter Student avg::");
				avg=sc.nextFloat();
			}
			
			//register JDBC drive
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:Excel:///D:/Java Package NareshIT/Adv Java by Natraj sir/JDBC");
			
			//create JDBC PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(INSERT_STUDENT_QUERY);
			
				//set values to query params
			if(ps!=null) {
				ps.setInt(1,sno);
				ps.setString(2, sname);
				ps.setString(3, sadd);
				ps.setFloat(4, avg);
			}
			//execute the query
			if(ps!=null)
				count=ps.executeUpdate();
			
			//process the result
			if(count==0)
				System.out.println("Records are not inserted");
			else
				System.out.println("Records are inserted");
			
		}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close JDBC objects
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
			catch(Exception se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
