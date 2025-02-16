/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adity
 */
public class ProductDaoImpl implements ProductDao{

    @Override
    public String addProduct(ProductPojo product) {
        String status="Product Registration Failed";
        if(product.getProdId()==null){
            product.setProdId(IDutil.generateProdId());
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
                ps=conn.prepareStatement("insert into product values(?,?,?,?,?,?,?,?)");
                ps.setString(1,product.getProdId());
                ps.setString(2,product.getProdName());
                ps.setString(3,product.getProdType());
                ps.setString(4,product.getProdInfo());
                ps.setDouble(5,product.getProdPrice());
                ps.setInt(6,product.getProdQuantity());
                ps.setBlob(7,product.getProdImage());
                ps.setString(8,"Y");
                int count=ps.executeUpdate();
                if(count==1){
                    status="Product Added Successfully with id:"+product.getProdId();
                }
                 
            }
        
        catch(SQLException ex){
                System.out.println("Error in registerUser method!!"+ex);
                ex.printStackTrace();
            }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProduct(ProductPojo prevProduct, ProductPojo updatedProduct) {
        String status="Updation Failed";
        if(!prevProduct.getProdId().equals(updatedProduct.getProdId())){
            status="Product ID's Do not Match.Updation Failed";
            return status;
        }
        Connection conn =null;
        PreparedStatement ps=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setBlob(6, updatedProduct.getProdImage());
            ps.setString(7, updatedProduct.getProdId());
            int count=ps.executeUpdate();
            if(count==1){
                status="Product Updated Successfully";
            }
        }catch(SQLException ex){
            System.out.println("Error in updatedProduct method!!!"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProductPrice(String prodId, double updatedPrice) {
        String status="Updation Failed";
        Connection conn =null;
        PreparedStatement ps=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("update products set pprice=? where pid=?");
            ps.setDouble(1, updatedPrice);
            ps.setString(2, prodId);
            int count=ps.executeUpdate();
            if(count==1){
                status="Price Updated Successfully";
            }
        }catch(SQLException ex){
            status="Error"+ex.getMessage();
            System.out.println("Error in updatedProductPrice method!!!"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<ProductPojo> getAllProduct() {
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        try{
            conn=DBUtil.provideConnection();
            st=conn.prepareStatement("select * from products where available = 'Y'");
            rs=st.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getAllProduct method!!!"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return productList;
    }

    @Override
    public List<ProductPojo> getAllProductByType(String Type) {
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Type=Type.toLowerCase();
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select * from products where lower(ptype) like ?");
            ps.setString(1,"%"+Type+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getAllProductByType method!!!"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return productList;
    }

    @Override
    public List<ProductPojo> searchAllProduct(String search) {
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        search=search.toLowerCase();
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select * from products where lower(ptype) like ? or lower(pname) like ? or lower(pinfo) like ?");
            ps.setString(1,"%"+search+"%");
            ps.setString(2,"%"+search+"%");
            ps.setString(3,"%"+search+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);    
            }
        }catch(SQLException ex){
            System.out.println("Error in searchAllProduct method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }

    @Override
    public ProductPojo getProductDetails(String prodId) {
        ProductPojo product=null;
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select * from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));    
            }
        }catch(SQLException ex){
            System.out.println("Error in getProductDetails method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return product;
    }

    @Override
    public int getProductQuantity(String prodId) {
        int quantity=0;
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select pquantity from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
               quantity=rs.getInt(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getProductQuantity method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public String updateProductWithoutImage(String prevProductId, ProductPojo updatedProduct) {
        String status="Updation Failed";
        int prevQuantity=0;
        if(!prevProductId.equals(updatedProduct.getProdId())){
            status="Product ID's Do not Match.Updation Failed";
            return status;
        }
        Connection conn =null;
        PreparedStatement ps=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setString(6, updatedProduct.getProdId());
            int count=ps.executeUpdate();
            if(count==1 && prevQuantity<updatedProduct.getProdQuantity()){
                status="Product Updated Successfully and Mail Sent";
//              Code to Sent the mail
            }
            else if(count==1){
                status="Product Updated Successfully";
            }
        }catch(SQLException ex){
            System.out.println("Error in updatedProductWithoutImage method!!!"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public double getProductPrice(String prodId) {
        double price=0;
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select pprice from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
               price=rs.getDouble(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getProductPrice method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return price;
    }

    @Override
    public boolean sellNProduct(String prodId, int n) {
        boolean result=false;
        Connection conn=null;
        PreparedStatement ps=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("update products set pquantity=(pquantity-?) where pid=?");
            ps.setInt(1,n);
            ps.setString(2,prodId);
            int count=ps.executeUpdate();
            if(count==1){
                result=true;
            }
        }catch(SQLException ex){
            System.out.println("Error in sellNProduct method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }

    @Override
    public List<String> getAllProductsType() {
        List<String> productsTypeList=new ArrayList<>();
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        try{
            conn=DBUtil.provideConnection();
            st=conn.createStatement();
            rs=st.executeQuery("select ptype from products");
            while(rs.next()){
                productsTypeList.add(rs.getString(1));
            }
            
        }catch(SQLException ex){
            System.out.println("Error in getAllProductByType method!!!"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return productsTypeList;
    }

    @Override
    public byte[] getImage(String prodId) {
        byte[] arr=null;
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            conn=DBUtil.provideConnection();
            ps=conn.prepareStatement("select image from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
               arr=rs.getBytes(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getProductQuantity method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return arr;
    }

    @Override
    public String removeProduct(String prodId) {
        String status=" Product Not Found";
        Connection conn=null;
        PreparedStatement ps1=null,ps2=null;
        try{
            conn=DBUtil.provideConnection();
            ps1=conn.prepareStatement("update products set available='N' where pid=? and available='Y'");
            ps1.setString(1,prodId);
            int count=ps1.executeUpdate();
            if(count>1){
                status="Product has been Removed";
                ps2=conn.prepareStatement("delete from usercart where prodid=?");
                ps2.setString(1,prodId);
                count=ps2.executeUpdate();
            }
        }catch(SQLException ex){
            status="Error"+ex.getMessage();
            System.out.println("Error in removeProduct method!!! "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }
    
}
