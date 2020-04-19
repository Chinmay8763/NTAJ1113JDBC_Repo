//SelectTest5.java(With Java Coding Standard)

package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*App to get Emp Details based on given designations
*version:2.0
*author:Team-Natraj
*Date:2020/02/27
*/

public class SelectTest5{
	public static void main(String args[]){
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String desg1=null;
		String desg2=null;
		String desg3=null;
		boolean flag=false;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Desg1::");
				desg1=sc.next().toUpperCase();//gives CLERK
				System.out.println("Enter Desg2::");
				desg2=sc.next().toUpperCase();//gives MANAGER
				System.out.println("Enter Desg3::");
				desg3=sc.next().toUpperCase();//gives SALESMAN
			}
			 //Frame Condition ('CLERK','MANAGER','SALESMAN')
			String cond="('"+desg1+"','"+desg2+"','"+desg3+"')";
			   //gives('CLERK','MANAGER','SALESMAN')
             //register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 //Establish the connection
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");

            //create statement object
			if(con!=null)
			st=con.createStatement();
			//Frame the SQL Query
//select empno,ename,job,sal,hiredate,sal from emp where deptno in('CLERK','MANAGER','SALESMAN')order by deptno;
            String query="SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE JOB IN"+cond+"ORDER BY JOB";
			System.out.println(query);
			System.out.println("Displaying Results:");

			//send & execute SQL query in database software
			if(st!=null)
            rs=st.executeQuery(query);
			//process the ResultSet
			if(rs!=null){
            while(rs.next()){
				flag=true;
				System.out.println(+rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getInt(4));
			}//while
			if(flag==false)
				System.out.println("No Records Found");
		}//if
		}//try
		catch(SQLException se){//known Exception
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){//known Exception
			cnf.printStackTrace();
		}
		catch(Exception e){//unknown Exception
			e.printStackTrace();
		}
		finally{
			//close objects
			try{
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(st!=null)
					st.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(con!=null)
					con.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(sc!=null)
					sc.close();
			}
			catch(Exception se){
				se.printStackTrace();
			}
		}//finally
}//main
}//class