package in.gadgethub.servlet;

import in.gadgethub.dao.UserDao;
import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String education = request.getParameter("mobile");
        String pcode = request.getParameter("pincode");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");
        String message = "";
        if (email.isEmpty() || name.isEmpty() || mobile.isEmpty() || address.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
            message = "All field are mendatory required fill!";
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + message);
            rd.include(request, response);
        }
        if (!password.equals(cpassword)) {
            message = "password should be same here!";
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + message);
            rd.include(request, response);
        }
        int pincode=0;
        try{
            pincode=Integer.parseInt(pcode);
        }catch(NumberFormatException ex){
            message = "please pass integer value!";
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + message);
            rd.include(request, response);
        }
        UserPojo userPojo = new UserPojo(email, name, mobile, address, pincode, password);
        UserDao userDao = new UserDaoImpl();
        String status = userDao.registerUser(userPojo);
        RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" +status);
//        request.setAttribute("message", status);
        rd.include(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
