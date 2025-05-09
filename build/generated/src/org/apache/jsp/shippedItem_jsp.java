package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import in.gadgethub.dao.impl.*;
import java.util.List;
import in.gadgethub.dao.*;
import in.gadgethub.pojo.*;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.*;

public final class shippedItem_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!doctype html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("  <head>\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("    <title>Bootstrap demo</title>\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\n");
      out.write("  </head>\n");
      out.write("  <body>\n");
      out.write("      ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("    <div class=\"container-fluid\">\n");
      out.write("        <div class=\"h3 text-center text-primary mt-3 mb-3\">Shipped Orders</div>\n");
      out.write("        <div class=\"table-responsive\">\n");
      out.write("            <table class=\"table table-hover\">\n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Transction Id</th>\n");
      out.write("                        <th>Product Id</th>\n");
      out.write("                        <th> username</th>\n");
      out.write("                        <th>Address</th>\n");
      out.write("                        <th>Qty</th>\n");
      out.write("                         <th>Amount</th>\n");
      out.write("                         <th>Status</th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                     ");

                            List<OrderDetailsPojo> orders = (List<OrderDetailsPojo>) request.getAttribute("orders");
                            TransactionDao transactionDao = new TransactionDaoImpl();
                            OrderDaoImpl orderDao=new OrderDaoImpl();
                            UserPojo user=new UserPojo();
                            for (OrderDetailsPojo order : orders) {
                        
      out.write("\n");
      out.write("                    <tr>\n");
      out.write("                        <td>\n");
      out.write("                            ");
      out.print(transactionDao.getUserId(order.getOrderId()));
      out.write("\n");
      out.write("                        </td>\n");
      out.write("                        <td><a\n");
      out.write("                               href=\"updateProduct.jsp?prodid=");
      out.print(order.getProdId());
      out.write('"');
      out.write('>');
      out.print(order.getProdId());
      out.write("</a>\n");
      out.write("                        </td>\n");
      out.write("                        ");

                            
                                    String name = order.getProdName();
                                    name = name.substring(0, Math.min(name.length(), 25));
                        
      out.write("\n");
      out.write("                        <td>");
      out.print(user.getUserEmail());
      out.write("</td>\n");
      out.write("                        <td>");
      out.print(user.getAddress());
      out.write("</td>\n");
      out.write("                        <td>");
      out.print(orderDao.getSoldQuantity(order.getProdId()));
      out.write("</td>\n");
      out.write("                        <td>");
      out.print(order.getAmount());
      out.write("</td>\n");
      out.write("                        <td class=\"text-primary\">");
      out.print(order.getShipped());
      out.write("</td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td colspan=\"7\" class=\"text-center\">\n");
      out.write("                            No Items avaliable\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>\n");
      out.write("  </body>\n");
      out.write("</html> ");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
