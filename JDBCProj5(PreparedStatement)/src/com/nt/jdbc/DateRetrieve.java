package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DateRetrieve {
  private static final String  SELECT_DATE_QUERY="SELECT PID,PNAME,DOB,DOJ,DOM FROM PERSON_TAB";
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		java.util.Date udob=null,udoj=null,udom=null;
		String dob=null,doj=null,dom=null;
		int no=0;
		String name=null;
		SimpleDateFormat sdf=null;
		try {
			//Load jdbc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establihs the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			//create JDBC PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(SELECT_DATE_QUERY);
			//execute query 
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ResultSet
			if(rs!=null) {
				while(rs.next()) {
					no=rs.getInt(1);
					name=rs.getString(2);
					sqdob=rs.getDate(3);
					sqdoj=rs.getDate(4);
					sqdom=rs.getDate(5);
					//convert java.sql.Date class objs to java.util.Date class object
				    udob=(java.util.Date)sqdob;
				    udom=(java.util.Date)sqdom;
				    udoj=(java.util.Date)sqdoj;
				    //convert java.util.Date class objs to String date values
				    sdf=new SimpleDateFormat("MMM-dd-yyyy");
				    dob=sdf.format(udob);
				    dom=sdf.format(udom);
				    doj=sdf.format(udoj);
				    System.out.println(no+"  "+name+"   "+dob+"  "+doj+"  "+dom);
				}//while
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
			//close jdbc objs
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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
			
		}//finally
	}///main
}//class
