/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jhansivsetty
 */
public class Bank {
    public Map userCred(){
        Map<String, String> credentials = new HashMap<>();
            credentials.put("abc", "123");
            credentials.put("def", "456");
            credentials.put("ghi", "789");
        return credentials;
    } 
    
    public Map buildAccounts(){
        Map<String, Account> accountsMap = new HashMap<>();
        
        Account acc1 = new Account();
        acc1.setAccName("Account1");
        acc1.setAccNumber("343454");
        acc1.setBalance(0);
        
        Account acc2 = new Account();
        acc2.setAccName("Account2");
        acc1.setAccNumber("348394");
        acc1.setBalance(0);
        
        
        Account acc3 = new Account();
        acc1.setAccName("Account3");
        acc1.setAccNumber("345451");
        acc1.setBalance(0);
        
        accountsMap.put("abc", acc1);
        accountsMap.put("def", acc2);
        accountsMap.put("ghi", acc3);
        
        return accountsMap;
    }
}