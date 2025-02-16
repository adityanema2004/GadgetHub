/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adity
 */
public class CartDaoImpl implements CartDao{

    @Override
    public String addProductToCart(CartPojo cart) {
        String status="Failed to addProduct to Cart";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodId=? and useremail=? ");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                ProductDaoImpl prodDao=new ProductDaoImpl();
                int stockQty=prodDao.getProductQuantity(cart.getProdId());
                int newQty=cart.getQuantity()+rs.getInt("quantity");
                if(stockQty<newQty){
                    cart.setQuantity(newQty);
                    updateProductInCart(cart);
                    status="Only"+stockQty+"no. of Items are available in our stock so we are adding"+stockQty+"in your cart";
                    DemandPojo demandPojo=new DemandPojo();
                    demandPojo.setProdId(cart.getProdId());
                    demandPojo.setUseremail(cart.getUseremail());
                    demandPojo.setDemandQuantity(newQty-stockQty);
                    DemandDaoImpl demandDao= new DemandDaoImpl();
                    boolean result=demandDao.addProduct(demandPojo);
                    if(result==true){
                        status+="We will Mail you when"+(newQty-stockQty)+"no of items will be available";
                    }
                }else{
                    cart.setQuantity(newQty);
                    status=updateProductInCart(cart);
                    
                }
            }
        }
        catch(SQLException ex){
            status="Addition failed due to exception";
            System.out.println("Error in addProductToCart method!!!  in CartDaoImpl"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public String updateProductInCart(CartPojo cart) {
        String status="Failed to add to cart!";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        int ans=0;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodId=? and useremail=? ");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                int qty=cart.getQuantity();
                if(qty>0){
                    ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1,cart.getQuantity());
                    ps2.setString(2,cart.getUseremail());
                    ps2.setString(3,cart.getProdId());
                    ans=ps2.executeUpdate();
                    if(ans>0){
                        status="Product successfully updated in the  cart";
                    } 
                }
                else if(qty==0){
                    ps2=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                    ps2.setString(1,cart.getUseremail());
                    ps2.setString(2,cart.getProdId());
                    ps2.setInt(3,cart.getQuantity());
                    ans=ps2.executeUpdate();
                    if(ans>0){
                        status="Product successfully updated in the  cart";
                    }else{
                        status="Could Not Update the product";
                    }
                }
            }else{
                ps2=conn.prepareStatement("insert into usercart values(?,?,?)");
                ps2.setString(1,cart.getUseremail());
                ps2.setString(2,cart.getProdId());
                ps2.setInt(3,cart.getQuantity());
                ans=ps2.executeUpdate();
                if(ans>0){
                    status="Product successfully added into cart";
                }
            }
        }catch(SQLException ex){
            status="Updation failed due to exception";
            System.out.println("Error in updateProductInCart method!!!  in CartDaoImpl"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;
        
    }

    @Override
    public List<CartPojo> getAllCartItems(String userId) {
        List<CartPojo> items=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from usercart where useremail=?");
            ps.setString(1, userId);
            rs=ps.executeQuery();
            while(rs.next()){
                CartPojo cart =new CartPojo();
                cart.setUseremail(rs.getString("useremail"));
                cart.setProdId(rs.getString("prodid"));
                cart.setQuantity(rs.getInt("quantity"));
                items.add(cart);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllCartItems method!!!  in CartDaoImpl"+ex);
            ex.printStackTrace();
    }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return items;
        
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if(userId==null || itemId==null){
            return 0;
        }
        int count=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2, itemId);
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getCartItemCount method in CartDaoImpl"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return count;
    }
        

    @Override
    public String removeProductFromCart(String userId, String prodId) {
        String status="Removal of  product from cart is failed!";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from usercart where prodid=? and useremail=? ");
            ps1.setString(1, prodId);
            ps1.setString(2, userId);
            rs=ps1.executeQuery();
            if(rs.next()){
                int prodQuantity=rs.getInt(1);
                prodQuantity-=1;
                if(prodQuantity!=0){
                    ps2=conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1, prodQuantity);
                    ps2.setString(2,userId);
                    ps2.setString(3, prodId);
                    int k=ps2.executeUpdate();
                    if(k>0){
                        status="Product Successfully removed from cart";
                    }
                    
                }else{
                    ps2=conn.prepareStatement("delete from  usercart where useremail=? and prodid=?");
                    ps2.setString(1,userId);
                    ps2.setString(2, prodId);
                    int k=ps2.executeUpdate();
                    if(k>0){
                        status="Product successfully removed from cart";
                    }
                }
            }
        }catch(SQLException ex){
            System.out.println("Error in removeProductFromCart method!!!  in CartDaoImpl"+ex);
            ex.printStackTrace();
    }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public boolean removeAProduct(String userId, String prodId) {
       boolean status=false;
       Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
           ps.setString(1,userId);
           ps.setString(2,prodId);
           int k=ps.executeUpdate();
           if(k>0){
               status=true;
           }
       }
       catch(SQLException ex){
           System.out.println("Error in removeAProduct method!!!  in CartDaoImpl"+ex);
           ex.printStackTrace();
       }
       DBUtil.closeStatement(ps);
       return status;
    }
    
}
