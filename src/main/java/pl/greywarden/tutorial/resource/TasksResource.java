package pl.greywarden.tutorial.resource;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import pl.greywarden.tutorial.domain.dao.TasksDAO;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.entity.Task;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller("/tasks")
public class TasksResource {
    private final TasksDAO tasksDAO;

    @Inject
    public TasksResource(TasksDAO tasksDAO) {
        this.tasksDAO = tasksDAO;
    }

    @Get
    Collection<Task> getAllTasks() {
        return tasksDAO.findAll();
    }

    @Get("/today")
    List<Task> getTodayTasks() {
        return tasksDAO.getTodayTasks();
    }

    @Get("/lists")
    Set<String> getAllLists() {
        return tasksDAO.getAllLists();
    }

    @Get("/tags")
    Set<String> getAllTags() {
        return tasksDAO.getAllTags();
    }

    @Post
    Task createTask(@Body CreateTaskRequest createTaskRequest) {
        return tasksDAO.save(createTaskRequest);
    }
}
