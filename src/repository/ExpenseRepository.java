package repository;

import model.Expense;

import java.sql.Date;
import java.util.List;

public interface ExpenseRepository {

    Expense getExpenseById(int id);

    List<Expense> getAllExpensesByUserId(int userId);

    List<Expense> getExpensesByDate(int userId, Date startDate, Date endDate);

    List<Expense> getExpensesByDateAndType(int userId, Date startDate, Date endDate, String type);

    List<Expense> getExpensesByType(int userId, String type);

    Expense save(Expense expense);

    boolean update(int expenseId, Date date, String type, Integer cost, String comment);

    boolean delete(int expenseId);

}
