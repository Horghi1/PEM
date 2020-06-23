package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ExpenseTableViewModel {

    private final SimpleIntegerProperty rowNumColumnProperty = new SimpleIntegerProperty(0);

    private final SimpleIntegerProperty idColumnProperty = new SimpleIntegerProperty(0);

    private final SimpleStringProperty dateColumnProperty = new SimpleStringProperty("");

    private final SimpleIntegerProperty costColumnProperty = new SimpleIntegerProperty(0);

    private final SimpleStringProperty typeColumnProperty = new SimpleStringProperty("");

    private final SimpleStringProperty commentColumnProperty = new SimpleStringProperty("");

    public ExpenseTableViewModel(Integer rowNum, Integer id, String date, Integer cost, String type, String comment) {
        this.setRowNumColumnProperty(rowNum);
        this.setIdColumnProperty(id);
        this.setDateColumnProperty(date);
        this.setCostColumnProperty(cost);
        this.setTypeColumnProperty(type);
        this.setCommentColumnProperty(comment);
    }

    public int getRowNumColumnProperty() {
        return rowNumColumnProperty.get();
    }

    public SimpleIntegerProperty rowNumColumnPropertyProperty() {
        return rowNumColumnProperty;
    }

    public void setRowNumColumnProperty(int rowNumColumnProperty) {
        this.rowNumColumnProperty.set(rowNumColumnProperty);
    }

    public int getIdColumnProperty() {
        return idColumnProperty.get();
    }

    public SimpleIntegerProperty idColumnPropertyProperty() {
        return idColumnProperty;
    }

    public void setIdColumnProperty(int idColumnProperty) {
        this.idColumnProperty.set(idColumnProperty);
    }

    public String getDateColumnProperty() {
        return dateColumnProperty.get();
    }

    public SimpleStringProperty dateColumnPropertyProperty() {
        return dateColumnProperty;
    }

    public void setDateColumnProperty(String dateColumnProperty) {
        this.dateColumnProperty.set(dateColumnProperty);
    }

    public int getCostColumnProperty() {
        return costColumnProperty.get();
    }

    public SimpleIntegerProperty costColumnPropertyProperty() {
        return costColumnProperty;
    }

    public void setCostColumnProperty(int costColumnProperty) {
        this.costColumnProperty.set(costColumnProperty);
    }

    public String getTypeColumnProperty() {
        return typeColumnProperty.get();
    }

    public SimpleStringProperty typeColumnPropertyProperty() {
        return typeColumnProperty;
    }

    public void setTypeColumnProperty(String typeColumnProperty) {
        this.typeColumnProperty.set(typeColumnProperty);
    }

    public String getCommentColumnProperty() {
        return commentColumnProperty.get();
    }

    public SimpleStringProperty commentColumnPropertyProperty() {
        return commentColumnProperty;
    }

    public void setCommentColumnProperty(String commentColumnProperty) {
        this.commentColumnProperty.set(commentColumnProperty);
    }

    @Override
    public String toString() {
        return "ExpenseTableViewModel{" +
                "idColumnProperty=" + idColumnProperty +
                ", dateColumnProperty=" + dateColumnProperty +
                ", costColumnProperty=" + costColumnProperty +
                ", typeColumnProperty=" + typeColumnProperty +
                ", commentColumnProperty=" + commentColumnProperty +
                '}';
    }
}
