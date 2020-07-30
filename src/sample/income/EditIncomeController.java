package sample.income;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Expense;
import model.Income;
import repository.IncomeRepositoryImpl;
import service.IncomeService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EditIncomeController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField amountField;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> selectTypeComboBox;

    private IncomeOverviewController incomeOverviewController;

    private IncomeService incomeService;

    public EditIncomeController() {
        this.incomeService = new IncomeService(new IncomeRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectTypeComboBox.setItems(FXCollections.observableArrayList(Constants.incomeCategories));

        Income income = Context.getInstance().getIncome();

        datePicker.setValue(((java.sql.Date) income.getDate()).toLocalDate());
        amountField.setText(Double.toString(income.getAmount()));
        selectTypeComboBox.getSelectionModel().select(getTypeIndex(income.getType()));
    }

    public void setIncomeOverviewController(IncomeOverviewController incomeOverviewController) {
        this.incomeOverviewController = incomeOverviewController;
    }

    @FXML
    public void pressSaveButton() {
        errorLabel.setText("");

        Date date = null;
        if(datePicker.getValue() != null) {
            date = Date.valueOf(datePicker.getValue());
        } else {
            date = new Date(System.currentTimeMillis());
        }

        String type = selectTypeComboBox.getSelectionModel().getSelectedItem();
        if(type == null || type.equals(Constants.expenseCategories[0])) {
            errorLabel.setText("Please select category!");
            return;
        }

        Double amount = null;
        try {
            amount = new Double(amountField.getText());
            if(amount < 0) {
                errorLabel.setText("Amount should be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Amount should be a number");
            return;
        }

        boolean updated = incomeService.update(Context.getInstance().getIncome().getId(), date, type, amount);
        if(updated) {
            this.incomeOverviewController.pressSelectButton();

            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Income cannot be updated");
        }
    }

    @FXML
    public void pressDeleteButton() {
        errorLabel.setText("");

        boolean deleted = incomeService.delete(Context.getInstance().getIncome().getId());
        if(deleted) {
            this.incomeOverviewController.pressSelectButton();

            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Income cannot be deleted");
        }
    }

    private int getTypeIndex(String type) {
        for(int i = 0; i < Constants.incomeCategories.length; i++) {
            if (Constants.incomeCategories[i].equals(type)) {
                return i;
            }
        }
        return 0;
    }
}
