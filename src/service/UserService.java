package service;

import javafx.scene.control.PasswordField;
import model.User;
import repository.UserRepository;
import util.PasswordHashing;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) { // dependency injection
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    public User loginUser(String email, String password) {
        User user = this.userRepository.getUserByEmail(email);

        if(user != null) {
            boolean passwordMatch = PasswordHashing.checkPassword(password, user.getPassword());
            if(passwordMatch) {
                return user;
            }
        }

        return null;
    }

    public boolean existUser(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

    public boolean save(User user) {
        user.setPassword(PasswordHashing.hashPassword(user.getPassword()));
        return this.userRepository.save(user);
    }

    public boolean update(User user) {
        return this.userRepository.update(user);
    }

}
