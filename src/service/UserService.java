package service;

import model.User;
import repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) { // dependency injection
        this.userRepository = userRepository;
    }

    public User loginUser(String email, String password) {
        User user = this.userRepository.getUserByEmailAndPassword(email, password);

        if(user != null) {
            return user;
        }

        return null;
    }

    public boolean existUser(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

}
