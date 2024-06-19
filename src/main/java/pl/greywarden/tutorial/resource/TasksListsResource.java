package pl.greywarden.tutorial.resource;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import pl.greywarden.tutorial.domain.dto.TasksList;
import pl.greywarden.tutorial.service.TasksListsService;

import java.util.List;

@Controller("/lists")
public class TasksListsResource {
    @Inject
    private TasksListsService tasksListsService;

    @Get
    List<TasksList> getAllTasksLists() {
        return tasksListsService.getAllTasksLists();
    }
}
