package model;

import java.util.Date;

public class Expense {

    private int id;
    private Date date;
    private String type;
    private String comment;
    private int userId;

    public Expense(int id, Date date, String type, String comment, int userId) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.comment = comment;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
