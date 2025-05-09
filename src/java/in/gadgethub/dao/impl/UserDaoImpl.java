package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;

public class UserDaoImpl implements UserDao {

    @Override
    public String registerUser(UserPojo user) {
        String status="Registration Failed";
        boolean isUserRegistered=isRegistered(user.getUserEmail());
        if(isUserRegistered){
            status="Email Already Registered. Try Again";
            return status;
        }
         Connection conn = DBUtil.provideConnection();
         PreparedStatement ps=null;
       try{
             ps=conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
             ps.setString(1, user.getUserEmail());
             ps.setString(2, user.getUserName());
             ps.setString(3, user.getMobile());
             ps.setString(4, user.getAddress());
             ps.setInt(5, user.getPincodeNumber());
             ps.setString(6, user.getPassword());
             int count=ps.executeUpdate();
             if(count==1){
                 status="Registration Successfull";
                 //code to send email
                 MailMessage.registrationSuccess(user.getUserEmail(), user.getUserName());
                 System.out.println("Mail Sent Successfully");
             }
       }catch(SQLException ex){
           System.out.println("Exception occured in registerUser method............................");
           ex.printStackTrace();
       }
       catch(MessagingException ex){
           System.out.println("Exception occured in registerUser method............................");
           ex.printStackTrace();
       }
       
      DBUtil.closeStatement(ps);
      return status;
    }   
    @Override
    public boolean isRegistered(String emailId) {
        PreparedStatement ps=null;
         ResultSet rs=null;
          Connection conn = DBUtil.provideConnection();
         boolean flag=false;
       try{
             ps=conn.prepareStatement("select 1 from users where useremail = ?");
             ps.setString(1, emailId);
             rs=ps.executeQuery();
             if(rs.next()){
                 flag=true;
             }
       }catch(SQLException ex){
           System.out.println("Exception occured in isRegistered method.............................");
           ex.printStackTrace();
       }
      DBUtil.closeResultSet(rs);
      DBUtil.closeStatement(ps);
      return flag;
}

    @Override
    public String isValidCredentials(String emailId, String password) {
         PreparedStatement ps=null;
         Connection conn = DBUtil.provideConnection();
         ResultSet rs=null;
          String status="Login Denied! Incorrect Username/password";
       try{
             ps=conn.prepareStatement("select 1 from users where useremail = ? and password=?");
             ps.setString(1, emailId);
             ps.setString(2, password);
             rs=ps.executeQuery();
             if(rs.next()){
                status="Login Successful";
                //sending for email code
                UserPojo user=getUserDetails(emailId);
                MailMessage.loginSuccess(emailId, user.getUserName());
                System.out.println("Mail Sent Successfully");
             }
       }catch(SQLException ex){
           status="Error :"+ex.getMessage();
           System.out.println("Exception occured in isValidCredentials  method............");
           ex.printStackTrace();
       }
        catch(MessagingException ex){
           System.out.println("Exception occured in registerUser method............................");
           ex.printStackTrace();
       }
      DBUtil.closeResultSet(rs);
      DBUtil.closeStatement(ps);
      return status;
    }

    @Override
    public UserPojo getUserDetails(String emaiIId) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        UserPojo user=null;
       try{
             ps=conn.prepareStatement("select * from users where useremail = ?");
             ps.setString(1, emaiIId);
             rs=ps.executeQuery();
             if(rs.next()){
                 user=new UserPojo();
                 user.setUserEmail(rs.getString("useremail"));
                 user.setUserName(rs.getString("username"));
                 user.setMobile(rs.getString("mobile"));
                 user.setAddress(rs.getString("address"));
                 user.setPincodeNumber(rs.getInt("pincode"));
                 user.setPassword(rs.getString("password"));
             }
       }catch(SQLException ex){
           System.out.println("Exception occured in getUserDetails method.....................................");
           ex.printStackTrace();
       }
      DBUtil.closeResultSet(rs);
      DBUtil.closeStatement(ps);
      return user;
    }

    @Override
    public String getUserFirstName(String emailId) {
          PreparedStatement ps=null;
         ResultSet rs=null;
         Connection conn = DBUtil.provideConnection();
          String fName=null;
       try{
             ps=conn.prepareStatement("select username from users where useremail = ?");
             ps.setString(1, emailId);
             rs=ps.executeQuery();
             if(rs.next()){
                 String username=rs.getString("username");
                  fName=username.split(" ")[0];
             }
       }catch(SQLException ex){
           System.out.println("Exception occured in getUserFirstName method........................................");
           ex.printStackTrace();
       }
      DBUtil.closeResultSet(rs);
      DBUtil.closeStatement(ps);
      return fName;
    }

    @Override
    public String getUserAddr(String emailId) {
         Connection conn = DBUtil.provideConnection();
         PreparedStatement ps=null;
         ResultSet rs=null;
          String address=null;
       try{
             ps=conn.prepareStatement("select address from users where useremail = ?");
             ps.setString(1, emailId);
             rs=ps.executeQuery();
             if(rs.next()){
                 address=rs.getString("address");
             }
       }catch(SQLException ex){
           System.out.println("Exception occured in getUserAddr method.............................................");
           ex.printStackTrace();
       }
      DBUtil.closeResultSet(rs);
      DBUtil.closeStatement(ps);
      return address;
 }
}
