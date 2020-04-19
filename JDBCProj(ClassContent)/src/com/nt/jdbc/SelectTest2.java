//Write a jdbc app to get student details based on the start,end ranges of student numbers
//Using DEPT Table of SCOTT user

package com.nt.jdbc;

import java.sql.*;//jdbc api
import java.util.*;

public class SelectTest2{
	public static void main(String args[])throws Exception{

  //read inputs
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter Start range of dept no:::");
	int start=sc.nextInt(); //gives 10

	System.out.println("Enter End range of dept no:::");
	int end=sc.nextInt();  //gives 40

		//Load jdbc driver class
	Class.forName("oracle.jdbc.driver.OracleDriver");//optional

	//Establish the Connection
	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");

	//Create JDBC Statement object
	Statement st=con.createStatement();

	//Prepare query
  String query="SELECT * FROM DEPT WHERE DEPTNO>="+start+"AND DEPTNO<="+end+"";
	System.out.println(query);

	//send & execute SQL Query in DB s/w
  ResultSet rs=st.executeQuery(query);

	//process the ResultSet object
	while(rs.next()!=false){
		System.out.println(rs.getInt("deptno")+" "+rs.getString("dname")+" "+rs.getString("loc"));
	}
	//close jdbc objs
	rs.close();
	st.close();
	con.close();

	}//main
}//class