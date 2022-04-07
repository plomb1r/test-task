package application.dao;

import application.model.User;

import java.util.List;

public interface UserDao {
    List<User> allUsers();

    void add(User user);

    void remove(User user);

    void edit(User user);

    User getById(long id);

    User findByUsername(String username);
}
