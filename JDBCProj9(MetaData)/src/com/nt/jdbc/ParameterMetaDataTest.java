package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ParameterMetaDataTest {

	private static final String GET_ALL_EMPLOYEES_QUERY="SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE ENAME IN(?,?,?)";
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		ParameterMetaData pmd=null;
		int paramCnt=0;
		try {
			//load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create Statement object
			if(con!=null)
				ps=con.prepareStatement(GET_ALL_EMPLOYEES_QUERY);
            //create ParammeterMataData object
			if(ps!=null)
				pmd=ps.getParameterMetaData();
			
			System.out.println("pmd class name::"+pmd.getClass());
			//get Parameters count & details
		    if(pmd!=null) {
		    	paramCnt=pmd.getParameterCount();
		    	
		    	for(int i=1;i<=paramCnt;++i) {
		    		System.out.println(i+" param mode::"+pmd.getParameterMode(i));
		    		System.out.println(i+" param type::"+pmd.getParameterClassName(i));
		    		System.out.println(i+" param is signed"+pmd.isSigned(i));
		    	}//for
		    	
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
		
	}//main

}//class
