package sample.income;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import repository.ExpenseRepositoryImpl;
import repository.IncomeRepositoryImpl;
import service.ExpenseService;
import service.IncomeService;
import util.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class IncomeOverviewController implements Initializable {

    @FXML
    private ComboBox<String> selectTypeComboBox;

    @FXML
    public Label returnLabel;

    private IncomeService incomeService;

    public IncomeOverviewController() {
        this.incomeService = new IncomeService(new IncomeRepositoryImpl());
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

    //String type = selectTypeComboBox.getSelectionModel().getSelectedItem();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
