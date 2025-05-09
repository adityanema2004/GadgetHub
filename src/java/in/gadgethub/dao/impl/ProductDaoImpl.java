package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
import in.gadgethub.dao.OrderDao;
import in.gadgethub.dao.ProductDao;
import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDUtil;
import in.gadgethub.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

public class ProductDaoImpl implements ProductDao {

    @Override
    public String addProduct(ProductPojo product) {
        String status = "Product Registration Failed";
        if (product.getProdId() == null) {
            product.setProdId(IDUtil.generateProdId());
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into products values(?,?,?,?,?,?,?,?)");
            ps.setString(1, product.getProdId());
            ps.setString(2, product.getProdName());
            ps.setString(3, product.getProdType());
            ps.setString(4, product.getProdInfo());
            ps.setDouble(5, product.getProdPrice());
            ps.setInt(6, product.getProdQuantity());
            ps.setBlob(7, product.getProdImage());
            ps.setString(8, "Y");
            int count = ps.executeUpdate();
            if (count == 1) {
                status = "Product Added Successfully with Id:" + product.getProdId();
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in addProduct method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;

    }

    @Override
    public String updateProduct(ProductPojo preProduct, ProductPojo updateProduct) {
        String status = "Updation Failed";
        PreparedStatement ps = null;
        if (!preProduct.getProdId().equals(updateProduct.getProdId())) {
            status = "Product ID's Do Not Match. Updation Failed";
            return status;
        }
        Connection conn = DBUtil.provideConnection();
        try {
            ps = conn.prepareStatement("update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?");
            ps.setString(1, updateProduct.getProdName());
            ps.setString(2, updateProduct.getProdInfo());
            ps.setString(3, updateProduct.getProdType());
            ps.setDouble(4, updateProduct.getProdPrice());
            ps.setInt(5, updateProduct.getProdQuantity());
            ps.setBlob(6, updateProduct.getProdImage());
            ps.setString(7, updateProduct.getProdId());
            int count = ps.executeUpdate();
            if (count == 1) {
                status = "Product Updated Successfully";
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in updateProduct  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProductPrice(String prodId, double updatePrice) {
        String status = "Price Updation Failed";
        PreparedStatement ps = null;
        Connection conn = DBUtil.provideConnection();
        try {
            ps = conn.prepareStatement("update products set pprice=? where pid=?");
            ps.setDouble(1, updatePrice);
            ps.setString(2, prodId);
            int count = ps.executeUpdate();
            if (count == 1) {
                status = "Product Price Updated Successfully";
            }
        } catch (SQLException ex) {
            status = "Error! :" + ex.getMessage();
            System.out.println("Exception occured in updateProduct  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<ProductPojo> getAllProducts() {
        List<ProductPojo> productList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from products where available='Y'");
            while (rs.next()) {
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getAllProducts  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(st);
        return productList;
    }

    @Override
    public List<ProductPojo> getAllProductByType(String type) {
        List<ProductPojo> productList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductPojo product = null;
        type = type.toLowerCase();
        try {
            ps = conn.prepareStatement("select * from products where lower(ptype) like ? and available='Y'");
            ps.setString(1, "%" + type + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                product.setProdType(rs.getString("ptype"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getAllProductByType  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }

    @Override
    public List<ProductPojo> searchAllProducts(String searchTerm) {
        List<ProductPojo> productList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        searchTerm = searchTerm.toLowerCase();
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("Select * from products where lower(ptype) like ? or lower(pname) like ? or lower(pinfo) like ? and available='Y'");
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, "%" + searchTerm + "%");
            ps.setString(3, "%" + searchTerm + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }

        } catch (SQLException ex) {

            System.out.println("Error  in searchAllProducts" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }

    @Override
    public ProductPojo getProductDetails(String prodId) {
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductPojo product = null;
        try {
            ps = conn.prepareStatement("select * from products where pid=? and available='Y'");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getProductDetails  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return product;
    }

    @Override
    public int getProductQuantity(String prodId) {
        int quantity = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = DBUtil.provideConnection();
        try {
            ps = conn.prepareStatement("select pquantity from products where pid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getProductQuantity method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public String updateProductWithoutImage(String prevProdId, ProductPojo updatedProduct) {
        String status = "Updation Failed!";
        int prevQuantity = 0;
        if (!prevProdId.equals(updatedProduct.getProdId())) {
            status = "Product ID's Do Not Match. Updation Failed";
            return status;
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            prevQuantity = getProductQuantity(prevProdId);
            ps = conn.prepareStatement("Update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setString(6, updatedProduct.getProdId());
            int count = ps.executeUpdate();
            if (count == 1 && prevQuantity < updatedProduct.getProdQuantity()) {
                status = "Product Updated Successfully And Mail Sent";
                //code for sending mail    
                DemandDao demandDao = new DemandDaoImpl();
                List<DemandPojo> demands = demandDao.haveDemanded(prevProdId);
                for (DemandPojo demand : demands) {
                    String userEmail = demand.getUserEmail();
                    String prodId = demand.getProdId();
                    int qty = demand.getdemandQuantity();
                    UserDao userDao = new UserDaoImpl();
                    String userName = userDao.getUserFirstName(userEmail);
                    if (qty <= updatedProduct.getProdQuantity()) {
                        MailMessage.sendDemandFulfilledEmail(userEmail, userName, prodId,updatedProduct.getProdName(),qty,updatedProduct.getProdQuantity());
                        System.out.println("Mail sent successfully");
                        boolean result=demandDao.removeProduct(userEmail, prodId);
                        System.out.println(result);
                    }
                }
            } else if (count == 1) {
                status = "Product Updated Successfully";
            }
        } catch (SQLException ex) {
            System.out.println("Error in updateProduct:" + ex);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Exception occured in registerUser method............................");
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public double getProductPrice(String prodId) {
        double price = 0.0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = DBUtil.provideConnection();
        try {
            ps = conn.prepareStatement("select pprice from products where pid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getProductPrice  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeStatement(ps);
        return price;
    }

    @Override
    public Boolean sellNProduct(String prodId, int n) {
        boolean result = false;
        PreparedStatement ps = null;
        Connection conn = DBUtil.provideConnection();
        try {
            ps = conn.prepareStatement("update products set pquantity=(pquantity-?) where pid=? and available='Y'");
            ps.setInt(1, n);
            ps.setString(2, prodId);
            int count = ps.executeUpdate();
            if (count == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in sellNProduct  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }

    @Override
    public List<String> getAllProductsType() {
        List<String> types = new ArrayList<>();
        Statement st = null;
        Connection conn = DBUtil.provideConnection();
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select distinct ptype from products where available='Y'");
            while (rs.next()) {
                types.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getAllProducts  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return types;
    }

    @Override
    public byte[] getImage(String prodId) {
        byte[] image = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = DBUtil.provideConnection();
        try {
            ps = conn.prepareStatement("select image from products where pid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            while (rs.next()) {
                image = rs.getBytes(1);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in getImage  method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return image;
    }

    @Override
    public String removeProduct(String prodId) {
        String status = "Product Not Found!";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            ps1 = conn.prepareStatement("update products set available='N' where pid=? and available='Y'");
            ps1.setString(1, prodId);
            int count1 = ps1.executeUpdate();
            if (count1 > 0) {
                status = "Product Removed Successfully";
                ps2 = conn.prepareStatement("delete from usercart where prodid=?");
                ps2.setString(1, prodId);
                int count2 = ps2.executeUpdate();
            }
        } catch (SQLException ex) {
            status = "Error:" + ex.getMessage();
            System.out.println("Exception occured in removeProduct method............................" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }
}
