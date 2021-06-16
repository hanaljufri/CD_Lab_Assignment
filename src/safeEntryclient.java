
import java.rmi.Naming;			//Import the rmi naming - so you can lookup remote object
import java.rmi.RemoteException;	//Import the RemoteException class so you can catch it
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import java.net.MalformedURLException;	//Import the MalformedURLException class so you can catch it
import java.rmi.NotBoundException;	//Import the NotBoundException class so you can catch it

public class safeEntryclient {

	private safeEntry server;
	safeEntry c = (server);
    public static void main(String[] args) {

    	System.out.println("Hello World !");
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

    
    public safeEntryclient() {
    	System.out.println("Hello World!");
    	doConnect();
    	frame=new JFrame("Safe Entry");
	    JPanel main =new JPanel();
	    JPanel top =new JPanel();
	    JPanel cn =new JPanel();
	    JPanel bottom =new JPanel();
	    JPanel panel = new JPanel();
	    
	    
	    location = new JTextField();
	    nric=new JTextField();
	    name=new JTextField();
	    signin=new JButton("Sign In");
	    signin.setPreferredSize(new Dimension(200, 50));
	    signout=new JButton("Sign Out");
	    textarea=new JTextArea(5, 20);
	    textarea.setEditable(false);
	   
	    String[] locations = { "Bedok Mall", "Tampines One", "NEX", "Vivo City", "Mustafa Centre", "ION Orchard","Scape", "Bugis Junction" };        
	    JComboBox locationlist = new JComboBox(locations);
	    
	    main.setLayout(new BorderLayout(5,5));         
	    top.setLayout(new GridLayout(0,4)); 
	    panel.setLayout(new GridLayout(0,2));
	    cn.setLayout(new BorderLayout(5,5));
	    bottom.setLayout(new GridLayout(0,2));
	    
	    top.add(new JLabel("NAME:"));top.add(name);    
	    top.add(new JLabel("NRIC:"));top.add(nric);
	    
	    top.add(new JLabel("Location:"));top.add(locationlist);
        bottom.add(signin);
        bottom.add(signout);
        //cn.add(textarea);
   
	    main.add(top, BorderLayout.NORTH); 
	    main.add(bottom, BorderLayout.SOUTH);
	    //main.add(cn,BorderLayout.CENTER);
	    main.setBorder(new EmptyBorder(10, 10, 10, 10) );
	    
	    //Events
	    signin.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){ 
	    	  String namestr = name.getText();
	    	  String nricstr = nric.getText();
				Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}",Pattern.CASE_INSENSITIVE);
			    Matcher matcher = nricpattern.matcher(nricstr);
			    boolean matchFound = matcher.find();
			    String locationstr = (String) locationlist.getSelectedItem();
			    String status = "sign in";
			    
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,"Successful Sign Out!"); 
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
			    String status = "sign out";
			    
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					  JOptionPane.showMessageDialog(null,"Successful Sign Out!");  
				  } 
	      }  });
	    
	    frame.setContentPane(main);
	    frame.setSize(400,250);
	    frame.setVisible(true);  
	  }
	  JTextField name, nric, location, testfield;
	  JTextArea textarea;
	  JButton signin, signout;
	  JFrame frame;

}
