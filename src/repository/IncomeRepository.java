package repository;

import model.Income;

import java.sql.Date;
import java.util.List;

public interface IncomeRepository {

    List<Income> getAllByUserId(int userId);

    List<Income> getAllByDatesAndUserId(Date startDate, Date endDate, int userId);

    boolean save(Date date, String type, Double amount, Integer userId);

}
