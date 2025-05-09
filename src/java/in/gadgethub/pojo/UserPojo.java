package in.gadgethub.pojo;

public class UserPojo {

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPincodeNumber() {
        return pincodeNumber;
    }

    public void setPincodeNumber(int pincodeNumber) {
        this.pincodeNumber = pincodeNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserPojo{" + "userEmail=" + userEmail + ", userName=" + userName + ", mobile=" + mobile + ", address=" + address + ", pincodeNumber=" + pincodeNumber + ", password=" + password + '}';
    }

    public UserPojo() {
    }

    public UserPojo(String userEmail, String userName, String mobile, String address, int pincodeNumber, String password) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.mobile = mobile;
        this.address = address;
        this.pincodeNumber = pincodeNumber;
        this.password = password;
    }
    private String userEmail;
    private String userName;
    private String mobile;
    private String address;
    private int pincodeNumber;
    private String password;
}
