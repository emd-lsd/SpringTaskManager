package ru.webdev.springtaskmanager.task;

import java.util.Collection;

public interface TaskService {
    Task createTask(long userId, long boadrId, Task task);

    Task updateTask(long userId, long taskId, Task task);

    Task deleteTask(long userId, long taskId);

    Collection<Task> getAllTasksByBoard(long userId, long boardId);
}
