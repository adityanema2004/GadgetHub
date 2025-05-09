/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

/**
 *
 * @author Hp
 */
public class UserdemandPojo {

    @Override
    public String toString() {
        return "Demandpojo{" + "useremail=" + useremail + ", prodid=" + prodid + ", demandquantity=" + demandquantity + '}';
    }

   

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public int getDemandquantity() {
        return demandquantity;
    }

    public void setDemandquantity(int demandquantity) {
        this.demandquantity = demandquantity;
    }

    public UserdemandPojo(String useremail, String prodid, int demandquantity) {
        this.useremail = useremail;
        this.prodid = prodid;
        this.demandquantity = demandquantity;
    }
    private String useremail;
    private String prodid;
    private int demandquantity;

    public UserdemandPojo() {
    }
    
}
