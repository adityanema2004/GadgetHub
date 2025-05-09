/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.listener;

import in.gadgethub.utility.DBUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author LAPTOP
 */
@WebServlet(name = "DBConnectionListener", urlPatterns = {"/DBConnectionListener"})
public class DBConnectionListener  implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context initialized method called...");
        ServletContext ct=sce.getServletContext();
        String dbUrl=ct.getInitParameter("url");
        String dbUsername=ct.getInitParameter("username");
        String dbPassword=ct.getInitParameter("password");
        DBUtil.openConnection(dbUrl, dbUsername, dbPassword);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

}
