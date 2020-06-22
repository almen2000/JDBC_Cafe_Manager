package com.cafe.management;

import com.cafe.connection.Connect;
import com.cafe.types.Gender;
import com.cafe.types.UserType;

import java.sql.*;
import java.util.UUID;

public class Manager extends User {
    private final UUID managerId;

    private Manager(UUID managerId, UUID userId, String first_name, String last_name, String username, String password_hash, String email, Gender gender, UserType type) {
        super(userId, first_name, last_name, username, password_hash, email, gender, type);
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
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

    public static Manager getManagerByUsername(String username) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select \"user\".id  as user_id,\n" +
                    "       manager.id as manager_id,\n" +
                    "       first_name,\n" +
                    "       last_name,\n" +
                    "       username,\n" +
                    "       pass_hash,\n" +
                    "       email,\n" +
                    "       gender,\n" +
                    "       type\n" +
                    "from \"user\"\n" +
                    "inner join manager on \"user\".id = manager.user_id\n" +
                    "where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new RuntimeException("There is no manager with this username");

            return new Manager(
                    UUID.fromString(resultSet.getString("manager_id")),
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

    public void createWaiterInDB(String first_name, String last_name, String username, String password_hash, String email, Gender gender) {
        try {
            UUID newUserId = UUID.randomUUID();
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into \"user\"\n" +
                    "(id\n," +
                    "first_name,\n" +
                    "last_name,\n" +
                    "username,\n" +
                    "pass_hash,\n" +
                    "email,\n" +
                    "gender,\n" +
                    "type)\n" +
                    "values (?,?,?,?,?,?,?,?::\"user_type\");");
            preparedStatement.setObject(1, newUserId);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password_hash);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, gender.getType());
            preparedStatement.setString(8, UserType.WAITER.getType());
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            preparedStatement = connection.prepareStatement("" +
                    "insert into waiter(user_id)\n" +
                    "values (?);");
            preparedStatement.setObject(1, newUserId);
            updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Waiter created successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createManagerInDB(String first_name, String last_name, String username, String password_hash, String email, Gender gender) {
        try {
            UUID newUserId = UUID.randomUUID();
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into \"user\"\n" +
                    "(id\n," +
                    "first_name,\n" +
                    "last_name,\n" +
                    "username,\n" +
                    "pass_hash,\n" +
                    "email,\n" +
                    "gender,\n" +
                    "type)\n" +
                    "values (?,?,?,?,?,?,?,?::\"user_type\");");
            preparedStatement.setObject(1, newUserId);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password_hash);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, gender.getType());
            preparedStatement.setString(8, UserType.MANAGER.getType());
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            preparedStatement = connection.prepareStatement("" +
                    "insert into manager(user_id)\n" +
                    "values (?);");
            preparedStatement.setObject(1, newUserId);
            updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Manager created successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableInDB(int capacity) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into \"table\"(capacity)\n" +
                    "values (?);");
            preparedStatement.setInt(1, capacity);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Table created successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createProductInDB(String name, double price, double weight, String description, int cookDuration) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "insert into product\n" +
                    "(name,\n" +
                    " price,\n" +
                    " weight,\n" +
                    " description,\n" +
                    " cook_duration)\n" +
                    "values (?,?::\"numeric\"::\"money\",?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setDouble(3, weight);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, cookDuration);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Product created successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void assignTableToWaiter(UUID TableId, String waiterUsername) {
        try {
            Connection connection = Connect.getMainConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select waiter.id\n" +
                    "from waiter\n" +
                    "inner join \"user\" on waiter.user_id = \"user\".id\n" +
                    "where username = 'vangay';");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new RuntimeException("There is no manager with waiter username");
            UUID waiterId = UUID.fromString(resultSet.getString("id"));
            preparedStatement = connection.prepareStatement("" +
                    "update \"table\"\n" +
                    "set waiter_id = ?\n" +
                    "where id = ?;");
            preparedStatement.setObject(1, waiterId);
            preparedStatement.setObject(2, TableId);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount == 0) throw new RuntimeException("insert did not complete");
            System.out.println("Waiter assigned to table successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
