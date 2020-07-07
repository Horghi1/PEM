package sample.income;

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
import model.Income;
import model.IncomeTableViewModel;
import repository.ExpenseRepositoryImpl;
import repository.IncomeRepositoryImpl;
import sample.expense.EditExpenseController;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class IncomeOverviewController implements Initializable {

    @FXML
    private ComboBox<String> selectTypeComboBox;

    @FXML
    private TableView<IncomeTableViewModel> incomeTable;

    @FXML
    private TableColumn<IncomeTableViewModel, String> rowNumColumn;

    @FXML
    private TableColumn<IncomeTableViewModel, String> dateColumn;

    @FXML
    private TableColumn<IncomeTableViewModel, String> typeColumn;

    @FXML
    private TableColumn<IncomeTableViewModel, String> amountColumn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    public Label returnLabel;

    private IncomeService incomeService;

    public IncomeOverviewController() {
        this.incomeService = new IncomeService(new IncomeRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectTypeComboBox.setItems(FXCollections.observableArrayList(Constants.incomeCategories));

        rowNumColumn.setCellValueFactory(new PropertyValueFactory<>("rowNumColumnProperty"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateColumnProperty"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeColumnProperty"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amountColumnProperty"));

        incomeTable.getItems().setAll(getItemsToAdd(incomeService.getAllByUserId(Context.getInstance().getUser().getId())));
    }

    private List<IncomeTableViewModel> getItemsToAdd(List<Income> incomeList) {
        List<IncomeTableViewModel> list = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        int counter = 1;
        for(Income income: incomeList) {
            list.add(new IncomeTableViewModel(counter++, income.getId(), formatter.format(income.getDate()), income.getAmount(), income.getType()));
        }

        return list;
    }

    @FXML
    public void pressSelectButton() {
        String type = selectTypeComboBox.getSelectionModel().getSelectedItem();
        if(type == null || type.equals(Constants.expenseCategories[0])) {
            type = null;
        }

        Date startDate = null;
        Date endDate = null;

        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();

        if(type != null && localStartDate == null && localEndDate == null) {
            incomeTable.getItems().setAll(getItemsToAdd(incomeService.getIncomeByType(type, Context.getInstance().getUser().getId())));
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

        if(type == null) {
            incomeTable.getItems().setAll(getItemsToAdd(incomeService.getAllByDatesAndUserId(startDate, endDate, Context.getInstance().getUser().getId())));
        } else {
            incomeTable.getItems().setAll(getItemsToAdd(incomeService.getAllByDatesAndType(startDate, endDate, type, Context.getInstance().getUser().getId())));
        }

    }

    @FXML
    public void pressRefreshButton() {
        incomeTable.getItems().setAll(getItemsToAdd(incomeService.getAllByUserId(Context.getInstance().getUser().getId())));
    }

    @FXML
    public void onMouseClickedTableViewEvent() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        IncomeTableViewModel incomeTableViewModel = incomeTable.getSelectionModel().getSelectedItem();
        try {
            Context.getInstance().setIncome(new Income(incomeTableViewModel.getIdColumnProperty(),
                    incomeTableViewModel.getTypeColumnProperty(),
                    incomeTableViewModel.getAmountColumnProperty(),
                    Context.getInstance().getUser().getId(),
                    new Date(formatter.parse(incomeTableViewModel.getDateColumnProperty()).getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // open a new window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_income.fxml"));
            Parent window = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit income");
            stage.setScene(new Scene(window, 275, 400));
            stage.show();
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());

            EditIncomeController EditIncomeController = (EditIncomeController) loader.getController();
            EditIncomeController.setIncomeOverviewController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void pressReturnLabel() {
        changeWindow("../expense/expense.fxml");
    }

    private void changeWindow(String resourceURL) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(resourceURL));
            Stage stage = (Stage) returnLabel.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
