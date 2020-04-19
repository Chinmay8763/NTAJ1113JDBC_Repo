/*27th March 2020  SWING GUI APPLICATION*/

package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIStudentRegistrationApp extends JFrame implements ActionListener,WindowListener {
  
	private static final String STUDENT_INSERT_QUERY="INSERT INTO STUDENT_TAB VALUES(SNO_SEQ.NEXTVAL,?,?,?)";
	
	private JLabel lsname,lsadd,lavg,lresult;
	private JButton register;
	private JTextField tsname,tsadd,tavg;
	private Connection con;
	private PreparedStatement ps;
	
	//constructor
	public GUIStudentRegistrationApp() {
		
		System.out.println("GUIStudentRegistrationApp:: 0-param constructor");
	    
		//frame window settings
		setTitle("Student Registration");
		setSize(200,200);
		setBackground(Color.GRAY);
		setLayout(new FlowLayout());
		
		//add components to the frame window
		lsname=new JLabel("Student's Name::");
		add(lsname);
		tsname=new JTextField(10);
		add(tsname);
		
		lsadd=new JLabel("Student's Address::");
		add(lsadd);
		tsadd=new JTextField(10);
		add(tsadd);
		
		lavg=new JLabel("Student's Average::");
		add(lavg);
		tavg=new JTextField(10);
		add(tavg);
		
		register=new JButton("Register Here");
		register.addActionListener(this);  //we're linking current invoking class object as ActionListener to button comp. to handle ActionEvent
		add(register);
		
		lresult=new JLabel();
		add(lresult);
		
		setVisible(true);
		
		//terminate application when window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create JDBC objects
		initializeJdbc();
		
		//Add WindowListener to frame
		this.addWindowListener(this);  //1st this(invoking obj) represent FrameWindow,2nd this(arg obj) represent WindowListener
		
	}
	
	private void initializeJdbc() {  //helper methods that will be used in the class that will be taken as private methods
		System.out.println("GUIStudentRegistrationApp.initializeJdbc()");
		try {
			//Load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create JDBC PreparedStatement obj
			ps=con.prepareStatement(STUDENT_INSERT_QUERY);
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
	}//initializeJdbc
	
	@Override
	public void actionPerformed(ActionEvent ae) {
	System.out.println("GUIStudentRegistrationApp.actionPerformed()");
	String name,addrs=null;
	float avg=0.0f;
	int count=0;
	try {
		//read text values from test boxes
		name=tsname.getText();
		addrs=tsadd.getText();
		avg=Float.parseFloat(tavg.getText());
		//set above values as query param values
		ps.setString(1, name);
		ps.setString(2,addrs);
		ps.setFloat(3, avg);
		//execute the query
		count=ps.executeUpdate();
		//process the result
		if (count==0) {
			lresult.setForeground(Color.RED);
			lresult.setText("Student Registration Failed");
		}
		else {
			lresult.setForeground(Color.GREEN);
			lresult.setText("Student Registration Successful!!");
		}
	}//try
	catch(SQLException se) {
		se.printStackTrace();
		lresult.setForeground(Color.RED);
		lresult.setText("Student Registration Failed--DB Problem::"+se.getMessage());
	}
	catch(Exception e) {
		e.printStackTrace();
		lresult.setForeground(Color.RED);
		lresult.setText("Student Registration Failed--Unknown Problem");
	}
		
	}//actionPerformed
	
	//main(-) method
public static void main(String[] args) {
		System.out.println("Start of main(-) method"); 
		//Object not having referenced variable called Anonymous object
		new GUIStudentRegistrationApp();//Anonymous object to execute the constructor
		System.out.println("End of main(-) method");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIStudentRegistrationApp.windowClosing()");
		try {
			//close JDBC objects
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
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("GUIStudentRegistrationApp.windowClosed()");
	}

	@Override
	public void windowIconified(WindowEvent e) {           //maximize windows
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {    //minimize windows
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {    //window in focus
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {  // window looses focus
		// TODO Auto-generated method stub
		
	}


}
