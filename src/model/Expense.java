package model;

import java.sql.Date;

public class Expense {

    private Integer id;
    private Date date;
    private Integer cost;
    private String type;
    private String comment;
    private Integer userId;

    public Expense(Date date, Integer cost, String type, String comment, int userId) {
        this.date = date;
        this.cost = cost;
        this.type = type;
        this.comment = comment;
        this.userId = userId;
    }

    public Expense(int id, Date date, Integer cost, String type, String comment) {
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.type = type;
        this.comment = comment;
    }

    public Expense(int id, Date date, Integer cost, String type, String comment, Integer userId) {
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.type = type;
        this.comment = comment;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", date=" + date +
                ", cost=" + cost +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", userId=" + userId +
                '}';
    }
}
