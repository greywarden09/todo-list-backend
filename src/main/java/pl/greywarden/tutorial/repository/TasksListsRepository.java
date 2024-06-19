package pl.greywarden.tutorial.repository;

import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import pl.greywarden.tutorial.domain.dto.TasksList;

import java.util.List;

import static pl.greywarden.tutorial.jooq.Tables.TASKS_LISTS;

@Singleton
public class TasksListsRepository {
    @Inject
    private DSLContext dsl;

    @Nullable
    public TasksList findByName(String name) {
        return dsl.selectFrom(TASKS_LISTS).where(TASKS_LISTS.NAME.eq(name)).fetchOneInto(TasksList.class);
    }

    @Connectable
    public List<TasksList> getAllTasksLists() {
        return dsl.selectFrom(TASKS_LISTS).fetchInto(TasksList.class);
    }
}
