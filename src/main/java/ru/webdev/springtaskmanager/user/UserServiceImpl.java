package ru.webdev.springtaskmanager.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdev.springtaskmanager.exception.DuplicateLoginException;
import ru.webdev.springtaskmanager.exception.InsufficientPermissionException;
import ru.webdev.springtaskmanager.exception.NotFoundDataException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        try {
            user.setRole(Role.USER);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLoginException("User with login " + user.getLogin() + " already exists");
        }
    }

    @Override
    public void deleteUser(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else throw new NotFoundDataException("User with id " + id + " not found");
    }

    @Override
    public User updateUser(long adminId, long id, User user) {
        if (userRepository.findById(adminId).isPresent()) {
            if (userRepository.findById(adminId).get().getRole().equals(Role.ADMIN)) {
                User userFromDb = userRepository.findById(id).orElseThrow(() -> new NotFoundDataException("User with id " + id + " not found"));
                if (user.getRole() != null) {
                    userFromDb.setRole(user.getRole());
                }
                return userFromDb;
            } else throw new InsufficientPermissionException("User with id " + id + " has insufficient permission");
        } else throw new NotFoundDataException("User with id " + id + " not found");
    }

    @Override
    public User getUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else throw new NotFoundDataException("User with id " + id + " not found");
    }
}
