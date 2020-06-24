package repository;

import model.Expense;

import java.sql.Date;
import java.util.List;

public interface ExpenseRepository {

    List<Expense> getAllExpensesByUserId(int userId);

    List<Expense> getExpensesByDate(int userId, Date startDate, Date endDate);

    List<Expense> getExpensesByDateAndType(int userId, Date startDate, Date endDate, String type);

    List<Expense> getExpensesByType(int userId, String type);

    boolean save(Date date, String type, Integer cost, String comment, Integer userId);

    boolean update(int expenseId, Date date, String type, Integer cost, String comment);

    boolean delete(int expenseId);

}
