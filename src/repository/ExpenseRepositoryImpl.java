package repository;

import model.Expense;
import util.DatabaseProperties;

import javax.swing.plaf.nimbus.State;
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

                expenses.add(new Expense(id, new Date(date.getTime()), cost, type, comment, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    @Override
    public Expense getExpenseById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expense WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Integer cost = resultSet.getInt("cost");
                String type = resultSet.getString("type");
                String comment = resultSet.getString("comment");
                Date date = resultSet.getDate("date");
                int userId = resultSet.getInt("id_user");

                return new Expense(id, new Date(date.getTime()), cost, type, comment, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
    public Expense save(Expense expense) {
        try {
            int columnIndex = 1;
            PreparedStatement preparedStatement;

            if(expense.getId() != null) {
                preparedStatement = connection.prepareStatement("INSERT INTO expense(id, date, type, cost, comment, id_user) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(columnIndex++, expense.getId());
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO expense(date, type, cost, comment, id_user) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            }

            preparedStatement.setDate(columnIndex++, expense.getDate(), Calendar.getInstance());
            preparedStatement.setString(columnIndex++, expense.getType());
            preparedStatement.setInt(columnIndex++, expense.getCost());
            preparedStatement.setString(columnIndex++, expense.getComment());
            preparedStatement.setInt(columnIndex++, expense.getUserId());

            preparedStatement.executeUpdate();

            if(expense.getId() == null) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()) {
                    expense.setId(resultSet.getInt(1));
                }
            }

            return expense;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(int expenseId, Date date, String type, Integer cost, String comment) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE expense SET date = ?, type = ?, " +
                    "cost = ?, comment = ? WHERE id = ?");
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, cost);
            preparedStatement.setString(4, comment);
            preparedStatement.setInt(5, expenseId);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int expenseId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM expense WHERE id = ?");
            preparedStatement.setInt(1, expenseId);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
