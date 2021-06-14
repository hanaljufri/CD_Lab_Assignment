import java.rmi.RemoteException;

import javax.swing.JTextField;

public interface safeEntry
          extends java.rmi.Remote {
	
//define all functions here

    public long add(long a, long b)
        throws java.rmi.RemoteException;

    public long sub(long a, long b)
        throws java.rmi.RemoteException;

    public long mul(long a, long b)
        throws java.rmi.RemoteException;

    public long div(long a, long b)
        throws java.rmi.RemoteException;

    public long pow(long a, int b)
	throws java.rmi.RemoteException;
    
	public void tell (String name)throws RemoteException ;
	public void getinfo(String namestr, String nricstr, String locationstr, String status)throws RemoteException ;

}
