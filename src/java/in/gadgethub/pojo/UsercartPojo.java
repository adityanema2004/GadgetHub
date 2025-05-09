/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

/**
 *
 * @author LAPTOP
 */
public class UsercartPojo {

    public UsercartPojo(String userEmail, String prodId, int quantity) {
        this.userEmail = userEmail;
        this.prodId = prodId;
        this.quantity = quantity;
    }

    public UsercartPojo() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    private String userEmail;
    private String prodId;
    private int quantity;
}