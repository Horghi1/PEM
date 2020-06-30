package sample.profile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import repository.UserRepositoryImpl;
import service.UserService;
import util.Context;
import util.FormValidator;
import util.PasswordHashing;
import util.exceptions.InvalidEmailException;
import util.exceptions.InvalidNameException;
import util.exceptions.InvalidPasswordException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Label returnLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField firstNameTextField;


    @FXML
    private TextField lastNameTextField;


    @FXML
    private TextField emailTextField;


    @FXML
    private TextField oldPasswordTextField;


    @FXML
    private TextField newPasswordTextField;


    @FXML
    private TextField confirmPasswordTextField;

    private UserService userService;

    public ProfileController() {
        this.userService = new UserService(new UserRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = Context.getInstance().getUser();

        this.firstNameTextField.setText(user.getFirstName());
        this.lastNameTextField.setText(user.getLastName());
        this.emailTextField.setText(user.getEmail());
    }

    @FXML
    public void pressUpdateButton() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        this.errorLabel.setTextFill(Color.rgb(255, 0, 0));
        this.errorLabel.setText("");

        try {
            String dbPassword =  Context.getInstance().getUser().getPassword();
            if(!PasswordHashing.checkPassword(oldPassword, dbPassword)) {
                throw new InvalidPasswordException("Old password is wrong! Insert again!");
            } else {
                FormValidator.validateName(firstName);
                FormValidator.validateName(lastName);
                FormValidator.validateEmail(email);

                if(!newPassword.equals("")) {
                    FormValidator.validatePassword(newPassword);
                    FormValidator.validatePasswords(newPassword, confirmPassword);
                }
            }

        } catch (InvalidNameException | InvalidEmailException | InvalidPasswordException e) {
            this.errorLabel.setText(e.getMessage());
            return;
        }

        User updatedUser;
        if(newPassword.equals("")) {
            updatedUser = new User(Context.getInstance().getUser().getId(), firstName, lastName, email, Context.getInstance().getUser().getPassword());
        } else {
            updatedUser = new User(Context.getInstance().getUser().getId(), firstName, lastName, email, PasswordHashing.hashPassword(newPassword));
        }

        boolean updated = this.userService.update(updatedUser);
        if(updated) {
            Context.getInstance().setUser(updatedUser);
            this.errorLabel.setTextFill(Color.rgb(0, 128, 0));
            this.errorLabel.setText("User has been updated!");
        } else {
            this.errorLabel.setText("User can't be updated");
        }
    }

    @FXML
    public void pressDeleteAccountButton() {
        String password = oldPasswordTextField.getText();
        if(!PasswordHashing.checkPassword(password, Context.getInstance().getUser().getPassword())) {
            this.errorLabel.setText("Incorect Password");
            return;
        }

        boolean deleted = this.userService.delete(Context.getInstance().getUser().getId());
        if(deleted) {
            changeWindow("../login/sample.fxml");
        } else {
            this.errorLabel.setText("User can't be deleted!");
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
