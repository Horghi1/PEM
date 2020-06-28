package sample.income;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.IncomeRepositoryImpl;
import service.IncomeService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddIncomeController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField amountField;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> selectTypeComboBox;

    private IncomeService incomeService;

    public AddIncomeController() {
        this.incomeService = new IncomeService(new IncomeRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectTypeComboBox.setItems(FXCollections.observableArrayList(Constants.incomeCategories));
    }

    @FXML
    public void pressSaveAddNewIncomeButton() {
        errorLabel.setText("");

        Date date = null;
        if(datePicker.getValue() != null) {
            date = Date.valueOf(datePicker.getValue());
        } else {
            date = new Date(System.currentTimeMillis());
        }

        String type = selectTypeComboBox.getSelectionModel().getSelectedItem();
        if(type == null || type.equals(Constants.incomeCategories[0])) {
            errorLabel.setText("Please select category!");
            return;
        }


        Integer amount = null;
        try {
            amount = new Integer(amountField.getText());
            if(amount < 0) {
                errorLabel.setText("Amount should be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Amount should be a number");
            return;
        }

        boolean saved = incomeService.save(date, type, amount, Context.getInstance().getUser().getId());
        if(saved) {
            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Income cannot be saved");
        }
    }
}
