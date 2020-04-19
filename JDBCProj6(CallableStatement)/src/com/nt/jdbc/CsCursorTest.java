package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.jdbc.internal.OracleTypes;

/*CREATE OR REPLACE PROCEDURE P_GET_EMPDETAILSBYINITCHARS 
(
  INITCHARS IN VARCHAR2 
, DETAILS OUT SYS_REFCURSOR 
) AS 
BEGIN

  open details for
    select empno,ename,job,sal,deptno from emp where ename like INITCHARS;
    
END P_GET_EMPDETAILSBYINITCHARS;*/

public class CsCursorTest {

private static final String CALL_PROCEDURE="{CALL P_GET_EMPDETAILSBYINITCHARS(?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		String initChars=null;
		Connection con=null;
		CallableStatement cs=null;
		ResultSet rs=null;
		boolean flag=false;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter emp's initial chars::");
				initChars=sc.next().toUpperCase()+"%";
			}
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//Create JDBC CallableStatement object
			if(con!=null)
				cs=con.prepareCall(CALL_PROCEDURE);
			//register OUT params with JDBC
			if(cs!=null)
				cs.registerOutParameter(2, OracleTypes.CURSOR);
			//set values to IN params
			if(cs!=null)
				cs.setString(1, initChars );
			//call PL/SQL Procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params
			if(cs!=null)
				rs=(ResultSet)cs.getObject(2);
			//process the ResultSet
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4)+" "+rs.getInt(5));
				}//while
				if(flag==false)
					System.out.println("Records not found");
				else
					System.out.println("Records found & displayed");
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
			try{
				if(rs!=null)
				rs.close();
			}
		catch(SQLException se) {
			se.printStackTrace();
		}
			try {
				if(cs!=null) 
					cs.close();
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
