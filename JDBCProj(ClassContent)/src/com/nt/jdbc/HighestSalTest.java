//select * from( select ename, sal, dense_rank() over(order by sal desc)r from emp) where r=&n;

package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HighestSalTest {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con= null;
		int no=0;
		boolean flag=false;
		ResultSet rs=null;
		Statement st=null;
		
		try {
			sc=new Scanner(System.in);
			System.out.println("Enter the Employee rank to see the highest salary");
			no=sc.nextInt();
			//load jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			/*
			 * String
			 * query="select empno,ename,job,sal from( select ename, sal, dense_rank() over(order by sal desc)r from emp) where r="
			 * +no;
			 */
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			if(con!=null)
				st=con.createStatement();
				String query1="SELECT ENAME,SAL FROM(SELECT ENAME,SAL,DENSE_RANK()OVER(ORDER BY SAL DESC)R FROM EMP)WHERE R="+no;
				System.out.println(query1);
				System.out.println("Results are::");
				//send and execute query
					if (st!=null)
					rs=st.executeQuery(query1);
					//process the resultset
					if(rs!=null){
						while (rs.next()) {
							flag=true;
							System.out.println(rs.getString(1)+"  "+rs.getFloat(2));
						}
						}
						if (flag==true)
							System.out.println("Congratulations!!Records found and displayed");
						else 
							System.out.println("Ooops!!Records are not found");
				
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
				//close object
				try {
					if(rs!=null)
						rs.close();
				}
				catch (SQLException se) {
					se.printStackTrace();
					
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
				catch (SQLException se) {
					se.printStackTrace();
				}
				try {
					if(sc!=null)
						sc.close();
				}
				catch (Exception e) {
					e.printStackTrace();
					
				}
			}
				
	}}
			
