import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
//import au.com.bytecode.opencsv.CSVWriter;
import java.util.Map;

public class safeEntryimpl extends java.rmi.server.UnicastRemoteObject implements safeEntry {

	public safeEntryimpl() throws java.rmi.RemoteException {
		super();
	}

	// current errors:
	// saving data of all 5 check ins when there were only 2
	//

	public void getinfo(String namestr, String nricstr, String locationstr, String status) {
		DateTimeFormatter dtfdate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter dtftime = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime nowdate = LocalDateTime.now();
		LocalDateTime nowtime = LocalDateTime.now();

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
				Files.write(path, nowdate.format(dtfdate).toString().getBytes(), StandardOpenOption.APPEND);
				Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
				Files.write(path, nowtime.format(dtftime).toString().getBytes(), StandardOpenOption.APPEND);
				Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
				Files.write(path, "N".getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		} else {
			try {
				File f = new File("C:\\Users\\Hana\\eclipse-workspace\\CD_Lab_Assignment\\src\\database.csv");
				pw = new FileWriter("database.csv", true);
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
				pw.append(nowdate.format(dtfdate).toString());
				pw.append(",");
				pw.append(nowtime.format(dtftime).toString());
				pw.append(",");
				pw.append("N");
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void retrievedataMOH(String fromdateinput, String fromtimeinput, String todateinput, String totimeinput,
			String locationstr) {
		System.out.print("im here");
		Map<String, String> map = new HashMap<String, String>();
		String line = "";
		Date todate, fromdate, fromtime, totime;

		try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
			fromdate = new SimpleDateFormat("dd/MM/yyyy").parse(fromdateinput);
			todate = new SimpleDateFormat("dd/MM/yyyy").parse(todateinput);
			fromtime = new SimpleDateFormat("HH:mm").parse(fromtimeinput);
			totime = new SimpleDateFormat("HH:mm").parse(totimeinput);
			while ((line = br.readLine()) != null) {
				String[] info = line.split(",");
				if (info[2] == locationstr) {
					Date tempdate, temptime;
					//if checkin is before the from and checkout is after the to
					tempdate = new SimpleDateFormat("dd/MM/yyyy").parse(info[4]);
					temptime = new SimpleDateFormat("HH:mm").parse(info[5]);
					if (tempdate.after(fromdate) && tempdate.before(todate)) {
						if (temptime.after(fromtime) && temptime.before(totime)) {

						}
					}
				} else {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// retrieve data from file based on location & time of sign in&out
		// notify users
		// notify based on nric (not sure how)
		// ifnot ask for nric and retrieve data based on that (will show if have been
		// notified to stayquarintined)
		catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void notifynric(String fromdateinput, String fromtimeinput, String todateinput, String totimeinput,
			String locationstr) {
		
		//change all n to y 

	}

	public void retrievedataNRIC() {
		//retrieve data based on nric (if got y, must show "to quarantine")
		//display history
	}

	public void groupcheckin(String[] names, String nrics[], String location, String status) {
		DateTimeFormatter dtfdate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter dtftime = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime nowdate = LocalDateTime.now();
		LocalDateTime nowtime = LocalDateTime.now();
		outerloop: for (int r = 0; r < names.length; r++) {
			if (names[r].equals(null) || names[r].equals("")) {
				break outerloop;
			} else {
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
						Files.write(path, nowdate.format(dtfdate).toString().getBytes(), StandardOpenOption.APPEND);
						Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
						Files.write(path, nowtime.format(dtftime).toString().getBytes(), StandardOpenOption.APPEND);
						Files.write(path, ",".getBytes(), StandardOpenOption.APPEND);
						Files.write(path, "N".getBytes(), StandardOpenOption.APPEND);

					} catch (IOException e) {
						System.err.println("Error: " + e.getMessage());
					}
				} else {
					try {
						File f = new File("C:\\Users\\Hana\\eclipse-workspace\\CD_Lab_Assignment\\src\\database.csv");
						pw = new FileWriter("database.csv", true);
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
						pw.append(nowdate.format(dtfdate).toString());
						pw.append(",");
						pw.append(nowtime.format(dtftime).toString());
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
