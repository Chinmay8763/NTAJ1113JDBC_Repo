package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class BLOBRetrieveIOUtils {

	private static final String BLOB_RETRIEVE_QUERY="SELECT ARTISTID,ARTISTNAME,ARTISTADDRS,PHOTO FROM CINEMA_ARTIST WHERE ARTISTID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int artistId=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String artistName=null,artistAddrs=null;
		InputStream is=null;
		OutputStream os=null;
		byte[] buffer=new byte[4096];
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter ArtistId::");
				artistId=sc.nextInt();
			}//if
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create PreparedStatement
			if(con!=null)
				ps=con.prepareCall(BLOB_RETRIEVE_QUERY);
			//SET QUERY PARAM VALUE
			if(ps!=null)
				ps.setInt(1, artistId);
			//execute the query
			if(ps!=null)
				rs=ps.executeQuery();
			
			//process the ResultSet object
			if(rs.next()) {
				//get record content
				artistId=rs.getInt(1);
				artistName=rs.getString(2);
				artistAddrs=rs.getString(3);
				is=rs.getBinaryStream(4);
				//create OutputStream point to empty destination file
				os=new FileOutputStream("deepeka.jpg");
				
				IOUtils.copy(is, os);
				
				System.out.println("BLOB value retrieved");
				System.out.println(artistId+" "+artistName+" "+artistAddrs);
			}//if
			else {
				System.out.println("Record not found");
			}
			
		}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
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
			try {
				if(is!=null)
					is.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			try {
				if(os!=null)
					os.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
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
