package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*CREATE OR REPLACE PROCEDURE P_GETEMPDETAILSBYNO 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, DESG OUT VARCHAR2 
, SALARY OUT FLOAT
) AS 
BEGIN
  
  select ename,job,sal into name,desg,salary from emp where empno=no;
  
END P_GETEMPDETAILSBYNO;*/

public class CsGetEmpDetailsTest {

	private static final String CALL_PROCEDURE="{CALL P_GETEMPDETAILSBYNO(?,?,?,?)}";
	
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Employee No::");
				no=sc.nextInt();
			}
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//Create CallableStatement object
			if(con!=null)
				cs=con.prepareCall(CALL_PROCEDURE);
			//register OUT params with JDBC types
			if(cs!=null) {
				cs.registerOutParameter(2, Types.VARCHAR);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.FLOAT);
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(1, no);
			//execute/call PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params
			if(cs!=null) {
				System.out.println("Employee Name::"+cs.getString(2));
				System.out.println("Employee Desg::"+cs.getString(3));
				System.out.println("Employee Salary::"+cs.getFloat(4));
			}
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			if(se.getErrorCode()==1403)
				System.out.println("Employee not found");
			else
				System.out.println("Unknown DB problem");
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
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
