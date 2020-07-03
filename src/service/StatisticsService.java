package service;

import model.Expense;
import model.Income;
import util.Context;

import java.sql.Date;

public class StatisticsService {

    private ExpenseService expenseService;
    private IncomeService incomeService;

    public StatisticsService(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    public Double getTotalIncomeAmountForPeriod(Date startDate, Date endDate) {
        return this.incomeService.getAllByDatesAndUserId(startDate, endDate, Context.getInstance().getUser().getId())
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }

    public Double getTotalOutcomeAmountForPeriod(Date startDate, Date endDate) {
        return this.expenseService.getExpensesByDate(Context.getInstance().getUser().getId(), startDate, endDate)
                .stream()
                .mapToDouble(Expense::getCost)
                .sum();
    }

    public Double getTotalIncomeAmount() {
        return this.incomeService.getAllByUserId(Context.getInstance().getUser().getId())
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }

    public Double getTotalOutcomeAmount() {
        return this.expenseService.getAllExpensesByUserId(Context.getInstance().getUser().getId())
                .stream()
                .mapToDouble(Expense::getCost)
                .sum();
    }
}
