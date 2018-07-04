/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.Remote;

/**
 * @author jhansivsetty
 */
public interface BankRMIinterface extends Remote {
    boolean validateUser(String userName) throws Exception;
    
    boolean validateUserPass(String userName, String password) throws Exception;
}
