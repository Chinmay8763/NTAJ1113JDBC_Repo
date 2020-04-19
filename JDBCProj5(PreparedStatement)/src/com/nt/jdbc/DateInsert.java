package com.nt.jdbc;

import java.sql.Connection;


import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/*CREATE TABLE "SYSTEM"."PERSON_DATE_TAB" 
(	"PID" NUMBER(10,0) NOT NULL ENABLE, 
	"PNAME" VARCHAR2(20 BYTE), 
	"DOB" DATE,  
	"DOM" DATE,
	"DOJ" DATE, 
	 CONSTRAINT "PERSON_DATE_TAB_PK" PRIMARY KEY ("PID"))
*/

public class DateInsert {
	private static final String DATE_INSERT_QUERY= "INSERT INTO PERSON_TAB VALUES(?,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		String name=null,dob=null,dom=null,doj=null;
		SimpleDateFormat sdf1=null,sdf2=null;
		java.util.Date udom=null,udob=null;
		java.sql.Date sqdob=null,sqdom=null,sqdoj=null;
		long ms=0;
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter no:");
				no=sc.nextInt();
				System.out.println("Enter Name:");
				name=sc.next();
				System.out.println("Enter DOB(dd-MM-yyyy):");       
				dob=sc.next();
				System.out.println("Enter DOM(dd-MM-yyyy):");
				dom=sc.next();
				System.out.println("Enter DOJ(yyyy-MM-dd):");    
				doj=sc.next();
			}//if
			//Convert String date values to java.sql.Date class objects
			//for DOB
			sdf1=new SimpleDateFormat("dd-MM-yyyy");
			if(sdf1!=null)
				udob=sdf1.parse(dob);    //gives java.util.Date object
			if(udob!=null)
				ms=udob.getTime();
			sqdob=new java.sql.Date(ms);  //gives java.sql.Date class object
			//for DOM
			sdf2=new SimpleDateFormat("dd-MM-yyyy");
			if(sdf2!=null)
				udom=sdf2.parse(dom);    //gives java.util.Date object
			if(udom!=null)
				ms=udom.getTime();
			sqdom= new java.sql.Date(ms);//gives java.sql.Date class object
			//for DOJ
				sqdoj= java.sql.Date.valueOf(doj);//gives java.sql.Date class object
			//Register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//Create PreparedStatement object
			if(con!=null)
			ps=con.prepareStatement(DATE_INSERT_QUERY);
			//set value Query params
			if(ps!=null) {
				ps.setInt(1, no);
				ps.setString(2, name);
				ps.setDate(3, sqdob);
				ps.setDate(4, sqdom);
				ps.setDate(5, sqdoj);
			}//if
			//execute the Query
			if(ps!=null)
				result=ps.executeUpdate();
			//process the result
			if(result==0)
				System.out.println("Records are not inserted");
			else
				System.out.println("Records are inserted");
		}//try
		catch(SQLException se) {   //To handle known Exception
			se.printStackTrace();
			System.out.println("Record Insertion Failed");
		}
		catch(ClassNotFoundException cnf) {   //To handle known Exception
			cnf.printStackTrace();
			System.out.println("Records Insertion Failed");
		}
		catch(Exception e) {  //To handle Unknown Exception
			e.printStackTrace();
			System.out.println("Record Insertion Failed");
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
			catch(Exception e) {
				e.printStackTrace();
	        }
		}//finally
    }//main
}//class