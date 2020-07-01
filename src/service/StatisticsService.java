package service;

import model.Expense;
import model.Income;
import util.Context;

public class StatisticsService {

    private ExpenseService expenseService;
    private IncomeService incomeService;

    public StatisticsService(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    public Double getTotalIncome() {
        return this.incomeService.getAllByUserId(Context.getInstance().getUser().getId())
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }
}
