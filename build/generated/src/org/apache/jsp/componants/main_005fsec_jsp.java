package org.apache.jsp.componants;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_005fsec_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("  <head>\n");
      out.write("    <meta charset=\"utf-8\" />\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n");
      out.write("    <title>Bootstrap demo</title>\n");
      out.write("    <link\n");
      out.write("      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\"\n");
      out.write("      rel=\"stylesheet\"\n");
      out.write("      integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\"\n");
      out.write("      crossorigin=\"anonymous\"\n");
      out.write("    />\n");
      out.write("    <style>\n");
      out.write("      .row card {\n");
      out.write("        box-shadow: rgba(0, 0, 0, 0.16) 0px 10px 36px 0px,\n");
      out.write("          rgba(0, 0, 0, 0.06) 0px 0px 0px 1px;\n");
      out.write("      }\n");
      out.write("      .card {\n");
      out.write("        background-color: #effafb;\n");
      out.write("      }\n");
      out.write("      h6{\n");
      out.write("        font-weight: bolder;\n");
      out.write("        color:darkblue\n");
      out.write("      }\n");
      out.write("      .card-body h6{\n");
      out.write("        color:blue;\n");
      out.write("      }\n");
      out.write("    </style>\n");
      out.write("  </head>\n");
      out.write("  <body style=\"background-color: #effafb\">\n");
      out.write("    <div class=\"container w-100\">\n");
      out.write("      <p class=\"h2 text-center text-primary\">All Products</p>\n");
      out.write("      <div class=\"row\">\n");
      out.write("        <div class=\"col-md-4\">\n");
      out.write("          <div class=\"card mt-2\">\n");
      out.write("            <img\n");
      out.write("              src=\"../images/desktop_image.jpg\"\n");
      out.write("              alt=\"\"\n");
      out.write("              class=\"card-img-top\"\n");
      out.write("              height=\"300px\"\n");
      out.write("            />\n");
      out.write("            <div class=\"card-body\">\n");
      out.write("              <h6 class=\"card-title text-center\">\n");
      out.write("                APPLE iPhone 13 Pro (Graphite, 512GB)(P20230423082243)\n");
      out.write("              </h6>\n");
      out.write("              <p class=\"cart-text\">\n");
      out.write("                iPhone 13, boasts an advanced dual-camra system that allows you\n");
      out.write("                to click mesmerising pictures with immaculte clarity.\n");
      out.write("              </p>\n");
      out.write("              <h2 class=\"text-danger text-center\">Rs 125999.0</h2>\n");
      out.write("              <div class=\"text-center\">\n");
      out.write("                <button class=\"btn btn-danger\">Remove Product</button>\n");
      out.write("                <button class=\"btn btn-primary\">Update Product</button>\n");
      out.write("              </div>\n");
      out.write("            </div>\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"col-md-4\">\n");
      out.write("          <div class=\"card mt-2\">\n");
      out.write("            <img\n");
      out.write("              src=\"../images/hp-intel-core-i5-11th-gen.avif\"\n");
      out.write("              alt=\"\"\n");
      out.write("              class=\"card-img-top p-3 pt-3 pb-3\"\n");
      out.write("            />\n");
      out.write("            <div class=\"card-body\">\n");
      out.write("              <h6 class=\"card-title text-center\">\n");
      out.write("                HP Intel Core i5 11th Gen (P20230423083830)\n");
      out.write("              </h6>\n");
      out.write("              <p class=\"cart-text\">\n");
      out.write("                iPhone 13, boasts an advanced dual-camra system that allows you\n");
      out.write("                to click mesmerising pictures with immaculte clarity.\n");
      out.write("              </p>\n");
      out.write("              <h2 class=\"text-danger text-center\">Rs 125999.0</h2>\n");
      out.write("              <div class=\"text-center\">\n");
      out.write("                <button class=\"btn btn-danger\">Remove Product</button>\n");
      out.write("                <button class=\"btn btn-primary\">Update Product</button>\n");
      out.write("              </div>\n");
      out.write("            </div>\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"col-md-4\">\n");
      out.write("          <div class=\"card mt-2\">\n");
      out.write("            <img\n");
      out.write("              src=\"../images/led-smart-google-tv.webp\"\n");
      out.write("              alt=\"\"\n");
      out.write("              class=\"card-img-top pt-5 p-3\"\n");
      out.write("            />\n");
      out.write("            <div class=\"card-body\">\n");
      out.write("              <h6 class=\"card-title text-center\">\n");
      out.write("               LED Smart Google TV (P202304423084143)\n");
      out.write("              </h6>\n");
      out.write("              <p class=\"cart-text\">\n");
      out.write("                iPhone 13, boasts an advanced dual-camra system that allows you\n");
      out.write("                to click mesmerising pictures with immaculte clarity.\n");
      out.write("              </p>\n");
      out.write("              <h2 class=\"text-danger text-center\">Rs 125999.0</h2>\n");
      out.write("              <div class=\"text-center\">\n");
      out.write("                <button class=\"btn btn-danger\">Remove Product</button>\n");
      out.write("                <button class=\"btn btn-primary\">Update Product</button>\n");
      out.write("              </div>\n");
      out.write("            </div>\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("      </div>\n");
      out.write("    </div>\n");
      out.write("    <script\n");
      out.write("      src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"\n");
      out.write("      integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\"\n");
      out.write("      crossorigin=\"anonymous\"\n");
      out.write("    ></script>\n");
      out.write("  </body>\n");
      out.write("</html>\n");
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
