package pl.greywarden.tutorial.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import pl.greywarden.tutorial.domain.dao.TasksDAO;
import pl.greywarden.tutorial.domain.dto.Task;

import java.util.Collection;

@Singleton
public class TasksService {
    @Inject
    private TasksDAO tasksDAO;

    public Collection<Task> getAllTasks() {
        return tasksDAO.findAll();
    }
}
