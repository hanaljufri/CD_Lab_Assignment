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
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import au.com.bytecode.opencsv.CSVWriter;

public class safeEntryimpl
	extends java.rmi.server.UnicastRemoteObject implements safeEntry{

    public safeEntryimpl()
        throws java.rmi.RemoteException {
        super();
    }

    public long add(long a, long b)
        throws java.rmi.RemoteException {
    	return a +b;
    }

    public long sub(long a, long b)
        throws java.rmi.RemoteException {
    	return a-b;
    }

    public long mul(long a, long b)
        throws java.rmi.RemoteException {
    	return a*b;
    }

    public long div(long a, long b)
        throws java.rmi.RemoteException {
    	return a/b;
    }

    public long pow(long a, int b)
    		throws java.rmi.RemoteException {
    		if (b==0)
    			return 1;
    		else 
    			return a*pow(a,b-1);
	  }
    
	private String name;
	private safeEntryclient ui;	
	public safeEntryimpl (String n) throws RemoteException {
		name=n;
		}
	
	public void tell(String st) throws RemoteException{
		System.out.println(st);
		//ui.writeMsg(st);
	}
	   public void getinfo(String namestr,String nricstr, String locationstr, String status) {
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now(); 
		   //String[] dataLines = new String[] {namestr, nricstr, locationstr, status, now.format(dtf).toString()};

		   System.out.print("\n"+namestr+nricstr+locationstr+status+now.format(dtf).toString());
 
		    FileWriter pw;
		    Path path = Paths.get("C:\\Users\\Hana\\eclipse-workspace\\CD_Lab_Assignment\\src\\database.csv");
		    if (Files.exists(path)) {
	    	try {
	    		/*
	    		BufferedWriter out = null;
	    	    FileWriter fstream = new FileWriter("database.csv", true); //true tells to append data.
	    	    out = new BufferedWriter(fstream);
	    	    out.write(Arrays.toString(dataLines));
	    	    */
	    		CSVWriter writer = new CSVWriter(new FileWriter("database.csv", true));
	    		
	    		writer.writeNext(namestr);
	    		writer.writeNext(",");
	    		writer.writeNext(nricstr);
	    		writer.writeNext(",");
	    		writer.writeNext(locationstr);
	    		writer.writeNext(",");
	    		writer.writeNext(status);
	    		writer.writeNext(",");
				pw.append(now.format(dtf).toString());
	    		writer.flush();
	    		writer.close();
	    	}
	    	catch (IOException e) {
	    	    System.err.println("Error: " + e.getMessage());
	    	}
		    }
		    else {
				try {
					pw = new FileWriter("database.csv");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	   }
	
	public void setGUI(safeEntryclient t){ 
		ui=t ; 
	} 	
}
