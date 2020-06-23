package sample.expense;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.ExpenseRepositoryImpl;
import service.ExpenseService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


public class AddExpenseController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField costField;

    @FXML
    private TextField commentField;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> selectTypeComboBox;

    private ExpenseService expenseService;

    public AddExpenseController() {
        this.expenseService = new ExpenseService(new ExpenseRepositoryImpl());
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        selectTypeComboBox.setItems(FXCollections.observableArrayList(Constants.categories));
    }

    @FXML
    public void presssaveAddNewExpenseButton() {
        errorLabel.setText("");

        Date date = null;
        if(datePicker.getValue() != null) {
            date = Date.valueOf(datePicker.getValue());
        } else {
            date = new Date(System.currentTimeMillis());
        }

        String type = selectTypeComboBox.getSelectionModel().getSelectedItem();
        String comment = commentField.getText();

        Integer cost = null;
        try {
            cost = new Integer(costField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Cost should be a number");
            return;
        }

        //insert in database
        boolean inserted = this.expenseService.saveExpense(date, type, cost, comment, Context.getInstance().getUser().getId());
        if(inserted) {
            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }
}

