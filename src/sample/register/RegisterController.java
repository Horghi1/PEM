package sample.register;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import repository.UserRepositoryImpl;
import service.UserService;
import util.FormValidator;
import util.exceptions.InvalidEmailException;
import util.exceptions.InvalidNameException;
import util.exceptions.InvalidPasswordException;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Button submitButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Label errorLabel;

    private final String LOGIN_RESOURCE_URL = "../login/sample.fxml";

    public UserService userService;

    public RegisterController() {
        this.userService = new UserService(new UserRepositoryImpl());
    }

    @FXML
    public void pressloginLabel() {
        this.changeWindow(this.LOGIN_RESOURCE_URL);
    }

    @FXML
    public void pressSubmitButton() {
        /*
        Pasul 1: ne luam datele din text field-uri
        Pasul 2: validam datele: name, email, password
        Pasul 3: verificam daca ami exista un user in baza de date cu aceeasi adresa de email
        Pasul 4: introducem user-ul in baza de date
         */
        // Pasul 1
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        // Pasul 2
        this.errorLabel.setText("");

        try {
            FormValidator.validateName(firstName);
            FormValidator.validateName(lastName);
            FormValidator.validateEmail(email);
            FormValidator.validatePassword(password);
            FormValidator.validatePasswords(password, confirmPassword);
        } catch (InvalidNameException | InvalidEmailException | InvalidPasswordException e) {
            this.errorLabel.setText(e.getMessage());
            return;
        }

        // Pasul 3
        if(this.userService.existUser(email)) {
            this.errorLabel.setText("User with " + email + " already exist in database");
            return;
        }

        // Pasul 4
        boolean isSaved = this.userService.save(new User(firstName, lastName, email, password));
        if(isSaved) {
            this.changeWindow(this.LOGIN_RESOURCE_URL);
        } else {
            this.errorLabel.setText("The user can't be saved in database");
        }

    }


    private void changeWindow(String resourceURL) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(resourceURL));
            Stage stage = (Stage) submitButton.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
