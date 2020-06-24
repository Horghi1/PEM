package sample.expense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Expense;
import repository.ExpenseRepositoryImpl;
import service.ExpenseService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.sql.Date;
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
        selectTypeComboBox.setItems(FXCollections.observableArrayList(Constants.categories));

        Expense expense = Context.getInstance().getExpense();

        datePicker.setValue(((java.sql.Date) expense.getDate()).toLocalDate());
        costField.setText(expense.getCost().toString());
        commentField.setText(expense.getComment());
        selectTypeComboBox.getSelectionModel().select(getCategoryIndex(expense.getType()));
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
        String comment = commentField.getText();

        Integer cost = null;
        try {
            cost = new Integer(costField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Cost should be a number");
            return;
        }

        boolean updated = expenseService.update(Context.getInstance().getExpense().getId(), date, type, cost, comment);
        if(updated) {
            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Expense cannot be updated");
        }
    }

    @FXML
    public void pressDeleteButton() {
        errorLabel.setText("");

        boolean deleted = expenseService.delete(Context.getInstance().getExpense().getId());
        if(deleted) {
            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Expense cannot be deleted");
        }
    }

    private int getCategoryIndex(String category) {
        for(int i = 0; i < Constants.categories.length; i++) {
            if (Constants.categories[i].equals(category)) {
                return i;
            }
        }
        return 0;
    }
}
