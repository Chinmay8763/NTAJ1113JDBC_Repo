package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*CREATE TABLE "SCOTT"."JDBC_BANK_ACCOUNT" 
(	"ACNO" NUMBER(5,0) NOT NULL ENABLE, 
	"HOLDERNAME" VARCHAR2(20 BYTE), 
	"BALANCE" FLOAT(126), 
	 CONSTRAINT "JDBC_BANK_ACCOUNT_PK" PRIMARY KEY ("ACNO");*/

/*ACNO  HOLDERNAME  BALANCE
1001	     Chinmaya	       90000
1002	        Ashok	           80000*/

public class TxMgmtMoneyTransfer {

	public static void main(String[] args) {
		int srcAcno=0,destAcno=0;
		float amount=0.0f;
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		int result[]=null;
		boolean flag=false;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Source account number::");
				srcAcno=sc.nextInt();
				
				System.out.println("Enter Destination account number::");
				destAcno=sc.nextInt();
				
				System.out.println("Enter Amount to transfer::");
				amount=sc.nextFloat();
			}//if
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//Begin Tx
			if(con!=null)
				con.setAutoCommit(false);
			//create JDBC statement object
			if(con!=null)
				st=con.createStatement();
			
			//execute the Queries to the batch
			if(st!=null) {
				st.addBatch("UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE-"+amount+"WHERE ACNO="+srcAcno);
				st.addBatch("UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE+"+amount+"WHERE ACNO="+destAcno);
			}
			
			//execute batch
			if(st!=null)
				result=st.executeBatch();
			
			//perform Transaction Management
			for(int i=0;i<result.length;++i) {
				if(result[i]==0)
					flag=true;
				    break;
			}
		}//try
		catch(SQLException se) {
			flag=true;
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			flag=true;
			cnf.printStackTrace();
		}
		catch(Exception e) {
			flag=true;
			e.printStackTrace();
		}
		finally {
			//Tx Mgmt
			try {
				if(con!=null) {
					if(flag) {
						con.rollback();
						System.out.println("Transaction rolledback------>Money not transfered");
					}//if
					else {
						con.commit();
						System.out.println("Transaction committed------>Money Transafered");
					}
				}//if
			}//try
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
		
	}//main(-)

}//class
