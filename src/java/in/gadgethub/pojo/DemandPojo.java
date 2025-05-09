package in.gadgethub.pojo;
public class DemandPojo {

    public DemandPojo() {
    }

    public DemandPojo(String userEmail, String prodId, int demandQuantity) {
        this.userEmail = userEmail;
        this.prodId = prodId;
        this.demandQuantity = demandQuantity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getdemandQuantity() {
        return demandQuantity;
    }

    public void setQuantity(int quantity) {
        this.demandQuantity = quantity;
    }
    private String userEmail;
    private String prodId;
    private int demandQuantity;
}
