/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 *
 * @author jhansivsetty
 */
public class BankRMIServer extends UnicastRemoteObject implements BankRMIinterface{
    private static Map<String,String> userCred;
    private static Map<String,Account> accounts;
    private BankRMIServer() throws RemoteException{
        super();
    }
    
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(4444);
            registry.rebind("hi server", new BankRMIServer());
            System.out.println("Bank Server is ready");
            
            Bank bank = new Bank();
            userCred = bank.userCred();
            accounts = bank.buildAccounts();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean validateUser(String userName) throws Exception {
        return userCred.containsKey(userName);
    }

    @Override
    public boolean validateUserPass(String userName, String password) throws Exception {
        String pass = userCred.get(userName);
        return pass.equals(password);
    }
}
