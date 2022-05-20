package com.project.shopping.cart.dao;

import com.project.shopping.cart.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
