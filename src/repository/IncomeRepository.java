package repository;

import model.Income;

import java.sql.Date;
import java.util.List;

public interface IncomeRepository {

    List<Income> getAllByUserId(int userId);

    List<Income> getAllByDatesAndUserId(Date startDate, Date endDate, int userId);

    boolean save(Date date, String type, Double amount, Integer userId);

    List<Income> getAllByDatesAndType(Date startDate, Date endDate, String type, int userId);

    List<Income> getIncomeByType(String type, int userId);

    boolean update(int id, Date date, String type, double amount);

    boolean delete(int id);

}
