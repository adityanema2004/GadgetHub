/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author adity
 */
public class UserDaoImpl implements UserDao {
    @Override
    public boolean isRegistered(String emailId){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        boolean flag=false;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select 1 from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            
            if(rs.next()){
                flag=true;
            }
        }
        catch(SQLException ex){
                System.out.println("Error in isRegistered method!!");
                ex.printStackTrace();
            }
        
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return flag;
    }

    @Override
    public String registerUser(UserPojo user) {
        String status="Registration Failed";
        boolean isUserRegistered=isRegistered(user.getUseremail());
        if(isUserRegistered){
            status="Email Already Registered.Try Again";
            return status;
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            {
                ps=conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
                ps.setString(1,user.getUseremail());
                ps.setString(2,user.getUsername());
                ps.setString(3,user.getMobile());
                ps.setString(4,user.getAddress());
                ps.setInt(5,user.getPincode());
                ps.setString(6,user.getPassword());
                int count=ps.executeUpdate();
                if(count==1){
                    status="Registration Successfull";
//                    Code to send email
                }
                 
            }
        }
        catch(SQLException ex){
                System.out.println("Error in registerUser method!!");
                ex.printStackTrace();
            }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String isValidCredentials(String emailId, String password) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String status="Login Denied. Invalid username or password";
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select 1 from users where useremail=? and password=?");
            ps.setString(1, emailId);
            ps.setString(2,password);
            rs=ps.executeQuery();
            
            if(rs.next()){
                status="Login Successfull";
            }
        }
        catch(SQLException ex){
            status="Error:"+ex.getMessage();
                System.out.println("Error in isValidCredentials method!!");
                ex.printStackTrace();
            }
        
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public UserPojo getUserDetails(String emailId) {
        UserPojo user=null;
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                user=new UserPojo();
                user.setUseremail(rs.getString("useremail"));
                user.setUsername(rs.getString("username"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPincode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }
        }
        catch(SQLException ex){
                System.out.println("Error in getUserDetails method!!");
                ex.printStackTrace();
            }
        
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return user;
    }

    @Override
    public String getUserFirstName(String emailId) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String fname=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            
            if(rs.next()){
                String fullName=rs.getString(1);
                fname=fullName.split(" ")[0];
                
            }
        }
        catch(SQLException ex){
                System.out.println("Error in getUserFirstName method!!");
                ex.printStackTrace();
            }
        
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return fname;
    }

    @Override
    public String getUserAddr(String emailId) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String address=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select address from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                address=rs.getString(1);
            }
        }
        catch(SQLException ex){
                System.out.println("Error in getUserAddr method!!");
                ex.printStackTrace();
            }
        
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return address;
    }
}
    

