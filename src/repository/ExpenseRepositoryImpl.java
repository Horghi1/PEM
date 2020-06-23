package repository;

import model.Expense;
import util.DatabaseProperties;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpenseRepositoryImpl implements ExpenseRepository {

    private Connection connection;

    public ExpenseRepositoryImpl() {
        try {
            connection = DriverManager.getConnection(DatabaseProperties.CONNECTION_URL, DatabaseProperties.CONNECTION_USER, DatabaseProperties.CONNECTION_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<Expense> getExpensesFromResultSet(ResultSet resultSet) {
        List<Expense> expenses = new ArrayList<>();
        try {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                Integer cost = resultSet.getInt("cost");
                String type = resultSet.getString("type");
                String comment = resultSet.getString("comment");
                Date date = resultSet.getDate("date");
                int userId = resultSet.getInt("id_user");

                expenses.add(new Expense(id, date, cost, type, comment, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    @Override
    public List<Expense> getAllExpensesByUserId(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expense WHERE id_user = ?");
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return getExpensesFromResultSet(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Expense> getExpensesByDate(int userId, Date startDate, Date endDate) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expense WHERE id_user = ? and date >= ? and date <= ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, startDate, Calendar.getInstance());
            preparedStatement.setDate(3, endDate, Calendar.getInstance());

            ResultSet resultSet = preparedStatement.executeQuery();

            return getExpensesFromResultSet(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Expense> getExpensesByDateAndType(int userId, Date startDate, Date endDate, String type) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expense WHERE id_user = ? and type = ? and date >= ? and date <= ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, type);
            preparedStatement.setDate(3, startDate, Calendar.getInstance());
            preparedStatement.setDate(4, endDate, Calendar.getInstance());

            ResultSet resultSet = preparedStatement.executeQuery();

            return getExpensesFromResultSet(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Expense> getExpensesByType(int userId, String type) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expense WHERE id_user = ? and type = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, type);

            ResultSet resultSet = preparedStatement.executeQuery();

            return getExpensesFromResultSet(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(Date date, String type, Integer cost, String comment, Integer userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO expense(date, type, cost, comment, id_user) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setDate(1, date, Calendar.getInstance());
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, cost);
            preparedStatement.setString(4, comment);
            preparedStatement.setInt(5, userId);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
