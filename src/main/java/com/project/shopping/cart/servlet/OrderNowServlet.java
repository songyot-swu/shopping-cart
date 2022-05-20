package com.project.shopping.cart.servlet;

import com.project.shopping.cart.connection.DbCon;
import com.project.shopping.cart.dao.OrderDao;
import com.project.shopping.cart.model.Cart;
import com.project.shopping.cart.model.Order;
import com.project.shopping.cart.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderNowServlet", urlPatterns = {"/order-now"})
public class OrderNowServlet extends HttpServlet {

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet OrderNowServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet OrderNowServlet at " + request.getContextPath() + "</h1>");
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date date = new Date();

            User auth = (User) request.getSession().getAttribute("auth");

            if (auth != null) {

                String productId = request.getParameter("id");

                int productQuantity = Integer.parseInt(request.getParameter("quantity"));

                if (productQuantity <= 0) {
                    productQuantity = 1;
                }

                Order orderModel = new Order();
                orderModel.setId(Integer.parseInt(productId));
                orderModel.setUid(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));

                OrderDao orderDao = new OrderDao(DbCon.getConnection());
                boolean result = orderDao.insertOrder(orderModel);

                if (result) {

                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");
                    if (cart_list != null) {
                        for (Cart c : cart_list) {
                            if (c.getId() == Integer.parseInt(productId)) {
                                cart_list.remove(cart_list.indexOf(c));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("orders.jsp");
                } else {
                    out.println("Order failed !");
                }
            } else {
                response.sendRedirect("login.jsp");
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
