package sample.expense;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Expense;
import model.ExpenseTableViewModel;
import model.User;
import repository.ExpenseRepositoryImpl;
import service.ExpenseService;
import service.IncomeService;
import util.Constants;
import util.Context;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {

    @FXML
    private TableView<ExpenseTableViewModel> expenseTable;

    @FXML
    private TableColumn<ExpenseTableViewModel, String> rowNumColumn;

    @FXML
    private TableColumn<ExpenseTableViewModel, String> dateColumn;

    @FXML
    private TableColumn<ExpenseTableViewModel, String> typeColumn;

    @FXML
    private TableColumn<ExpenseTableViewModel, String> costColumn;

    @FXML
    private TableColumn<ExpenseTableViewModel, String> commentColumn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> selectCategoryComboBox;

    private ExpenseService expenseService;

    public ExpenseController() {
        this.expenseService = new ExpenseService(new ExpenseRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectCategoryComboBox.setItems(FXCollections.observableArrayList(Constants.expenseCategories));

        rowNumColumn.setCellValueFactory(new PropertyValueFactory<>("rowNumColumnProperty"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateColumnProperty"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeColumnProperty"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("costColumnProperty"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("commentColumnProperty"));

        Context.getInstance().setUser(new User(11, "florin", "horghidan", "florinhorghidan@yahoo.com", "$2a$12$dnVv9PLa4y25.EkPYMAidOPTR79OSwWM/z9EKKcex4Xxm/wR7Nmaa"));
        expenseTable.getItems().setAll(getItemsToAdd(expenseService.getAllExpensesByUserId(Context.getInstance().getUser().getId())));
    }


    private List<ExpenseTableViewModel> getItemsToAdd(List<Expense> expenses) {
        List<ExpenseTableViewModel> list = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        int counter = 1;
        for(Expense expense: expenses) {
            list.add(new ExpenseTableViewModel(counter++, expense.getId(), formatter.format(expense.getDate()), expense.getCost(), expense.getType(), expense.getComment()));
        }

        return list;
    }

    @FXML
    public void pressSelectButton() {
        String category = selectCategoryComboBox.getSelectionModel().getSelectedItem();
        if(category == null || category.equals(Constants.expenseCategories[0])) {
            category = null;
        }

        Date startDate = null;
        Date endDate = null;

        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();

        if(category != null && localStartDate == null && localEndDate == null) {
            expenseTable.getItems().setAll(getItemsToAdd(expenseService.getExpensesByType(Context.getInstance().getUser().getId(), category)));
            return;
        }

        if(localStartDate == null) {
            startDate = new Date(946681200000L); // ~ year = 2000
        } else {
            startDate = Date.valueOf(localStartDate);
        }

        if(localEndDate == null) {
            endDate = new Date(System.currentTimeMillis());
        } else {
            endDate = Date.valueOf(localEndDate);
        }

        if(category == null) {
            expenseTable.getItems().setAll(getItemsToAdd(expenseService.getExpensesByDate(Context.getInstance().getUser().getId(), startDate, endDate)));
            return;
        }

        if(category != null) {
            expenseTable.getItems().setAll(getItemsToAdd(expenseService.getExpensesByDateAndType(Context.getInstance().getUser().getId(), startDate, endDate, category)));
        }

    }

    @FXML
    public void pressAddNewExpenseMenuItem(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_expense.fxml"));
            Parent window = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add new expense");
            stage.setScene(new Scene(window, 275, 400));
            stage.show();
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());

            AddExpenseController addExpenseController = (AddExpenseController) loader.getController();
            addExpenseController.setExpenseController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pressAddNewIncomeMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../income/add_income.fxml"));
            Parent window = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add new income");
            stage.setScene(new Scene(window, 275, 400));
            stage.show();
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void pressExpenseOverviewMenuItem (){
        changeWindow("../statistics/statistics.fxml");
    }

    @FXML
    public void pressIncomeOverviewMenuItem (){
        changeWindow("../income/income_overview.fxml");
    }

    @FXML
    public void pressRefreshButton() {
        expenseTable.getItems().setAll(getItemsToAdd(expenseService.getAllExpensesByUserId(Context.getInstance().getUser().getId())));
    }

    @FXML
    public void pressLogoutMenuItem() {
        Context.getInstance().setUser(null);
        changeWindow("../login/sample.fxml");
    }
    @FXML
    public void pressProfileMenuItem(){
        changeWindow("../profile/profile.fxml");
    }

    @FXML
    public void onMouseClickedTableViewEvent() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        ExpenseTableViewModel expenseTableViewModel = expenseTable.getSelectionModel().getSelectedItem();
        try {
            Context.getInstance().setExpense(new Expense(expenseTableViewModel.getIdColumnProperty(),
                    new Date(formatter.parse(expenseTableViewModel.getDateColumnProperty()).getTime()),
                    expenseTableViewModel.getCostColumnProperty(),
                    expenseTableViewModel.getTypeColumnProperty(), expenseTableViewModel.getCommentColumnProperty()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // open a new window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_expense.fxml"));
            Parent window = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit expense");
            stage.setScene(new Scene(window, 275, 400));
            stage.show();
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());

            EditExpenseController editExpenseController = (EditExpenseController) loader.getController();
            editExpenseController.setExpenseController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pressUndoMenuItem() {
        this.expenseService.undo();
        this.pressRefreshButton();
    }

    @FXML
    public void pressExitMenuItem() {
        System.exit(0);
    }

    private void changeWindow(String resourceURL) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(resourceURL));
            Stage stage = (Stage) expenseTable.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
