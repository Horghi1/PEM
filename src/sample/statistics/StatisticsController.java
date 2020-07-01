package sample.statistics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.ExpenseRepositoryImpl;
import repository.IncomeRepositoryImpl;
import service.ExpenseService;
import service.IncomeService;
import service.StatisticsService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private TextField totalIncomeTextField;

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
        totalIncomeTextField.setText(statisticsService.getTotalIncome().toString());
    }


}
