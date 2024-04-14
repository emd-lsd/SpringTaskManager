package ru.webdev.springtaskmanager.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping()
    public Task createTask(@RequestHeader("X-Task-Creator-Id") long userId,
                           @RequestHeader("X-Board-Id") long boardId,
                           @Valid @RequestBody Task task) {
        log.info("Creating task: {}", task);
        return taskService.createTask(userId, boardId, task);
    }

    @PatchMapping("/{taskId}")
    public Task updateTask(@RequestHeader("X-Task-Creator-Id") long userId,
                           @PathVariable long taskId,
                           @Valid @RequestBody Task task) {
        log.info("Updating task with id: {}", taskId);
        return taskService.updateTask(userId, taskId, task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@RequestHeader("X-Task-Creator-Id") long userId,
                           @PathVariable long taskId) {
        log.info("Deleting task with id: {}", taskId);
        taskService.deleteTask(userId, taskId);
    }

    @GetMapping
    public Collection<Task> getAllTasksByBoard(@RequestHeader("X-Board-Id") long userId,
                                               @RequestParam("boardId") long boardId) {
        log.info("Getting all tasks by board id: {}", boardId);
        return taskService.getAllTasksByBoard(userId, boardId);
    }
}
