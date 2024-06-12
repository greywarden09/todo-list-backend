package pl.greywarden.tutorial.resource;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.dto.Tag;
import pl.greywarden.tutorial.domain.dto.Task;
import pl.greywarden.tutorial.domain.dto.TasksList;
import pl.greywarden.tutorial.service.TasksService;

import java.util.List;

@Controller("/tasks")
public class TasksResource {
    @Inject
    private TasksService tasksService;

    @Get
    List<Task> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @Get("/{id}")
    Task getTaskById(String id) {
        return tasksService.getTaskById(id);
    }

    @Get("/{id}/tags")
    List<Tag> getTaskTags(String id) {
        return tasksService.getTaskTags(id);
    }

    @Get("/{id}/list")
    TasksList getTaskList(String id) {
        return tasksService.getTaskList(id);
    }

    @Get("/today")
    List<Task> getTodayTasks() {
        return tasksService.getTodayTasks();
    }

    @Get("/upcoming")
    List<Task> getUpcomingTasks() {
        return tasksService.getUpcomingTasks();
    }

    @Post
    HttpResponse<Task> createTask(@Body CreateTaskRequest createTaskRequest) {
        return HttpResponse.created(tasksService.createTask(createTaskRequest));
    }
}
