package repository;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;
    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/pem_schema?serverTimezone=UTC";
    private final String CONNECTION_USER = "root";
    private final String CONNECTION_PASS = "@Faraparola10";

    public UserRepositoryImpl() {
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User(id, firstName, lastName, email, password);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User user = new User(id, firstName, lastName, email, password);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");

                User user = new User(id, firstName, lastName, email, password);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(first_name, last_name, email, password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
