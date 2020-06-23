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
        if(!checkName(firstName) || firstName.length() > 45) {
            this.errorLabel.setText("Invalid first name! First name should contains just letters and maximum 45 letters.");
            return;
        }

        if(!checkName(lastName) || lastName.length() > 45) {
            this.errorLabel.setText("Invalid last name! Last name should contains just letters and maximum 45 letters.");
            return;
        }

        if(!checkEmail(email) || email.length() > 100) {
            this.errorLabel.setText("Invalid email!");
            return;
        }

        if(!isValidPassword(password)) {
            this.errorLabel.setText("Password should have at least six characters and two numbers characters ");
            return;
        }

        if(!password.equals(confirmPassword)) {
            this.errorLabel.setText("Invalid Password....characters are not the same");
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

    private boolean checkName(String name){
        for(int i = 0; i< name.length(); i++){
            if((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')) {
                // do nothing
            } else {
                return false;
            }
            // echivalent cu...
//            if(!((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))) {
//                return false;
//            }
        }
        return true;
    }

    private boolean checkEmail (String email){
        for(int i = 0; i<email.length(); i++){
            if((email.charAt(i) >= 'a' && email.charAt(i) <= 'z') || (email.charAt(i) >= '0' && email.charAt(i) <= '9') ||
                    email.charAt(i) == '_' || email.charAt(i) == '.' || email.charAt(i) == '@') {
                //do nothing
            } else {
                return false;
            }
        }

        if(!email.contains("@")) {
            return false;
        }

        if(!email.contains(".")) {
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            return false;
        }

        int countChar = 0;
        int countDigits = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (ch >= '0' && ch <= '9') {
                countDigits++;
            } else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                countChar++;
            } else {
                return false;
            }
        }

        return countChar >= 1 && countDigits >= 2;
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
