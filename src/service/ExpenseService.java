package service;

import javafx.util.Pair;
import model.Expense;
import repository.ExpenseRepository;
import util.Constants;
import util.UndoOperation;

import java.sql.Date;
import java.util.List;

public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private UndoOperation undoOperation;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.undoOperation = UndoOperation.getInstance();
        this.expenseRepository = expenseRepository;
    }

    public Expense getExpenseById(int id) {
        return this.expenseRepository.getExpenseById(id);
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
        Expense savedExpense = this.expenseRepository.save(new Expense(date, cost, type, comment, userId));

        if(savedExpense != null) {
            undoOperation.add(new Pair<>(Constants.SAVE_OPERATION, savedExpense));
            return true;
        }

        return false;
    }

    public boolean update(int expenseId, Date date, String type, Integer cost, String comment) {
        Expense oldExpense = this.getExpenseById(expenseId);

        boolean updated = expenseRepository.update(expenseId, date, type, cost, comment);
        if(updated) {
            this.undoOperation.add(new Pair<>(Constants.UPDATE_OPERATION, oldExpense));
        }

        return updated;
    }

    public boolean delete(int expenseId) {
        Expense deletedExpense = this.getExpenseById(expenseId);

        boolean deleted = this.expenseRepository.delete(expenseId);
        if(deleted) {
            this.undoOperation.add(new Pair<>(Constants.DELETE_OPERATION, deletedExpense));
        }

        return deleted;
    }

    public void undo() {
        Pair<String, Expense> undo = this.undoOperation.undo();
        if(undo == null) {
            return;
        }

        switch (undo.getKey()) {
            case Constants.SAVE_OPERATION:
                this.expenseRepository.delete(undo.getValue().getId());
                break;

            case Constants.UPDATE_OPERATION:
                Expense expense = undo.getValue();
                this.expenseRepository.update(expense.getId(), expense.getDate(), expense.getType(), expense.getCost(), expense.getComment());
                break;

            case Constants.DELETE_OPERATION:
                this.expenseRepository.save(undo.getValue());
                break;
        }

    }
}
