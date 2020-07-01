package repository;

import model.Income;
import util.DatabaseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public List<Income> getAllByUserId(int userId) {
        List<Income> incomeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM income WHERE id_user = ?");
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int incomeId = resultSet.getInt("id");
                String type = resultSet.getString("type");
                int amount = resultSet.getInt("amount");
                Date date = resultSet.getDate("date");

                incomeList.add(new Income(incomeId, type, amount, userId, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    @Override
    public List<Income> getAllByDatesAndUserId(Date startDate, Date endDate, int userId) {
        List<Income> incomeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM income WHERE id_user = ? and date >= ? and date <= ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, startDate, Calendar.getInstance());
            preparedStatement.setDate(3, endDate, Calendar.getInstance());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int incomeId = resultSet.getInt("id");
                String type = resultSet.getString("type");
                int amount = resultSet.getInt("amount");
                Date date = resultSet.getDate("date");

                incomeList.add(new Income(incomeId, type, amount, userId, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomeList;
    }

    @Override
    public boolean save(Date date, String type, Double amount, Integer userId) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO income(date, type, amount, id_user) VALUES (?, ?, ?, ?)");
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, type);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setInt(4, userId);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
