package com.project.shopping.cart.servlet;

import com.project.shopping.cart.model.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "QuantityIncDecServlet", urlPatterns = {"/quantity-Inc-dec"})
public class QuantityIncDecServlet extends HttpServlet {

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet QuantityIncDecServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet QuantityIncDecServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String action = request.getParameter("action");
            int id = Integer.parseInt(request.getParameter("id"));

            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");

            if (action != null && id >= 1) {
                if (action.equals("inc")) {
                    for (Cart c : cart_list) {
                        if (c.getId() == id) {
                            int quantity = c.getQuantity();
                            quantity++;
                            c.setQuantity(quantity);
                            break;
                        }
                    }
                    response.sendRedirect("cart.jsp");
                }

                if (action.equals("dec")) {
                    for (Cart c : cart_list) {
                        if (c.getId() == id && c.getQuantity() > 1) {
                            int quantity = c.getQuantity();
                            quantity--;
                            c.setQuantity(quantity);
                            break;
                        }
                    }
                    response.sendRedirect("cart.jsp");
                }
            } else {
                response.sendRedirect("cart.jsp");
            }
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
}
