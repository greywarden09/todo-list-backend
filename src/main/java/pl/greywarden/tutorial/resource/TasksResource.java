package pl.greywarden.tutorial.resource;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import pl.greywarden.tutorial.domain.dao.TasksDAO;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.dto.Task;
import pl.greywarden.tutorial.service.TasksService;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller("/tasks")
public class TasksResource {
    @Inject
    private TasksDAO tasksDAO;
    @Inject
    private TasksService tasksService;

    @Get
    Collection<Task> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @Get("/today")
    List<Task> getTodayTasks() {
        return Collections.emptyList();
    }

    @Get("/lists")
    Set<String> getAllLists() {
        return Collections.emptySet();
    }

    @Get("/tags")
    Set<String> getAllTags() {
        return Collections.emptySet();
    }

    @Post
    HttpResponse<Void> createTask(@Body CreateTaskRequest createTaskRequest) {
        final var taskId = tasksDAO.save(createTaskRequest);
        return HttpResponse.created(URI.create("/tasks/" + taskId));
    }
}
