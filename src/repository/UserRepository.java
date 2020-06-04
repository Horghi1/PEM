package repository;

import model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);

}
