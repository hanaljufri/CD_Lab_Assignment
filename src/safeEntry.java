import java.rmi.RemoteException;

import javax.swing.JTextField;

public interface safeEntry
          extends java.rmi.Remote {
	
	//define functions here

	public void getinfo(String namestr, String nricstr, String locationstr, String status)throws RemoteException ;

	public void groupcheckin(String[] names, String nrics[], String location, String status)throws RemoteException;

}
