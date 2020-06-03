package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import repository.UserRepositoryImpl;
import service.UserService;

import java.awt.event.ActionListener;


public class Controller {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    public UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void pressLoginButton() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if(email.equals("") || password.equals("")) {
            errorLabel.setText("Please complete all fields!");
            return;
        }

        User user = userService.loginUser(email, password);
        if(user == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("expense/expense.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
        } else {
            errorLabel.setText("Email or password is wrong! Please try again!");
        }

    }


}
