package sample.expense;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Expense;
import model.User;
import repository.ExpenseRepositoryImpl;
import service.ExpenseService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditExpenseController implements Initializable {

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

    public EditExpenseController() {
        this.expenseService = new ExpenseService(new ExpenseRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Expense expense = Context.getInstance().getExpense();
        datePicker.setValue(LocalDate.of(expense.getDate().getYear(), expense.getDate().getMonth(), expense.getDate().getDay()));

    }

    @FXML
    public void presssaveAddNewExpenseButton() {

    }
}
