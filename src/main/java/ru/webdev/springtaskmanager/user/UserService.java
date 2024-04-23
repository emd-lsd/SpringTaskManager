package ru.webdev.springtaskmanager.user;

public interface UserService {
    User createUser(User user);

    void deleteUser(long id);

    User updateUser(long adminId, long id, User user);

    User getUserById(long id);
}
