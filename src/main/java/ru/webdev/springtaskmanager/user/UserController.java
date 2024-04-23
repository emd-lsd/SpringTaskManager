package ru.webdev.springtaskmanager.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Saving user {}", user);
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Deleting user with id {}", id);
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@RequestHeader("X-Board-Creator-Id") long adminId,
                           @PathVariable long id,
                           @Valid @RequestBody User user) {
        log.info("Updating user with id {}", id);
        return userService.updateUser(adminId, id, user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info("Getting user with id {}", id);
        return userService.getUserById(id);
    }

}
