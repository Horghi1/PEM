package service;

import model.Income;
import repository.IncomeRepository;

import java.sql.Date;
import java.util.List;

public class IncomeService {

    private IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public boolean save(Date date, String type, Double amount, Integer userId) {
        return incomeRepository.save(date, type, amount, userId);
    }

    public List<Income> getAllByUserId(int userId) {
        return this.incomeRepository.getAllByUserId(userId);
    }

    public List<Income> getAllByDatesAndUserId(Date startDate, Date endDate, int userId) {
        return this.incomeRepository.getAllByDatesAndUserId(startDate, endDate, userId);
    }
}
