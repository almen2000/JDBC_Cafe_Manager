package com.cafe.management;

import com.cafe.connection.Connect;
import com.cafe.types.Gender;
import com.cafe.types.Status;
import com.cafe.types.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Waiter extends User {
    private final UUID waiterId;

    private Waiter(UUID waiterId, UUID userId, String first_name, String last_name, String username, String password_hash, String email, Gender gender, UserType type) {
        super(userId, first_name, last_name, username, password_hash, email, gender, type);
        this.waiterId = waiterId;
    }

    @Override
    public String toString() {
        return "Waiter{" +
                "waiterId=" + waiterId +
                ", userId=" + userId +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", type=" + type +
                '}';
    }

    public static Waiter getWaiterByUsername(String username) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id  as user_id,\n" +
                    "       waiter.id as waiter_id,\n" +
                    "       first_name,\n" +
                    "       last_name,\n" +
                    "       username,\n" +
                    "       pass_hash,\n" +
                    "       email,\n" +
                    "       gender,\n" +
                    "       type\n" +
                    "from \"user\"\n" +
                    "inner join waiter on \"user\".id = waiter.user_id\n" +
                    "where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new RuntimeException("There is no waiter with this username");

            return new Waiter(
                    UUID.fromString(resultSet.getString("waiter_id")),
                    UUID.fromString(resultSet.getString("user_id")),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("username"),
                    resultSet.getString("pass_hash"),
                    resultSet.getString("email"),
                    Gender.getEnumByValue(resultSet.getString("gender")),
                    UserType.getEnumByValue(resultSet.getString("type"))
            );

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void assignedTables() {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select id\n" +
                    "from \"table\"\n" +
                    "where waiter_id = ?;");
            preparedStatement.setObject(1, waiterId);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Tables are:");
            while (resultSet.next()) {
                System.out.println("           " + resultSet.getString("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createOrderForTable(UUID tableId) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select count(*) as count\n" +
                    "from \"order\"\n" +
                    "where table_id = ? and status = 'open'\n" +
                    "limit 1;");
            preparedStatement.setObject(1, tableId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt("count") != 0) {
                System.out.println("order for this table is still open");
                return;
            }
            preparedStatement = connection.prepareStatement("" +
                    "insert into \"order\"(table_id, status)\n" +
                    "values (?, 'open');");
            preparedStatement.setObject(1, tableId);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Order created successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createProductInOrder(UUID productId, UUID orderId, int amount) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into product_in_order(product_id, order_id, amount)\n" +
                    "values (?, ?, ?);");
            preparedStatement.setObject(1, productId);
            preparedStatement.setObject(2, orderId);
            preparedStatement.setInt(3, amount);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Product in Order created Successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editProductInOrderAmount(UUID productId, UUID orderId, int newAmount) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "update product_in_order\n" +
                    "set amount = ?, updated = ?\n" +
                    "where product_id = ? and order_id = ?;");
            preparedStatement.setInt(1, newAmount);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            preparedStatement.setObject(3, productId);
            preparedStatement.setObject(4, orderId);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Product in Order updated Successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editOrderStatus(UUID orderId, Status status) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "update \"order\"\n" +
                    "set status = ?, updated = ?\n" +
                    "where id = ?");
            preparedStatement.setString(1, status.getStatus());
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            preparedStatement.setObject(3, orderId);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Order updated Successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
