import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
//import au.com.bytecode.opencsv.CSVWriter;

public class safeEntryimpl
	extends java.rmi.server.UnicastRemoteObject implements safeEntry{

    public safeEntryimpl()
        throws java.rmi.RemoteException {
        super();
    }
    
    //current errors:
    	//saving data of all 5 check ins when there were only 2
    	//
	
	   public void getinfo(String namestr,String nricstr, String locationstr, String status) {
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now(); 
 
		    FileWriter pw;
		    Path path = Paths.get("database.csv");
		    if (Files.exists(path)) {
	    	try {
	    		Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, namestr.getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, nricstr.getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, locationstr.getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, status.getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, now.format(dtf).toString().getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
	    		Files.write(path, "N".getBytes(), StandardOpenOption.APPEND);
	    	}
	    	catch (IOException e) {
	    	    System.err.println("Error: " + e.getMessage());
	    	}
		    }
		    else {
				try {
					File f = new File("C:\\Users\\Hana\\eclipse-workspace\\CD_Lab_Assignment\\src\\database.csv");
					pw = new FileWriter("database.csv",true);
					pw.append("Name");
					pw.append(",");
					pw.append("NRIC");
					pw.append(",");
					pw.append("Location");
					pw.append(",");
					pw.append("Status");
					pw.append(",");
					pw.append("Date & Time");
					pw.append(",");
					pw.append("In Risk?");
					pw.append(",");
					pw.append("\n");
					pw.append(namestr);
					pw.append(",");
					pw.append(nricstr);
					pw.append(",");
					pw.append(locationstr);
					pw.append(",");
					pw.append(status);
					pw.append(",");
					pw.append(now.format(dtf).toString());
					pw.append(",");
					pw.append("N");
					pw.flush();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
	   }
	   
	   public void retrievedataMOH(String fromdateinput, String fromtimeinput, String todateinput,String totimeinput,String locationstr) {
		   
		   	//retrieve data from file based on location & time of sign in&out
		   	//notify users
		   	//notify based on nric (not sure how)
		   //ifnot ask for nric and retrieve data based on that (will show if have been notified to stayquarintined)
	   }
	   
	   public void notifynric(String fromdateinput, String fromtimeinput, String todateinput,String totimeinput,String locationstr) {
		   
	   }
	   
	   public void retrievedataNRIC() {
		   //ask if moh anot if yes do below
		   	//retrieve data from file based on location & time of sign in&out
		   	//notify users
		   	//notify based on nric (not sure how)
		   //ifnot ask for nric and retrieve data based on that (will show if have been notified to stayquarintined)
	   }
	   
	   public void groupcheckin(String[] names, String nrics[], String location, String status) {
		   //do grp sign in?
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now(); 
		   //int r = 0;
		   outerloop:
		   for (int r=0; r<names.length; r++) {
			   //return null to remove?
			  if (names[r].equals(null) || names[r].equals("") ) {
				break outerloop;  
			  } 
			  else {
				   FileWriter pw;
				    Path path = Paths.get("database.csv");
				    if (Files.exists(path)) {
			    	try {
			    		Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, names[r].getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, nrics[r].getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, location.getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, status.getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, now.format(dtf).toString().getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
			    		Files.write(path, "N".getBytes(), StandardOpenOption.APPEND);

			    	}
			    	catch (IOException e) {
			    	    System.err.println("Error: " + e.getMessage());
			    	}
				    }
				    else {
						try {
							File f = new File("C:\\Users\\Hana\\eclipse-workspace\\CD_Lab_Assignment\\src\\database.csv");
							pw = new FileWriter("database.csv",true);
							pw.append("Name");
							pw.append(",");
							pw.append("NRIC");
							pw.append(",");
							pw.append("Location");
							pw.append(",");
							pw.append("Status");
							pw.append(",");
							pw.append("Date & Time");
							pw.append(",");
							pw.append("In Risk?");
							pw.append(",");
							pw.append("\n");
							pw.append(names[r]);
							pw.append(",");
							pw.append(nrics[r]);
							pw.append(",");
							pw.append(location);
							pw.append(",");
							pw.append(status);
							pw.append(",");
							pw.append(now.format(dtf).toString());
							pw.append(",");
							pw.append("N");
							pw.flush();
							pw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				    }
			   }
		   }
	   }
}
