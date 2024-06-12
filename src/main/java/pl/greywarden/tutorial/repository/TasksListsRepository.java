package pl.greywarden.tutorial.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import pl.greywarden.tutorial.domain.dto.TasksList;

import static pl.greywarden.tutorial.jooq.Tables.TASKS_LISTS;

@Singleton
public class TasksListsRepository {
    @Inject
    private DSLContext dsl;

    @Nullable
    public TasksList findByName(String name) {
        return dsl.selectFrom(TASKS_LISTS).where(TASKS_LISTS.NAME.eq(name)).fetchOneInto(TasksList.class);
    }
}
