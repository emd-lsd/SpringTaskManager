package ru.webdev.springtaskmanager.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webdev.springtaskmanager.user.User;

import java.util.Collection;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task deleteById(long taskId);

    Collection<Task> findAllByBoardId(long boardId);

}
