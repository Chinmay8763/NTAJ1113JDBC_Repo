package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIScrollFrameTest extends JFrame implements ActionListener{
	
	private static final String GET_ALL_EMPLOYEES="SELECT EMPNO,ENAME,JOB,SAL FROM EMP";
	
	private JLabel lno,lname,ljob,lsal;
	private JTextField tno,tname,tjob,tsal;
	private JButton bFirst,bNext,bLast,bPrevious;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public GUIScrollFrameTest() {
		System.out.println("GUIScrollFrameTest:: 0-param constructor");
		setTitle("GUI Scroll Frame");
		setSize(300,300);
		setBackground(Color.GRAY);
		setLayout(new FlowLayout());
		//add components
		lno=new JLabel("Employee number::");
		add(lno);
		tno=new JTextField(10);
		add(tno);
		
		lname=new JLabel("Employee name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		ljob=new JLabel("Employee JOB::");
		add(ljob);
		tjob=new JTextField(10);
		add(tjob);
		
		lsal=new JLabel("Employee Salary::");
		add(lsal);
		tsal=new JTextField(10);
		add(tsal);
		
		bFirst=new JButton("First");
		bFirst.addActionListener(this);
		add(bFirst);
		bNext=new JButton("Next");
		bNext.addActionListener(this);
		add(bNext);
		bLast=new JButton("Last");
		bLast.addActionListener(this);
		add(bLast);
		bPrevious=new JButton("Previous");
		bPrevious.addActionListener(this);
		add(bPrevious);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initializeJdbc();   //calling the helper method
		
		//disable Text-box editing
		tno.setEditable(false);
		tname.setEditable(false);
		tjob.setEditable(false);
		tsal.setEditable(false);
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowsClosing(WindowEvent e) {
				System.out
						.println("GUIScrollFrameTest.GUIScrollFrameTest().new WindowAdapter() {...}.windowsClosing()");
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
			}
		});
	}//GUIScrollFrameTest(-) constructor
	
	private void initializeJdbc() {
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//create JDBC PreparedStatemnet objects
			if(con!=null)
				ps=con.prepareStatement(GET_ALL_EMPLOYEES,
						                                              ResultSet.TYPE_SCROLL_SENSITIVE,
						                                              ResultSet.CONCUR_UPDATABLE);
			//create Scrollable ResultSet object
			if(ps!=null)
				rs=ps.executeQuery();
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
		
	}//initializeJdbc(-)

	public static void main(String[] args) {
		System.out.println("GUIScrollFrameTest.main()");
		new GUIScrollFrameTest();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("GUIScrollFrameTest.actionPerformed()");
		boolean flag=false;
		if(ae.getSource()==bFirst) {
			System.out.println("First Button is clicked");
			try {
				rs.first();
				flag=true;
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		else if(ae.getSource()==bNext) {
			System.out.println("Next Button is clicked");
			try {
				if(!rs.isLast()) {
				rs.next();
				flag=true;
			}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		else if(ae.getSource()==bPrevious) {
			System.out.println("Previous Button is clicked");
			try {
				if(!rs.isFirst()) {
				rs.previous();
				flag=true;
			}
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		else {
			try {
				rs.last();
				flag=true;
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			System.out.println("Last Button is clicked");
		}//else
		
		//write ResultSet record values to Text-boxes
		if(flag==true) {
			try {
			tno.setText(rs.getString(1));
			tname.setText(rs.getString(2));
			tjob.setText(rs.getString(3));
			tsal.setText(rs.getString(4));
		}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//if
		
	}//actionPerformed(-)

}//GUIScrollFrameTest(-)
