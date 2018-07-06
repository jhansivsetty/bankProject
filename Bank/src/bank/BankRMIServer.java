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
import java.util.Set;

/**
 *
 * @author jhansivsetty
 */
public class BankRMIServer extends UnicastRemoteObject implements BankRMIinterface{
    private static Map<String,String> userCred;
    private static Map<String,Account> accounts;
    private Account selectedAccount;
    private Account selectedToAccount;
    //private Account selectedAccount; 
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
        boolean isValidUserPss = pass.equals(password);
        if(isValidUserPss){
          selectedAccount = accounts.get(userName);  
        }
        return isValidUserPss;
    }

    //To check the balance of the user
    @Override
    public double checkBalance() throws Exception {
        return selectedAccount.getBalance();
    }
    
    @Override
    public String fetchAccountNumber() throws Exception {
        return selectedAccount.getAccNumber();
    }

    @Override
    public String fetchAccountName() throws Exception {
        return selectedAccount.getAccName();
    }

    //To deposit amount only if it is between 0 and 10000
    @Override
    public double deposit(double amount) throws Exception {
        if(amount > 0 && amount <= 10000){
            double currBalance = selectedAccount.getBalance();
            currBalance += amount;
            selectedAccount.setBalance(currBalance);
            return selectedAccount.getBalance();
        }else{
            return -1;
        }
    }

    @Override
    public double withdraw(double amount) throws Exception {
        double currBalance = selectedAccount.getBalance();
        if(amount > currBalance){
            //in case the amount to withdraw is greater than the balance
            return -1;
        } else{
            currBalance -= amount;
            selectedAccount.setBalance(currBalance);
            return selectedAccount.getBalance();
        }
    }

    @Override
    public boolean checkValidAccount(String accNumber) throws Exception {
        boolean isAccountExist = false;
        Set<String> keySet = accounts.keySet();
        for(String uname:keySet){
            Account account = accounts.get(uname);
            if(account.getAccNumber().equals(accNumber)){
                isAccountExist = true;
                selectedToAccount = account;
                break;
            }
        }
        return isAccountExist;
    }

    @Override
    public double transfer(double amount) throws Exception {
        double currBalance = selectedAccount.getBalance();
        System.out.println("Initial balance of from account with account number: "+selectedAccount.getAccNumber()+" is "+currBalance);
        System.out.println("Initial balance of to account with account number :"+selectedToAccount.getAccNumber()+" is "+ selectedToAccount.getBalance());
        System.out.println("Amount to transfer: "+amount);
        if(amount > currBalance){
            return -1;
        }else{
            currBalance -= amount;
            selectedAccount.setBalance(currBalance);
            System.out.println("The balance of from acc after transfer: "+selectedAccount.getBalance());
            
            double toAccBalance = selectedToAccount.getBalance();
            toAccBalance += amount;
            selectedToAccount.setBalance(toAccBalance);
            System.out.println("The balance of to account after transfer: "+selectedToAccount.getBalance());
        }
        return selectedAccount.getBalance();
    }
}
