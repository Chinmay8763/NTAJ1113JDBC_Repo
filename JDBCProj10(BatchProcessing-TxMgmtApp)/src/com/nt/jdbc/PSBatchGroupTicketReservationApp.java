package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*CREATE TABLE "SCOTT"."RAIL_RESERVATION" 
(	"TKTID" NUMBER(5,0) NOT NULL ENABLE, 
	"PSGNRNAME" VARCHAR2(20 BYTE), 
	"SOURCEPLACE" VARCHAR2(20 BYTE), 
	"DESTPLACE" VARCHAR2(20 BYTE), 
	"FARE" FLOAT(126), 
	 CONSTRAINT "RAIL_RESERVATION_PK" PRIMARY KEY ("TKTID");*/

/*CREATE SEQUENCE  "SCOTT"."TKTID"  MINVALUE 1
MAXVALUE 10000
INCREMENT BY 1 
START WITH 1 CACHE 20 NOORDER  NOCYCLE ;*/

public class PSBatchGroupTicketReservationApp {

	private static final String TICKET_RESERVATION="INSERT INTO RAIL_RESERVATION VALUES(TKTID.NEXTVAL,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String srcPlace=null,destPlace=null;
		float fare=0.0f;
		int count=0;
		Connection con=null;
		PreparedStatement ps=null;
		String psgnrName=null;
		int result[]=null;
		//read inputs
		try {
			//create Scanner
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Passenger's group count::");
				count=sc.nextInt();
				System.out.println("Enter start place of the journey::");
				srcPlace=sc.next();
				System.out.println("Enter dest place of the journey::");
				destPlace=sc.next();
				System.out.println("Enter Ticket Fare Amount::");
				fare=sc.nextFloat();			
			}//if
			
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create PreparedStatement object having precompiled SQL query
			if(con!=null)
				ps=con.prepareStatement(TICKET_RESERVATION);
			
			//add query param values to the batch by gathering group member details
			if(ps!=null && sc!=null) {
				for(int i=1;i<=count;++i) {
					System.out.println("Enter "+i+" passenger details");
					psgnrName=sc.next();
					//add query param values to the batch
					ps.setString(1, psgnrName);
					ps.setString(2, srcPlace);
					ps.setString(3, destPlace);
					ps.setFloat(4, fare);
					ps.addBatch();
				}//for
			}//if
			
			//execute the batch
			if(ps!=null)
				result=ps.executeBatch();
			
			//process the results
			if(result!=null) 
				System.out.println("Group Ticket reservation is done successfully");
			else
				System.out.println("Group Ticket Reservation Failed");
			
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
			//close the JDBC objects
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
		
	}//main(-)

}//class
