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
	
	   public void getinfo(String namestr,String nricstr, String locationstr, String status) {
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now(); 
		   //String[] dataLines = new String[] {namestr, nricstr, locationstr, status, now.format(dtf).toString()};

		   System.out.print("\n"+namestr+nricstr+locationstr+status+now.format(dtf).toString());
 
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
					pw.flush();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
	   }
	   
	   public void retrievedata() {
		   //ask if moh anot if yes do below
		   	//retrieve data from file based on location & time of sign in&out
		   	//notify users
		   	//notify based on nric (not sure how)
		   //ifnot ask for nric and retrieve data based on that (will show if have been notified to stayquarintined)
	   }
	   
	   public void groupsignin() {
		   //do grp sign in?
	   }
	   

}
