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
                int option = 0;
                double balance,amount;
                
                boolean isValidUser = bri.validateUser(userName);
                if(isValidUser){
                    System.out.println("Enter the password");
                    String password = sc.next();
                    boolean isValidUserPass = bri.validateUserPass(userName, password);
                    if(isValidUserPass){
                        System.out.println("------Login Successfull------");
                        while(option != 5){
                            System.out.println("\nChoose the operation to perform:");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. Transfer");
                            System.out.println("5. Exit");
                            
                            option = sc.nextInt();
                            if(option >5 || option<1){
                                System.err.println("Invalid option\n");
                            }
                            
                            switch(option){
                                case 1: balance = bri.checkBalance(userName);
                                        System.out.println("The current balance is :"+balance);
                                        break;
                                case 2: System.out.println("Enter the amount to deposit:");
                                        amount = sc.nextDouble();
                                        //System.out.println("The amount is:"+amount);
                                        balance = bri.deposit(amount);
                                        if(balance <0){
                                            System.err.println("Invalid amount. Enter valid amount between 0 to 10000.");
                                        }else{
                                            System.out.println("Deposit Successfull, Current balance is: "+ balance);
                                        }
                                        break;
                                case 3: System.out.println("Enter the amount to withdraw");
                                        amount = sc.nextDouble();
                                        if(amount<=0){
                                            System.err.println("Enter valid amount");
                                        }else{
                                            balance = bri.withdraw(amount);
                                            if(balance <0){
                                                System.err.println("Not enough balance to withdraw");
                                            }else{
                                                System.out.println("Withdraw Successfull. Current balance is: "+balance);
                                            }
                                        }
                                        break;
                                case 4: break;
                                case 5: break;
                                default : break;
                            }
                         
                        }
                        System.out.println("----Customer successfully logged out !!!----");
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
