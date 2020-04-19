//SelectTest3.java(With Java Coding Standard)

package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*App to get Emp Details based on given dept. no
*version:2.0
*author:Team-Natraj
*Date:2020/02/27
*/

public class SelectTest3{
	public static void main(String args[]){
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		int dept1=0;
		int dept2=0;
		int dept3=0;
		boolean flag=false;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Dept1::");
				dept1=sc.nextInt();//gives 10
				System.out.println("Enter Dept2::");
				dept2=sc.nextInt();//gives 20
				System.out.println("Enter Dept3::");
				dept3=sc.nextInt();//gives 30
			}
			 //Frame Condition (10,20,30)
			String cond="("+dept1+","+dept2+","+dept3+")";
			   //gives(10,20,30)
             //register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 //Establish the connection
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");

            //create statement object
			if(con!=null)
			st=con.createStatement();
			//Frame the SQL Query
//select empno,ename,job,sal,hiredate,sal from emp where deptno in(10,20,30)order by deptno;
            String query="SELECT EMPNO,ENAME,JOB,SAL,HIREDATE FROM EMP WHERE DEPTNO IN"+cond+"ORDER BY DEPTNO";
			System.out.println(query);
			System.out.println("Displaying Results:");

			//send & execute SQL query in database software
			if(st!=null)
            rs=st.executeQuery(query);
			//process the ResultSet
			if(rs!=null){
            while(rs.next()){
				flag=true;
				System.out.println(+rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getString(5));
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