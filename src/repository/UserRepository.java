package repository;

import model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);

    boolean save(User user);

    boolean update(User user);

}
