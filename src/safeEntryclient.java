
import java.rmi.Naming; //Import the rmi naming - so you can lookup remote object
import java.rmi.RemoteException; //Import the RemoteException class so you can catch it
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
import java.net.MalformedURLException; //Import the MalformedURLException class so you can catch it
import java.rmi.NotBoundException; //Import the NotBoundException class so you can catch it

public class safeEntryclient {

	private safeEntry server;
	safeEntry c = (server);

	public static void main(String[] args) {

		System.out.println("Hello World!");
		new safeEntryclient();
	}

	public void doConnect() {
		try {
			server = (safeEntry) Naming.lookup("rmi://localhost/SafeEntryService");
			System.out.print("connected");

		} catch (MalformedURLException murle) {
			System.out.println();
			System.out.println("MalformedURLException");
			System.out.println(murle);
		} catch (RemoteException re) {
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(re);
		} catch (NotBoundException nbe) {
			System.out.println();
			System.out.println("NotBoundException");
			System.out.println(nbe);
		} catch (java.lang.ArithmeticException ae) {
			System.out.println();
			System.out.println("java.lang.ArithmeticException");
			System.out.println(ae);
		}
	}

	// main page
	public safeEntryclient() {
		System.out.println("Hello World!");
		doConnect();

		frame = new JFrame("Safe Entry");
		JPanel main = new JPanel();
		JPanel welcomepanel = new JPanel(); // welcome info
		JPanel signbuttons = new JPanel();
		JPanel recordbuttons = new JPanel();// records buttons
		JPanel buttons = new JPanel();

		// welcome panel
		JLabel welcome = new JLabel("Welcome to SafeEntry!");
		welcomepanel.setLayout(new BorderLayout(5, 5));
		welcomepanel.add(welcome, BorderLayout.NORTH);
		
		

		// Sign up buttons
		indiv = new JButton("Individual Safe Entry");
		group = new JButton("Group Safe Entry");
		indiv.setPreferredSize(new Dimension(200, 50));
		group.setPreferredSize(new Dimension(200, 50));
		signbuttons.setLayout(new GridLayout(0, 2));
		signbuttons.add(indiv, BorderLayout.EAST);
		signbuttons.add(group, BorderLayout.WEST);

		myrecords = new JButton("My Records");
		allrecords = new JButton("All Records (MOH Only)");
		myrecords.setPreferredSize(new Dimension(200, 50));
		allrecords.setPreferredSize(new Dimension(200, 50));
		recordbuttons.setLayout(new GridLayout(0, 2));
		recordbuttons.add(myrecords, BorderLayout.EAST);
		recordbuttons.add(allrecords, BorderLayout.WEST);

		buttons.add(signbuttons, BorderLayout.NORTH);
		buttons.add(recordbuttons, BorderLayout.SOUTH);

		// main panel
		main.setLayout(new BorderLayout(5, 5));
		main.add(welcomepanel, BorderLayout.NORTH);
		main.add(buttons);
		main.setBorder(new EmptyBorder(10, 10, 10, 10));
		/*
		 * //Events
		 */

		indiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indiv_safeEntry();
			}
		});

		group.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				group_safeEntry();
			}
		});

		myrecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openmyrecords();
			}
		});

		allrecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openallrecords();
			}
		});

		frame.setContentPane(main);
		frame.setSize(400, 220);
		frame.setVisible(true);
	}

	JTextField name, nric, location, fromdate, fromtime, todate, totime;
	JTextArea textarea;
	JButton checkin, checkout, myrecords, allrecords, indiv, group, check, notify;
	JFrame frame;

	// individual sign-in & sign-out page
	public void indiv_safeEntry() {
		frame = new JFrame("Individual Safe Entry");
		JPanel top = new JPanel(); // welcome info
		JPanel main = new JPanel();
		JPanel buttons = new JPanel();

		checkin = new JButton("Check In");
		checkout = new JButton("Check Out");
		checkin.setPreferredSize(new Dimension(200, 50));
		checkout.setPreferredSize(new Dimension(200, 50));
		location = new JTextField();
		nric = new JTextField();
		name = new JTextField();
		String[] locations = { "Bedok Mall", "Tampines One", "NEX", "Vivo City", "Mustafa Centre", "ION Orchard",
				"Scape", "Bugis Junction" };
		JComboBox locationlist = new JComboBox(locations);

		top.setLayout(new GridLayout(0, 4));
		top.add(new JLabel("NAME:"));
		top.add(name);
		top.add(new JLabel("NRIC:"));
		top.add(nric);
		top.add(new JLabel("Location:"));
		top.add(locationlist);

		buttons.add(checkin, BorderLayout.EAST);
		buttons.add(checkout, BorderLayout.WEST);

		main.add(top, BorderLayout.NORTH);
		main.add(buttons);

		// Events
		checkin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String namestr = name.getText();
				String nricstr = nric.getText();
				Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}", Pattern.CASE_INSENSITIVE);
				Matcher matcher = nricpattern.matcher(nricstr);
				boolean matchFound = matcher.find();
				String locationstr = (String) locationlist.getSelectedItem();
				String status = "Check in";

				if (namestr.contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter name");
				} else if (nricstr.contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter nric");
				} else if (!matchFound) {
					JOptionPane.showMessageDialog(null, "Please enter a valid nric");
				} else {
					try {
						server.getinfo(namestr, nricstr, locationstr, status);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Successful Sign In!");
				}
			}
		});
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String namestr = name.getText();
				String nricstr = nric.getText();
				Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}", Pattern.CASE_INSENSITIVE);
				Matcher matcher = nricpattern.matcher(nricstr);
				boolean matchFound = matcher.find();
				String locationstr = (String) locationlist.getSelectedItem();
				String status = "Check out";

				if (namestr.contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter name");
				} else if (nricstr.contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter nric");
				} else if (!matchFound) {
					JOptionPane.showMessageDialog(null, "Please enter a valid nric");
				} else {
					try {
						server.getinfo(namestr, nricstr, locationstr, status);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Successful Sign Out!");
				}

			}
		});

		frame.setContentPane(main);
		frame.setSize(400, 220);
		frame.setVisible(true);
	}

	// group sign-in & sign-out page
	public void group_safeEntry() {

		frame = new JFrame("Group Safe Entry");
		JPanel main = new JPanel();
		JPanel top = new JPanel(); // welcome info
		JPanel buttons = new JPanel();

		checkin = new JButton("Check In");
		checkout = new JButton("Check Out");
		checkin.setPreferredSize(new Dimension(200, 50));
		checkout.setPreferredSize(new Dimension(200, 50));

		JTextField nric1 = new JTextField();
		JTextField name1 = new JTextField();
		JTextField nric2 = new JTextField();
		JTextField name2 = new JTextField();
		JTextField nric3 = new JTextField();
		JTextField name3 = new JTextField();
		JTextField nric4 = new JTextField();
		JTextField name4 = new JTextField();
		JTextField nric5 = new JTextField();
		JTextField name5 = new JTextField();
		String[] locations = { "Bedok Mall", "Tampines One", "NEX", "Vivo City", "Mustafa Centre", "ION Orchard",
				"Scape", "Bugis Junction" };
		JComboBox locationlist = new JComboBox(locations);

		top.setLayout(new GridLayout(0, 4));
		top.add(new JLabel("Name:"));
		top.add(name1);
		top.add(new JLabel("NRIC:"));
		top.add(nric1);
		top.add(new JLabel("Name:"));
		top.add(name2);
		top.add(new JLabel("NRIC:"));
		top.add(nric2);
		top.add(new JLabel("Name:"));
		top.add(name3);
		top.add(new JLabel("NRIC:"));
		top.add(nric3);
		top.add(new JLabel("Name:"));
		top.add(name4);
		top.add(new JLabel("NRIC:"));
		top.add(nric4);
		top.add(new JLabel("Name:"));
		top.add(name5);
		top.add(new JLabel("NRIC:"));
		top.add(nric5);
		top.add(new JLabel("Location:"));
		top.add(locationlist);

		buttons.add(checkin, BorderLayout.EAST);
		buttons.add(checkout, BorderLayout.WEST);

		main.add(top, BorderLayout.NORTH);
		main.add(buttons);

		checkin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] names = { name1.getText(), name2.getText(), name3.getText(), name4.getText(),
						name5.getText() };
				String[] nrics = { nric1.getText(), nric2.getText(), nric3.getText(), nric4.getText(),
						nric5.getText() };
				Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}", Pattern.CASE_INSENSITIVE);
				int r = 0;
				String locationstr = (String) locationlist.getSelectedItem();
				String status = "Check in";
				Matcher matcher = nricpattern.matcher(nrics[0]);
				boolean matchFound = matcher.find();
				// check info if first name exists + first nric is correct, call function

				if (name1.getText().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter at least 1 name");
				} else if (nric1.getText().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter at least 1 NRIC");
				} else if (!matchFound) {
					JOptionPane.showMessageDialog(null, "Please enter valid NRIC");
				} else {
					try {
						server.groupcheckin(names, nrics, locationstr, status);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Successful Check In!");
				}
				// there needs to be a for loop somewhere here idk where
			}
		});
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] names = { name1.getText(), name2.getText(), name3.getText(), name4.getText(),
						name5.getText() };
				String[] nrics = { nric1.getText(), nric2.getText(), nric3.getText(), nric4.getText(),
						nric5.getText() };
				Pattern nricpattern = Pattern.compile("[STFG]{1}[0-9]{7}[a-z]{1}", Pattern.CASE_INSENSITIVE);
				String locationstr = (String) locationlist.getSelectedItem();
				String status = "Check out";
				Matcher matcher = nricpattern.matcher(nrics[0]);
				boolean matchFound = matcher.find();

				// check info if first name exists + first nric is correct, call function
				if (name1.getText().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter at least 1 name");
				} else if (nric1.getText().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please enter at least 1 NRIC");
				} else if (!matchFound) {
					JOptionPane.showMessageDialog(null, "Please enter valid NRIC");
				} else {
					try {
						server.groupcheckin(names, nrics, locationstr, status);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Successful Check Out!");
				}
			}
		});
		frame.setContentPane(main);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}

	// allrecords page
	public void openallrecords() {

		frame = new JFrame("Group Safe Entry");
		JPanel main = new JPanel();
		JPanel top = new JPanel(); // welcome info
		JPanel buttons = new JPanel();

		check = new JButton("Find");
		check.setPreferredSize(new Dimension(200, 50));
		notify = new JButton("Notify on Quarintine");
		fromdate = new JTextField();
		fromtime = new JTextField();
		todate = new JTextField();
		totime = new JTextField();
		String[] locations = { "Bedok Mall", "Tampines One", "NEX", "Vivo City", "Mustafa Centre", "ION Orchard",
				"Scape", "Bugis Junction" };
		JComboBox locationlist = new JComboBox(locations);

		top.setLayout(new GridLayout(0, 4));
		top.add(new JLabel("From Date"));
		top.add(fromdate);
		top.add(new JLabel("From Time:"));
		top.add(fromtime);
		top.add(new JLabel("To Date"));
		top.add(todate);
		top.add(new JLabel("To Time:"));
		top.add(totime);
		top.add(new JLabel("Location:"));
		top.add(locationlist);

		buttons.setLayout(new GridLayout(0, 2));
		buttons.add(check);

		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pattern datepattern = Pattern.compile("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}", Pattern.CASE_INSENSITIVE);
				Pattern timepattern = Pattern.compile("[0-9]{2}[:]{1}[0-9]{2}", Pattern.CASE_INSENSITIVE);
				String locationstr = (String) locationlist.getSelectedItem();
				String fromdateinput = fromdate.getText();
				String fromtimeinput = fromtime.getText();
				String todateinput = todate.getText();
				String totimeinput = totime.getText();
				Matcher matcher1 = datepattern.matcher(fromdateinput);
				Matcher matcher2 = timepattern.matcher(fromtimeinput);
				Matcher matcher3 = datepattern.matcher(todateinput);
				Matcher matcher4 = timepattern.matcher(totimeinput);
				boolean matchFound1 = matcher1.find();
				boolean matchFound2 = matcher2.find();
				boolean matchFound3 = matcher3.find();
				boolean matchFound4 = matcher4.find();

				if (!matchFound1 || !matchFound3) {
					JOptionPane.showMessageDialog(null, "Please enter date in DD/MM/YYYY format");
				} else if (!matchFound2 || !matchFound4) {
					JOptionPane.showMessageDialog(null, "Please enter time in HH:MM format using 24 hour clock");
				} else {
					try {
						server.retrievedataMOH(fromdateinput, fromtimeinput, todateinput, totimeinput, locationstr);
						buttons.add(check);
						buttons.add(notify);
						buttons.validate();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		notify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String locationstr = (String) locationlist.getSelectedItem();
				String fromdateinput = fromdate.getText();
				String fromtimeinput = fromtime.getText();
				String todateinput = todate.getText();
				String totimeinput = totime.getText();
				try {
					server.notifynric(fromdateinput, fromtimeinput, todateinput, totimeinput, locationstr);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		main.add(top, BorderLayout.NORTH);
		main.add(buttons);
		frame.setContentPane(main);
		frame.setSize(500, 300);
		frame.setVisible(true);

	}

	// myrecord page
	public void openmyrecords() {

	}
}
