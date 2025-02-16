/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
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
public class DemandDaoImpl implements DemandDao{

    @Override
    public boolean addProduct(DemandPojo demandPojo) {
        boolean status=false;
        String updateSQL="Update userdemand set quantity=quantity+? where useremail=? and prodId=?";
        String insertSQL="Insert into userdemand values(?,?,?)";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        try{
            ps1=conn.prepareStatement(updateSQL);
            ps1.setInt(1,demandPojo.getDemandQuantity());
            ps1.setString(2,demandPojo.getUseremail());
            ps1.setString(3,demandPojo.getProdId());
            int count=ps1.executeUpdate();
            if(count==0){
                ps2=conn.prepareStatement(insertSQL);
                ps2.setString(1,demandPojo.getUseremail());
                ps2.setString(2,demandPojo.getProdId());
                ps2.setInt(3,demandPojo.getDemandQuantity());
                ps2.executeUpdate();
            }
            status=true;
        }catch(SQLException ex){
            System.out.println("Error in addProduct method!!!  in DemandDaoImpl"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public boolean removeProduct(String userId, String prodId) {
        boolean result=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("delete from userdemand where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2,prodId);
            result=ps.executeUpdate()>0;
        }catch(SQLException ex){
            System.out.println("Error in removeProduct method!!!  in DemandDaoImpl"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }

    @Override
    public List<DemandPojo> haveDemanded(String prodId) {
        List<DemandPojo> demandList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from userdemand where prodid=?");
            ps.setString(1, prodId);
            rs=ps.executeQuery();
            while(rs.next()){
                DemandPojo demandPojo=new DemandPojo();
                demandPojo.setUseremail(rs.getString("useremail"));
                demandPojo.setProdId(rs.getString("prodid"));
                demandPojo.setDemandQuantity(rs.getInt("quantity"));
                demandList.add(demandPojo);
            }
        }catch(SQLException ex){
            System.out.println("Error in haveDemanded method!!!  in DemandDaoImpl"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return demandList;
    }
    
}
