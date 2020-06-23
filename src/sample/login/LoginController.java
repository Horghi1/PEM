package sample.login;

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
import util.Context;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    private UserService userService;

    public LoginController() {
        this.userService = new UserService(new UserRepositoryImpl());
    }

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
            Context.getInstance().setUser(user);
            changeWindow("../expense/expense.fxml");
        } else {
            errorLabel.setText("Email or password is wrong! Please try again!");
        }

    }

    @FXML
    public void pressRegisterLabel() {
        changeWindow("../register/register.fxml");
    }

    private void changeWindow(String resourceURL) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(resourceURL));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
