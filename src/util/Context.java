package util;

import model.Expense;
import model.User;

public class Context {

    private User user;
    private Expense expense;

    private static Context instance = null;

    private Context() {
    }

    public static Context getInstance() {
        if(instance == null) {
           instance = new Context();
        }

        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
