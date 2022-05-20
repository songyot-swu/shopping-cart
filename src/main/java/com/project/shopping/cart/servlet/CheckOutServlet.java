package com.project.shopping.cart.servlet;

import com.project.shopping.cart.connection.DbCon;
import com.project.shopping.cart.dao.OrderDao;
import com.project.shopping.cart.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/cart-check-out"})
public class CheckOutServlet extends HttpServlet {

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet CheckOutServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet CheckOutServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date date = new Date();

            //retrive all cart product
            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");

            //user authentication
            User auth = (User) request.getSession().getAttribute("auth");

            //check auth and cart list
            if (cart_list != null && auth != null) {

                for (Cart c : cart_list) {

                    //prepare the order object
                    Order order = new Order();
                    order.setId(c.getId());
                    order.setUid(auth.getId());
                    order.setQuantity(c.getQuantity());
                    order.setDate(formatter.format(date));

                    //instantiate the DAO class
                    OrderDao oDao = new OrderDao(DbCon.getConnection());

                    //calling the insert method
                    boolean result = oDao.insertOrder(order);

                    if (!result) {
                        break;
                    }
                }

                //clear all item from cart
                cart_list.clear();
                response.sendRedirect("orders.jsp");

            } else {
                if (auth == null) {
                    response.sendRedirect("login.jsp");
                }
                response.sendRedirect("cart.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
}
