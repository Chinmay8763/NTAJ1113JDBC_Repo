package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteTest1 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		int rng1=0;
		int rng2=0;
		int result=0;
		boolean flag=false;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter 1st Range::");
				rng1=sc.nextInt();//gives 1st range
				System.out.println("Enter 2nd Range::");
				rng2=sc.nextInt();//gives 2nd range
			}
             //register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 //Establish the connection
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");

            //create statement object
			if(con!=null)
			st=con.createStatement();
			//Frame the SQL Query
			//DELETE FROM STUDENT1 WHERE SID BETWEEN"+rng1+"AND"+rng2
			            String query="DELETE FROM STUDENT1 WHERE SID BETWEEN "+rng1+"AND "+rng2;
						System.out.println(query);
						System.out.println("Displaying Results:");
			//send & execute SQL query in database software
			if(st!=null)
            result=st.executeUpdate("DELETE FROM STUDENT1 WHERE SID BETWEEN "+rng1+" AND "+rng2);
			//process the Result
			if(result==0)
				System.out.println("No records found for deletion");
			else
				System.out.println(result+"no of records are deleted");
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

