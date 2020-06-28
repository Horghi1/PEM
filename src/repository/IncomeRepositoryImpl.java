package repository;

import util.DatabaseProperties;

import java.sql.*;

public class IncomeRepositoryImpl implements IncomeRepository {

    private Connection connection;

    public IncomeRepositoryImpl() {
        try {
            connection = DriverManager.getConnection(DatabaseProperties.CONNECTION_URL, DatabaseProperties.CONNECTION_USER, DatabaseProperties.CONNECTION_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Date date, String type, Integer amount, Integer userId) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO income(date, type, amount, id_user) VALUES (?, ?, ?, ?)");
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, amount);
            preparedStatement.setInt(4, userId);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
