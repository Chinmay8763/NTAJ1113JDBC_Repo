package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*CREATE OR REPLACE FUNCTION FX_GETEMPDETAILSBYNO 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, DESG OUT VARCHAR2 
) RETURN NUMBER AS 
  SALARY NUMBER(10,2);
BEGIN
  SELECT ENAME,JOB,SAL INTO NAME,DESG,SALARY FROM EMP WHERE EMPNO=NO;
  RETURN SALARY;
END FX_GETEMPDETAILSBYNO;*/

public class CsFxGetEmpDetails {

	private static final String CALL_FUNCTION="{?=call FX_GETEMPDETAILSBYNO(?,?,?)}";
	
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
				cs=con.prepareCall(CALL_FUNCTION);
			//register OUT params,return params with JDBC types
			if(cs!=null) {
				cs.registerOutParameter(1, Types.FLOAT);     //return param
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.VARCHAR);
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(2, no);
			//execute/call PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params,return params
			if(cs!=null) {
				System.out.println("Employee Salary::"+cs.getFloat(1));//return param
				System.out.println("Employee Name::"+cs.getString(3));
				System.out.println("Employee Desg::"+cs.getString(4)); 
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
