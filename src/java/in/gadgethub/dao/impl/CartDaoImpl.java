package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.dao.ProductDao;
import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.pojo.UsercartPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

public class CartDaoImpl implements CartDao {

    @Override
    public String addProductToCart(UsercartPojo cart) {
        String status = "Failed to add into cart!";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from usercart where useremail=? and prodid=?");
            ps.setString(1, cart.getUserEmail());
            ps.setString(2, cart.getProdId());
            rs = ps.executeQuery();
            if (rs.next()) {
                ProductDaoImpl prodDao = new ProductDaoImpl();
                int stockQty = prodDao.getProductQuantity(cart.getProdId());
                int newQty = cart.getQuantity() + rs.getInt("quantity");
                if (stockQty < newQty) {
                    cart.setQuantity(stockQty);
                    updateProductInCart(cart);
                    status = "Only " + stockQty + " no of items are available in our stock so we are adding " + stockQty + " in your cart";
                    DemandPojo demandPojo = new DemandPojo();
                    demandPojo.setProdId(cart.getProdId());
                    demandPojo.setUserEmail(cart.getUserEmail());
                    demandPojo.setQuantity(newQty - stockQty);
                    DemandDaoImpl demandDao = new DemandDaoImpl();
                    boolean result = demandDao.addProduct(demandPojo);
                    if (result == true) {
                        status = "We will mail you when " + (newQty - stockQty) + " no of items will be available";
                    }
                } else {
                    cart.setQuantity(newQty);
                    updateProductInCart(cart);
                }

            }
        } catch (SQLException ex) {
            status = "Update Failed into cart!";
            System.out.println("Error occured in updateProductInCart method............................" + ex);
            ex.printStackTrace();
        } 
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public String updateProductInCart(UsercartPojo cart) {
        String status = "Failed to Add into cart!";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        int result = 0;
        try {
            ps1 = conn.prepareStatement("select * from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUserEmail());
            rs = ps1.executeQuery();
            if (rs.next()) {
                int qty = cart.getQuantity();
                if (cart.getQuantity() > 0) {
                    ps2 = conn.prepareStatement("update usercart set quantity=? where prodid=? and useremail=?");
                    ps2.setInt(1, qty);
                    ps2.setString(2, cart.getProdId());
                    ps2.setString(3, cart.getUserEmail());
                    result = ps2.executeUpdate();
                    if (result > 0) {
                        status = "Product Successfully Updated to Cart!";
                    }
                } else if (qty == 0) {
                    ps2 = conn.prepareStatement("delete from usercart where prodid=? and useremail=?");
                    ps2.setString(1, cart.getProdId());
                    ps2.setString(2, cart.getUserEmail());
                    result = ps2.executeUpdate();
                    if (result > 0) {
                        status = "Product Successfully Updated in Cart!";
                    }
                } else {
                    status = "Could not updated the product!";
                }
            } else {
                ps2 = conn.prepareStatement("insert into usercart values(?,?,?)");
                ps2.setString(1, cart.getUserEmail());
                ps2.setString(2, cart.getProdId());
                ps2.setInt(3, cart.getQuantity());
                result = ps2.executeUpdate();
                if (result > 0) {
                    status = "Product Successfully Added to Cart!";
                } else {
                    status = "Could not add the product";
                }
            }
        } catch (SQLException ex) {
            status = "Update Failed into cart!";
            System.out.println("Error occured in updateProductInCart method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public List<UsercartPojo> getAllCartItems(String userId) {
        List<UsercartPojo> cartItemList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from usercart where useremail=?");
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsercartPojo cartItems = new UsercartPojo();
                cartItems.setUserEmail(rs.getString("useremail"));
                cartItems.setProdId(rs.getString("prodid"));
                cartItems.setQuantity(rs.getInt("quantity"));
                cartItemList.add(cartItems);
            }
        } catch (SQLException ex) {
            System.out.println("Error occured in getAllCartItems method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return cartItemList;
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        int quantity = 0;
        if (userId == null || itemId == null) {
            return 0;
        }

        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, itemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error occured in getCartItemCount method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
        String status = "Product removal failed!";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        try {
            ps1 = conn.prepareStatement("select * from usercart where useremail=? and prodid=?");
            ps1.setString(1, userId);
            ps1.setString(2, prodId);
            rs = ps1.executeQuery();
            if (rs.next()) {
                int prodQuantity = rs.getInt("quantity");
                prodQuantity -= 1;
                if (prodQuantity > 0) {
                    ps2 = conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1, prodQuantity);
                    ps2.setString(2, userId);
                    ps2.setString(3, prodId);
                    int result = ps2.executeUpdate();
                    if (result > 0) {
                        status = "Product Successfully removed from the Cart!";
                    }
                } else if (prodQuantity == 0) {
                    ps2 = conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                    ps2.setString(1, userId);
                    ps2.setString(2, prodId);
                    int result = ps2.executeUpdate();
                    if (result > 0) {
                        status = "Product Successfully removed from the Cart!";
                    }
                }
            }
        } catch (SQLException ex) {
            status = "Update Failed into cart!";
            System.out.println("Error occured in updateProductInCart method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public Boolean removeAProduct(String userId, String prodId) {
        boolean status = false;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, prodId);
            int result = ps.executeUpdate();
            if (result > 0) {
                status = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error occured in getCartItemCount method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
}
