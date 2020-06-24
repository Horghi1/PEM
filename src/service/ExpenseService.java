package service;

import model.Expense;
import repository.ExpenseRepository;

import java.sql.Date;
import java.util.List;

public class ExpenseService {

    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpensesByUserId(int userId) {
        return expenseRepository.getAllExpensesByUserId(userId);
    }

    public List<Expense> getExpensesByDate(int userId, Date startDate, Date endDate) {
        return expenseRepository.getExpensesByDate(userId, startDate, endDate);
    }

    public List<Expense> getExpensesByDateAndType(int userId, Date startDate, Date endDate, String type) {
        return expenseRepository.getExpensesByDateAndType(userId, startDate, endDate, type);
    }

    public List<Expense> getExpensesByType(int userId, String type) {
        return expenseRepository.getExpensesByType(userId, type);
    }

    public boolean saveExpense(Date date, String type, Integer cost, String comment, Integer userId) {
        return this.expenseRepository.save(date, type, cost, comment, userId);
    }

    public boolean update(int expenseId, Date date, String type, Integer cost, String comment) {
        return expenseRepository.update(expenseId, date, type, cost, comment);
    }

    public boolean delete(int expenseId) {
        return expenseRepository.delete(expenseId);
    }

}
