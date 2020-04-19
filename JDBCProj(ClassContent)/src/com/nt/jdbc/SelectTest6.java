package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest6 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String city1=null;
		String city2=null;
		String city3=null;
		boolean flag=false;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter City1::");
				city1=sc.next().toUpperCase();//gives USA
				System.out.println("Enter City2::");
				city2=sc.next().toUpperCase();//gives RSA
				System.out.println("Enter City3::");
				city3=sc.next().toUpperCase();//gives CANADA
			}
			 //Frame Condition ('USA','RSA','CANADA')
			String cond="('"+city1+"','"+city2+"','"+city3+"')";
			   //gives('USA','RSA','CANADA')
             //register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 //Establish the connection
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");

            //create statement object
			if(con!=null)
			st=con.createStatement();
			//Frame the SQL Query
//select sid,sname,avgmark,city from student where city in('USA','RSA','CANADA')order by city;
            String query="SELECT SID,SNAME,AVGMARK,CITY FROM STUDENT WHERE CITY IN ('USA','RSA','CANADA')ORDER BY CITY";
			System.out.println(query);
			System.out.println("Displaying Results:");

			//send & execute SQL query in database software
			if(st!=null)
            rs=st.executeQuery(query);
			//process the ResultSet
			if(rs!=null){
            while(rs.next()){
				flag=true;
				System.out.println(+rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getString(4));
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
}//clasS