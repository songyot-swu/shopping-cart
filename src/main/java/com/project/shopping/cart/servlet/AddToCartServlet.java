package com.project.shopping.cart.servlet;

import com.project.shopping.cart.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddToCartServlet", urlPatterns = {"/add-to-cart"})
public class AddToCartServlet extends HttpServlet {

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AddToCartServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AddToCartServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            //สร้าง ArrayList<Cart> ชื่อ "cartList"
            ArrayList<Cart> cartList = new ArrayList<Cart>();

            //สร้างตัวแปร int ชื่อ "id" เพื่อรับค่าจาก parameter id ที่ส่งมาทาง request
            int id = Integer.parseInt(request.getParameter("id"));

            //สร่้าง Object Cart ชื่อ "cm" แล้วทำการ set ค่าให้กับ id และ quantity
            Cart cm = new Cart();
            cm.setId(id);
            cm.setQuantity(1);

            //สร้างตัวแปร HttpSession ชื่อ "session" เก็บค่า session
            HttpSession session = request.getSession();

            //สร่้าง ArrayList<Cart> ชื่อ "cart_list" กำหนดให้มีค่าเท่ากับ session attribute "cart_list"
            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");

            //ตรวจสอบค่าของ "cart_list" ว่าเป็น null หรือไม่ ถ้าเป็น null แสดงว่ายังไม่มีการสร้าง session
            if (cart_list == null) {

                //ให้ทำการเพิ่มข้อมูลจาก cm เข้าไปใน "cartList"
                cartList.add(cm);

                //กำหนดค่า attribute ให้กับ session ชื่อ "cart_list" มีค่าตาม "cartList"
                session.setAttribute("cart_list", cartList);
                response.sendRedirect("index.jsp");
            } else {

                //กรณีที่ session ถูกสร้างขึ้นมาแล้ว กำหนดให้ "cartList" มีค่าเท่ากับ "cart_list"
                cartList = cart_list;

                //สร้างตัวแปร boolean ชื่อว่า "exist" มีค่าเป็น false
                boolean exist = false;

                //ลูปข้อมูลจาก  "cart_list"
                for (Cart c : cart_list) {

                    //ถ้าข้อมูล id ใน cart_list เท่ากับค่าจาก id ที่รับจาก request
                    if (c.getId() == id) {

                        //เปลี่ยนค่า "exist" เป็น true
                        exist = true;
                        out.println("<h3 style='color:crimson; text-align:center'>Item already exist in Cart. <a href='cart.jsp'>Go to Cart Page</a></h3>");
                    }
                }

                //ถ้า "exist" มีค่าเป็น true (มีการสร้าง session แต่ค่า id ใน cart_list ไม่ตรงกับ id ที่รับมาทาง request)
                if (!exist) {

                    //เพิ่มข้อมูล id และ quantity เข้าไปใน "cartList"
                    cartList.add(cm);
                    response.sendRedirect("index.jsp");
                }
            }

//ทดสอบแสดงผลค่า id จาก session catr_list
//            for (Cart c : cart_list) {
//                out.println(c.getId());
//            }
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
}
