package sample.income;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import repository.IncomeRepositoryImpl;
import service.IncomeService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
        Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) > 0);
                    }

                };
            }

        };
        datePicker.setDayCellFactory(callB);

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

        boolean saved = incomeService.save(date, type, amount, Context.getInstance().getUser().getId());
        if(saved) {
            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Income cannot be saved");
        }
    }
}
