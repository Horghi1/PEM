package sample.expense;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import repository.ExpenseRepositoryImpl;
import service.ExpenseService;
import util.Constants;
import util.Context;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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

    private ExpenseController expenseController;

    private ExpenseService expenseService;

    public AddExpenseController() {
        this.expenseService = new ExpenseService(new ExpenseRepositoryImpl());
    }

    @FXML
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

        selectTypeComboBox.setItems(FXCollections.observableArrayList(Constants.expenseCategories));
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
        if(type == null || type.equals(Constants.expenseCategories[0])) {
            errorLabel.setText("Please select category!");
            return;
        }
        String comment = commentField.getText();

        Double cost = null;
        try {
            cost = new Double(costField.getText());
            if(cost < 0) {
                errorLabel.setText("Cost should be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Cost should be a number");
            return;
        }

        //insert in database
        boolean inserted = this.expenseService.saveExpense(date, type, cost, comment, Context.getInstance().getUser().getId());
        if(inserted) {
            this.expenseController.pressSelectButton();

            Stage stage = (Stage) selectTypeComboBox.getScene().getWindow();
            stage.close();
        }
    }

    public void setExpenseController(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }
}

