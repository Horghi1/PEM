package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class IncomeTableViewModel {

    private final SimpleIntegerProperty rowNumColumnProperty = new SimpleIntegerProperty(0);

    private final SimpleIntegerProperty idColumnProperty = new SimpleIntegerProperty(0);

    private final SimpleStringProperty dateColumnProperty = new SimpleStringProperty("");

    private final SimpleDoubleProperty amountColumnProperty = new SimpleDoubleProperty(0);

    private final SimpleStringProperty typeColumnProperty = new SimpleStringProperty("");

    public IncomeTableViewModel(Integer rowNum, Integer id, String date, Double amount, String type) {
        this.setRowNumColumnProperty(rowNum);
        this.setIdColumnProperty(id);
        this.setDateColumnProperty(date);
        this.setAmountColumnProperty(amount);
        this.setTypeColumnProperty(type);
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

    public double getAmountColumnProperty() {
        return amountColumnProperty.get();
    }

    public SimpleDoubleProperty amountColumnPropertyProperty() {
        return amountColumnProperty;
    }

    public void setAmountColumnProperty(double amountColumnProperty) {
        this.amountColumnProperty.set(amountColumnProperty);
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

    @Override
    public String toString() {
        return "IncomeTableViewModel{" +
                "rowNumColumnProperty=" + rowNumColumnProperty +
                ", idColumnProperty=" + idColumnProperty +
                ", dateColumnProperty=" + dateColumnProperty +
                ", amountColumnProperty=" + amountColumnProperty +
                ", typeColumnProperty=" + typeColumnProperty +
                '}';
    }
}
