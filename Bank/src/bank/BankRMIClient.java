/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author jhansivsetty
 * 1. incorrect username
 * 2. incorrect password
 * 3. login successfull
 */
public class BankRMIClient {
    public static void main(String[] args) throws Exception {
        BankRMIinterface bri = null;
        try {
            //Registry registry = LocateRegistry.getRegistry("Localhost", 4444);
            try {
                Registry registry = LocateRegistry.getRegistry("Localhost", 4444);
                bri = (BankRMIinterface)registry.lookup("hi server");
                System.out.println("Connected to Bank server");
                
                System.out.println("\nEnter the username");
                Scanner sc = new Scanner(System.in);
                String userName = sc.next();
                
                boolean isValidUser = bri.validateUser(userName);
                if(isValidUser){
                    System.out.println("Enter the password");
                    String password = sc.next();
                    boolean isValidUserPass = bri.validateUserPass(userName, password);
                    if(isValidUserPass){
                        System.out.println("------Login Successfull------");
                    }else{
                        System.err.println("Incorrect password");
                    }
                }else{
                    System.err.println("No such User Present");
                }
                
            }catch (NotBoundException | AccessException ex) {
                ex.printStackTrace();
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
