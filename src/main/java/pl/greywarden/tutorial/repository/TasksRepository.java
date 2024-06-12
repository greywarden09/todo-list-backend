package pl.greywarden.tutorial.repository;

import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.dto.Task;
import pl.greywarden.tutorial.domain.dto.TasksList;
import pl.greywarden.tutorial.domain.mapper.TasksMapper;
import pl.greywarden.tutorial.service.HashIdGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static pl.greywarden.tutorial.jooq.Tables.TASKS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS_LISTS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS_TAGS;

@Singleton
public class TasksRepository {
    @Inject
    private DSLContext dsl;

    @Inject
    private TasksMapper tasksMapper;

    @Inject
    private TagsRepository tagsRepository;

    @Inject
    private HashIdGenerator hashIdGenerator;

    @Inject
    private TasksListsRepository tasksListsRepository;

    @Connectable
    public List<Task> getAllTasks() {
        return dsl.selectFrom(TASKS)
                .fetch(tasksMapper);
    }

    @Connectable
    public Task getTaskById(String id) {
        return dsl.selectFrom(TASKS).where(TASKS.ID.eq(id))
                .fetchOne(tasksMapper);
    }

    @Connectable
    public List<Task> getUpcomingTasks() {
        return dsl.selectFrom(TASKS)
                .where(TASKS.DUE_DATE.gt(LocalDate.now()))
                .fetch(tasksMapper);
    }

    @Connectable
    public Task createTask(CreateTaskRequest createTaskRequest) {
        return dsl.transactionResult(configuration -> {
            final var taskId = hashIdGenerator.generateHashId();
            final var list = tasksListsRepository.findByName(createTaskRequest.list());

            final var tags = tagsRepository.getTagsByNames(createTaskRequest.tags());
            final var task = configuration.dsl().insertInto(TASKS)
                    .set(TASKS.ID, taskId)
                    .set(TASKS.TITLE, createTaskRequest.title())
                    .set(TASKS.DESCRIPTION, createTaskRequest.description())
                    .set(TASKS.LIST_ID, Optional.ofNullable(list).map(TasksList::id).orElse(null))
                    .set(TASKS.DUE_DATE, LocalDate.parse(createTaskRequest.dueDate()))
                    .returning()
                    .fetchOne(tasksMapper);

            for (final var tag : tags) {
                dsl.insertInto(TASKS_TAGS)
                        .set(TASKS_TAGS.TASK_ID, Objects.requireNonNull(task).id())
                        .set(TASKS_TAGS.TAG_ID, tag.id())
                        .execute();
            }
            return task;
        });
    }

    @Connectable
    public List<Task> getTodayTasks() {
        return dsl.selectFrom(TASKS)
                .where(TASKS.DUE_DATE.eq(LocalDate.now()))
                .fetch(tasksMapper);
    }


    @Connectable
    public TasksList getTaskList(String id) {
        return dsl.select(TASKS_LISTS.ID, TASKS_LISTS.NAME, TASKS_LISTS.COLOR)
                .from(TASKS).leftJoin(TASKS_LISTS)
                .on(TASKS.LIST_ID.eq(TASKS_LISTS.ID))
                .where(TASKS.ID.eq(id))
                .fetchOneInto(TasksList.class);
    }
}
