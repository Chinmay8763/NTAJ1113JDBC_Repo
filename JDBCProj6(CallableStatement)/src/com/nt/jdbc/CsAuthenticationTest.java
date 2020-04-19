package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*UNAME      PWD
-----------    ---------
RAJA	          RANI
KING	          KINGDOM
JANI	          BEGAM
*/


/*CREATE OR REPLACE PROCEDURE P_AUTHENTICATION 
(
  USERNAME IN VARCHAR2 
, PASS IN VARCHAR2 
, RESULT OUT VARCHAR2 
) AS 
cnt number(1);
BEGIN

  select count(*) into cnt from userlist where uname=username and pwd=pass;
  if(cnt<>0)then
    result:='VALID CREDENTIALS';
  else
    result:='INVALID CREDENTIALS';
  end if;
END P_AUTHENTICATION;*/

public class CsAuthenticationTest {

		private static final String CALL_PROCEDURE="{CALL P_AUTHENTICATION(?,?,?)}";
		
		public static void main(String[] args) {
			Scanner sc=null;
			String user=null,pass=null;
			Connection con=null;
			CallableStatement cs=null;
			try {
				//read inputs
				sc=new Scanner(System.in);
				if(sc!=null) {
					System.out.println("Enter Username::");
					user=sc.nextLine();
					System.out.println("Enter Password::");
					pass=sc.nextLine();
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
					cs.registerOutParameter(3, Types.VARCHAR);
				}
				//set values to IN params
				if(cs!=null)
					cs.setString(1, user);
				    cs.setString(2,pass);
				//execute/call PL/SQL procedure
				if(cs!=null)
					cs.execute();
				//gather results from OUT params
				if(cs!=null) {
					System.out.println("Result is::"+cs.getString(3));
				}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
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
