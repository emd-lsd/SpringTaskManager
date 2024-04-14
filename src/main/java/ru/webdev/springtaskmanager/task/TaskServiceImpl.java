package ru.webdev.springtaskmanager.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdev.springtaskmanager.board.Board;
import ru.webdev.springtaskmanager.board.BoardRepository;
import ru.webdev.springtaskmanager.exception.InsufficientPermissionException;
import ru.webdev.springtaskmanager.exception.NotFoundDataException;
import ru.webdev.springtaskmanager.user.Role;
import ru.webdev.springtaskmanager.user.User;
import ru.webdev.springtaskmanager.user.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final UserRepository userRepository;
    public final TaskRepository taskRepository;
    public final BoardRepository boardRepository;

    @Override
    public Task createTask(long userId, long boardId, Task task) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundDataException("User with id " + userId + " not found"));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundDataException("Board with id " + boardId + " not found"));
        if (user.getRole().getLevel() > 1) {
            task.setStatus(Status.OPEN);
            task.setCreator(user);
            task.setBoard(board);
            return taskRepository.save(task);
        } else
            throw new InsufficientPermissionException("User with id " + userId + " has no permission to create task");
    }

    @Override
    public Task updateTask(long userId, long taskId, Task task) {
        if (userRepository.findById(userId).isPresent()) {
            if (taskRepository.findById(taskId).isPresent()) {
                Task taskFromDb = taskRepository.findById(taskId).get();
                if (task.getAssignee() != null) {
                    User assignee = userRepository.findById(task.getAssignee().getId()).orElseThrow(() ->
                            new NotFoundDataException("User with id " + task.getAssignee().getId() + " not found"));
                    taskFromDb.setAssignee(assignee);
                }
                if (task.getStatus() != null) {
                    taskFromDb.setStatus(task.getStatus());
                }
                return taskRepository.save(taskFromDb);
            } else throw new NotFoundDataException("Task with id " + taskId + " not found");
        } else throw new NotFoundDataException("User with id " + userId + " not found");
    }

    @Override
    public Task deleteTask(long userId, long taskId) {
        if (userRepository.findById(userId).isPresent()) {
            if (userRepository.findById(userId).get().getRole().equals(Role.ADMIN)) {
                if (taskRepository.findById(taskId).isPresent()) {
                    return taskRepository.deleteById(taskId);
                } else throw new NotFoundDataException("Task with id " + taskId + " not found");
            } else
                throw new InsufficientPermissionException("User with id " + userId + " has no permission to delete task");
        } else throw new NotFoundDataException("User with id " + userId + " not found");
    }

    @Override
    public Collection<Task> getAllTasksByBoard(long userId, long boardId) {
        if (userRepository.findById(userId).isPresent()) {
            if (boardRepository.findById(boardId).isPresent()) {
                return taskRepository.findAllByBoardId(boardId);
            } else throw new NotFoundDataException("Board with id " + boardId + " not found");
        } else throw new NotFoundDataException("User with id " + userId + " not found");
    }

}
