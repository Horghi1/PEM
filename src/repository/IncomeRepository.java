package repository;

import java.sql.Date;

public interface IncomeRepository {

    boolean save(Date date, String type, Integer amount, Integer userId);

}
