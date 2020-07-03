package sample.statistics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.ExpenseRepositoryImpl;
import repository.IncomeRepositoryImpl;
import service.ExpenseService;
import service.IncomeService;
import service.StatisticsService;

import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField incomeTextField;

    @FXML
    private TextField outcomeTextField;

    @FXML
    private TextField savingTextField;

    @FXML
    private TextField savingRateTextField;

    @FXML
    private TextField totalIncomeTextField;

    @FXML
    private TextField totalOutcomesTextField;

    @FXML
    private TextField totalSavingsTextField;

    @FXML
    private TextField totalSavingsRateTextField;

    @FXML
    public Label returnLabel;

    private StatisticsService statisticsService;

    public StatisticsController() {
        this.statisticsService = new StatisticsService(new ExpenseService(new ExpenseRepositoryImpl()), new IncomeService(new IncomeRepositoryImpl()));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Double totalIncome = statisticsService.getTotalIncomeAmount();
        Double totalOutome = statisticsService.getTotalOutcomeAmount();

        totalIncomeTextField.setText(totalIncome.toString());
        totalOutcomesTextField.setText(totalOutome.toString());
        totalSavingsTextField.setText(Double.toString(totalIncome - totalOutome));

        if(totalIncome != 0) {
            totalSavingsRateTextField.setText(new DecimalFormat("#.00").format((totalIncome - totalOutome) / totalIncome * 100) + "%");
        } else {
            totalSavingsRateTextField.setText(Double.toString(0));
        }
    }

    @FXML
    public void pressSelectButton() {
        Date startDate;
        Date endDate;

        LocalDate localStartDate = this.startDatePicker.getValue();
        LocalDate localEndDate = this.endDatePicker.getValue();

        if(localStartDate == null) {
            startDate = new java.sql.Date(946681200000L); // ~ year = 2000
        } else {
            startDate = java.sql.Date.valueOf(localStartDate);
        }

        if(localEndDate == null) {
            endDate = new java.sql.Date(System.currentTimeMillis());
        } else {
            endDate = Date.valueOf(localEndDate);
        }

        Double incomeAmount = statisticsService.getTotalIncomeAmountForPeriod(startDate, endDate);
        Double outcomeAmount = statisticsService.getTotalOutcomeAmountForPeriod(startDate, endDate);

        incomeTextField.setText(incomeAmount.toString());
        outcomeTextField.setText(outcomeAmount.toString());
        savingTextField.setText(Double.toString(incomeAmount - outcomeAmount));
        if(incomeAmount != 0) {
            savingRateTextField.setText(new DecimalFormat("#.00").format((incomeAmount - outcomeAmount) / incomeAmount * 100) + "%");
        } else {
            savingRateTextField.setText(Double.toString(0));
        }

    }


}
