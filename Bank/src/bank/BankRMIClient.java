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
                long startTime, endTime;
                
                boolean isValidUser = bri.validateUser(userName);
                if(isValidUser){
                    System.out.println("Enter the password");
                    String password = sc.next();
                    boolean isValidUserPass = bri.validateUserPass(userName, password);
                    if(isValidUserPass){
                        System.out.println("------Login Successfull------");
                        while(option != 7){
                            System.out.println("\nChoose the operation to perform:");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Display account number");
                            System.out.println("3. Display Account name");
                            System.out.println("4. Deposit");
                            System.out.println("5. Withdraw");
                            System.out.println("6. Transfer");
                            System.out.println("7. Exit");
                            
                            option = sc.nextInt();
                            if(option >7 || option<1){
                                System.err.println("Invalid option\n");
                            }
                            
                            switch(option){
                                case 1: // To check the balance
                                        balance = bri.checkBalance();
                                        System.out.println("The current balance is :"+balance);
                                        break;
                                case 2: // To get the account number
                                        String accNumber = bri.fetchAccountNumber();
                                        System.out.println("The Account number is: "+accNumber);
                                        break;
                                case 3: //To get the account name
                                        String accName = bri.fetchAccountName();
                                        System.out.println("The acocunt name is: "+accName);
                                        break;
                                case 4: //To deposit an amount
                                        System.out.println("Enter the amount to deposit:");
                                        startTime = System.nanoTime();
                                        amount = sc.nextDouble();
                                        //System.out.println("The amount is:"+amount);
                                        balance = bri.deposit(amount);
                                        if(balance <0){
                                            System.err.println("Invalid amount. Enter valid amount between 0 to 10000.");
                                            
                                        }else{
                                            System.out.println("Deposit Successful, Current balance is: "+ balance);
                                        }
                                        //End Time
                                        endTime = System.nanoTime();
                                        System.out.println("The transaction took "+((endTime - startTime)/1000) + "ms"); 
                                        break;
                                case 5: //To withdraw an amount
                                        System.out.println("Enter the amount to withdraw");
                                        //Start time
                                        startTime = System.nanoTime();
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
                                        //End Time
                                        endTime = System.nanoTime();
                                        System.out.println("The transaction took "+((endTime - startTime)/1000) + "ms"); 
                                        break;
                                case 6: //To transfer an amount
                                        System.out.println("Enter the account number to which amount to be transfered:");
                                        //Start time
                                        startTime = System.nanoTime();
                                        accNumber = sc.next();
                                        boolean isAccPresent = bri.checkValidAccount(accNumber);
                                        if(isAccPresent){
                                            System.out.println("Enter the amount to transfer");
                                            amount = sc.nextDouble();
                                            if(amount<=0){
                                                System.err.println("Invalid amount");
                                            }else{
                                                balance = bri.transfer(amount);
                                                if(balance == -1){
                                                    System.err.println("Not enough balance to transfer");
                                                }else{
                                                    System.out.println("Transfer successfull. The current balance is: "+balance);
                                                }
                                            }
                                        }else{
                                            System.err.println("The account number is invalid");
                                        }
                                        //End Time
                                        endTime = System.nanoTime();
                                        System.out.println("The transaction took "+((endTime - startTime)/1000) + "ms"); 
                                        break;
                                case 7: break;
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
