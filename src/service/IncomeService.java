package service;

import repository.IncomeRepository;

import java.sql.Date;

public class IncomeService {

    private IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public boolean save(Date date, String type, Integer amount, Integer userId) {
        return incomeRepository.save(date, type, amount, userId);
    }

}
