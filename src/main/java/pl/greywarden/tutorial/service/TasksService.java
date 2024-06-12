package pl.greywarden.tutorial.service;

import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.dto.Tag;
import pl.greywarden.tutorial.domain.dto.Task;
import pl.greywarden.tutorial.domain.dto.TasksList;
import pl.greywarden.tutorial.repository.TasksRepository;

import java.util.List;

import static pl.greywarden.tutorial.jooq.Tables.TAGS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS_TAGS;

@Singleton
public class TasksService {
    @Inject
    private DSLContext dsl;

    @Inject
    private TasksRepository tasksRepository;

    public List<Task> getAllTasks() {
        return tasksRepository.getAllTasks();
    }

    public Task getTaskById(String id) {
        return tasksRepository.getTaskById(id);
    }

    public List<Task> getUpcomingTasks() {
        return tasksRepository.getUpcomingTasks();
    }

    public List<Task> getTodayTasks() {
        return tasksRepository.getTodayTasks();
    }

    public Task createTask(CreateTaskRequest createTaskRequest) {
        return tasksRepository.createTask(createTaskRequest);
    }

    @Connectable
    public List<Tag> getTaskTags(String id) {
        return dsl.select(TAGS.ID, TAGS.NAME, TAGS.COLOR)
                        .from(TASKS_TAGS)
                        .leftJoin(TAGS).on(TASKS_TAGS.TAG_ID.eq(TAGS.ID))
                        .where(TASKS_TAGS.TASK_ID.eq(id))
                .fetchInto(Tag.class);
    }

    public TasksList getTaskList(String id) {
        return tasksRepository.getTaskList(id);
    }
}
