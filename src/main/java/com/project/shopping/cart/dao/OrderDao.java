package com.project.shopping.cart.dao;

import com.project.shopping.cart.model.Order;
import com.project.shopping.cart.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OrderDao {

    private Connection con;
    private String query;
    private PreparedStatement pst;
    private ResultSet rs;

    public OrderDao(Connection con) {
        this.con = con;
    }

    public boolean insertOrder(Order model) {

        boolean result = false;

        try {
            query = "INSERT INTO orders (p_id, u_id, o_quantity, o_date) VALUE (?, ?, ?, ?)";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getUid());
            pst.setInt(3, model.getQuantity());
            pst.setString(4, model.getDate());
            pst.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Order> userOrders(int id) {
        List<Order> list = new ArrayList<>();

        try {

            query = "SELECT * FROM orders WHERE u_id = ? ORDER BY orders.o_id DESC";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                ProductDao productDao = new ProductDao(this.con);

                int pId = rs.getInt("p_id");

                Product product = productDao.getSingleProduct(pId);
                order.setOrderId(rs.getInt("o_id"));
                order.setId(pId);
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setPrice(product.getPrice() * rs.getInt("o_quantity"));
                order.setQuantity(rs.getInt("o_quantity"));
                order.setDate(rs.getString("o_date"));

                list.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cancelOrder(int id) {

        try {

            query = "DELETE FROM orders WHERE o_id = ?";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
