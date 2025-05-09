package in.gadgethub.pojo;
import java.util.Date;
public class TransactionPojo {

    public TransactionPojo(String transId, String userEmail, Date transTime, double amount) {
        this.transId = transId;
        this.userEmail = userEmail;
        this.transTime = transTime;
        this.amount = amount;
    }

    public TransactionPojo() {
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    private String transId;
    private String userEmail;
    private Date transTime;
    private double amount;
}
