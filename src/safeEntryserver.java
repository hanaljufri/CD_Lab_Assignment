import java.rmi.Naming; //Import naming classes to bind to rmiregistry

public class safeEntryserver {
	static int port = 1099;

	public safeEntryserver() {

		try {
			safeEntry c = new safeEntryimpl();
			Naming.rebind("rmi://localhost/SafeEntryService", c);
		} catch (Exception e) {
			System.out.println("Server Error: " + e);
		}
	}

	public static void main(String args[]) {
		// Create the new Calculator server
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		new safeEntryserver();
	}

}