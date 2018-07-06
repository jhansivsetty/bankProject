
package bank;
/**
 *
 * @author jhansivsetty
 */
public class Account {

    private String accName;
    private String accNumber;
    private double balance;

    public String getAccName() {
        return accName;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
