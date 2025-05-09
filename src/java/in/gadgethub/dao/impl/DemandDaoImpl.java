package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
import in.gadgethub.dao.ProductDao;
import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

public class DemandDaoImpl implements DemandDao {

    @Override
    public boolean addProduct(DemandPojo demandPojo) {
        boolean status = false;
        String updateSQL = "Update userdemand set quantity=quantity+? where useremail=? and prodid=?";
        String insertSQL = "Insert into userdemand values(?,?,?)";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            ps1 = conn.prepareStatement(updateSQL);
            ps1.setInt(1, demandPojo.getdemandQuantity());
            ps1.setString(2, demandPojo.getUserEmail());
            ps1.setString(3, demandPojo.getProdId());
            int k = ps1.executeUpdate();
            if (k == 0) {
                ps2 = conn.prepareStatement(insertSQL);
                ps2.setString(1, demandPojo.getUserEmail());
                ps2.setString(2, demandPojo.getProdId());
                ps2.setInt(3, demandPojo.getdemandQuantity());
                ps2.executeUpdate();

            }
            //for sending demandSuccess code
            String userEmail = demandPojo.getUserEmail();
            UserDao userDao = new UserDaoImpl();
            String userName = userDao.getUserFirstName(userEmail);
            ProductDao productDao = new ProductDaoImpl();
            ProductPojo products = productDao.getProductDetails(demandPojo.getProdId());
            MailMessage.demandSuccess(userEmail, userName, products.getProdName(), demandPojo.getdemandQuantity(),productDao.getProductQuantity(demandPojo.getProdId()));
            System.out.println("Mail Sent Successfully");
            status = true;
        } catch (SQLException ex) {
            System.out.println("Error in addProduct:" + ex);
            ex.printStackTrace();
        }
        catch (MessagingException ex) {
            System.out.println("Exception occured in registerUser method............................");
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    public boolean removeProduct(String userId, String prodId) {
        boolean status = false;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from userdemand where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, prodId);
            int count = ps.executeUpdate();
            if (count > 0) {
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in removeProduct method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    public List<DemandPojo> haveDemanded(String prodId) {
        List<DemandPojo> demandList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from userdemand where prodId=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            while (rs.next()) {
                DemandPojo demandPojo = new DemandPojo();
                demandPojo.setUserEmail(rs.getString("useremail"));
                demandPojo.setProdId(rs.getString("prodid"));
                demandPojo.setQuantity(rs.getInt("quantity"));
                demandList.add(demandPojo);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in haveDemanded method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return demandList;
    }
}
