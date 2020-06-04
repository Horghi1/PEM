package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import repository.UserRepositoryImpl;
import service.UserService;

import java.io.IOException;


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

    public Controller() {
        this.userService = new UserService(new UserRepositoryImpl());
    }

//    public Controller(UserService userService) {
//        this.userService = userService;
//    }

    @FXML
    public void pressLoginButton() {
        this.errorLabel.setText("");

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if(email.equals("") || password.equals("")) {
            errorLabel.setText("Please complete all fields!");
            return;
        }

        User user = userService.loginUser(email, password);
        if(user != null) {
            try {
                Parent pane = FXMLLoader.load(getClass().getResource("expense/expense.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(pane);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            errorLabel.setText("Email or password is wrong! Please try again!");
        }

    }

    @FXML
    public void pressRegisterLabel() {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("register/register.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
