package com.nt.basics;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DemoApp {

	public static void main(String[] args) {
		InputStream is=null;
		Properties props=null;
		try {
			//load properties
			is=new FileInputStream("src/com/nt/commons/info.properties");
			//Load properties file info into Properties class object
			props=new Properties();
			props.load(is);
			
			System.out.println("All Elements::"+props);
			System.out.println("person.name key value::"+props.getProperty("person.name"));
			
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}//main(-)

}//class
