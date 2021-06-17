
import java.rmi.Naming;			//Import the rmi naming - so you can lookup remote object
import java.rmi.RemoteException;	//Import the RemoteException class so you can catch it
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;	//Import the MalformedURLException class so you can catch it
import java.rmi.NotBoundException;	//Import the NotBoundException class so you can catch it

public class safeEntryclient {

	private safeEntry server;
	safeEntry c = (server);
    public static void main(String[] args) {

    	System.out.println("Hello World!");
    	new safeEntryclient();
    }
    
    public void doConnect(){
      try {
		server= (safeEntry)Naming.lookup("rmi://localhost/SafeEntryService");
		System.out.print("connected");
	    
  }
	catch (MalformedURLException murle) {
            System.out.println();
            System.out.println("MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
    }

    
    // main page
    public safeEntryclient() {
    	System.out.println("Hello World!");
    	doConnect();

    	frame=new JFrame("Safe Entry");
	    JPanel main =new JPanel();
	    JPanel welcomepanel = new JPanel(); //welcome info
	    JPanel signbuttons =new JPanel();
	    JPanel recordbuttons =new JPanel();//records buttons
	    JPanel buttons =new JPanel();
	    
	    //welcome panel
	    JTextArea welcome = new JTextArea("Welcome to SafeEntry!");
	    JTextArea instructions = new JTextArea("Instructions (to add)");
	    welcomepanel.setLayout(new BorderLayout(5,5));
	    welcomepanel.add(welcome, BorderLayout.NORTH);
	    welcomepanel.add(instructions, BorderLayout.SOUTH);
	    
	    //Sign up buttons
	    indiv=new JButton("Individual Safe Entry");
	    group=new JButton("Group Safe Entry");
	    indiv.setPreferredSize(new Dimension(200,50));
	    group.setPreferredSize(new Dimension(200,50));
	    signbuttons.setLayout(new GridLayout(0,2));
        signbuttons.add(indiv, BorderLayout.EAST);
        signbuttons.add(group, BorderLayout.WEST);	    
	    
	    myrecords=new JButton("My Records");
	    allrecords=new JButton("All Records (MOH Only)");
	    myrecords.setPreferredSize(new Dimension(200,50));
	    allrecords.setPreferredSize(new Dimension(200,50));
	    recordbuttons.setLayout(new GridLayout(0,2));
        recordbuttons.add(myrecords, BorderLayout.EAST);
        recordbuttons.add(allrecords, BorderLayout.WEST);	
        
        buttons.add(signbuttons, BorderLayout.NORTH);
        buttons.add(recordbuttons, BorderLayout.SOUTH);
	    
        //main panel
	    main.setLayout(new BorderLayout(5,5));         
	    main.add(welcomepanel, BorderLayout.NORTH); 
	    main.add(buttons);
	    main.setBorder(new EmptyBorder(10, 10, 10, 10) );
	/*    
	    //Events
	     */
	    
	    indiv.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  indiv_safeEntry();
		      }
		      });
	    
	    group.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  group_safeEntry();
		      }
		      });
	    
	    myrecords.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  openmyrecords();
		      }
		      });
	    
	    allrecords.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  openallrecords();
		      }
		      });
	    
	    frame.setContentPane(main);
	    frame.setSize(400,220);
	    frame.setVisible(true);  
	  }
	  JTextField name, nric, location;
	  JTextArea textarea;
	  JButton signin, signout, myrecords, allrecords, indiv, group;
	  JFrame frame;
	    
	  //individual sign-in & sign-out page
	  public void indiv_safeEntry() {
	    	frame=new JFrame("Individual Safe Entry");
		    JPanel top =new JPanel(); //welcome info
		    JPanel main =new JPanel();
		    JPanel buttons = new JPanel();
		    
		    signin=new JButton("Sign In");
		    signout=new JButton("Sign Out");
		    signin.setPreferredSize(new Dimension(200,50));
		    signout.setPreferredSize(new Dimension(200,50));
		    location = new JTextField();
		    nric=new JTextField();
		    name=new JTextField();
		    String[] locations = { "Bedok Mall", "Tampines One", "NEX", "Vivo City", "Mustafa Centre", "ION Orchard","Scape", "Bugis Junction" };        
		    JComboBox locationlist = new JComboBox(locations);
		    
		    top.setLayout(new GridLayout(0,4)); 
		    top.add(new JLabel("NAME:"));top.add(name);    
		    top.add(new JLabel("NRIC:"));top.add(nric);
		    top.add(new JLabel("Location:"));top.add(locationlist);
		    
		    buttons.add(signin, BorderLayout.EAST);
		    buttons.add(signout, BorderLayout.WEST);
		    
		    main.add(top, BorderLayout.NORTH); 
		    main.add(buttons);
		    
		    //Events
		    signin.addActionListener(new ActionListener(){
			      public void actionPerformed(ActionEvent e){ 
			    	  
			    	  String namestr = name.getText();
			    	  String nricstr = nric.getText();
						Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}",Pattern.CASE_INSENSITIVE);
					    Matcher matcher = nricpattern.matcher(nricstr);
					    boolean matchFound = matcher.find();
					    String locationstr = (String) locationlist.getSelectedItem();
					    String status = "Sign in";
					    
						if (namestr.contentEquals("")) {
							JOptionPane.showMessageDialog(null,"Please enter name");
						  }
						  else if (nricstr.contentEquals("")) {
							JOptionPane.showMessageDialog(null,"Please enter nric");
						  }
						  else if (!matchFound) {
							  JOptionPane.showMessageDialog(null,"Please enter a valid nric");
						  }
						  else {
							try {
								server.getinfo(namestr, nricstr, locationstr, status);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null,"Successful Sign In!"); 
						  } 
						}});
			signout.addActionListener(new ActionListener(){
			      public void actionPerformed(ActionEvent e){
			    	  String namestr = name.getText();
			    	  String nricstr = nric.getText();
						Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}",Pattern.CASE_INSENSITIVE);
					    Matcher matcher = nricpattern.matcher(nricstr);
					    boolean matchFound = matcher.find();
					    String locationstr = (String) locationlist.getSelectedItem();
					    String status = "Sign out";
					    
						if (namestr.contentEquals("")) {
							JOptionPane.showMessageDialog(null,"Please enter name");
						  }
						  else if (nricstr.contentEquals("")) {
							JOptionPane.showMessageDialog(null,"Please enter nric");
						  }
						  else if (!matchFound) {
							  JOptionPane.showMessageDialog(null,"Please enter a valid nric");
						  }
						  else {
							 try {
								server.getinfo(namestr, nricstr, locationstr, status);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
							  JOptionPane.showMessageDialog(null,"Successful Sign Out!");  
						  } 
						 
			      }  });
		    
		    frame.setContentPane(main);
		    frame.setSize(400,220);
		    frame.setVisible(true);  
	  }
	    
	  //group sign-in & sign-out page
	  public void group_safeEntry() {
		  frame=new JFrame("Group Safe Entry");
		  JPanel main =new JPanel();
		    
		  
		  frame.setContentPane(main);
		  frame.setVisible(true);  
		    
	  }
	      
	  //allrecords page
	  public void openallrecords() {
		  
	  }
	      
	  //myrecord page
	  public void openmyrecords() {
		  
	  }
}
