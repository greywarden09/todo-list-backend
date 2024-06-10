package pl.greywarden.tutorial.domain.dao;

import io.micronaut.core.util.CollectionUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.dto.Tag;
import pl.greywarden.tutorial.domain.dto.Task;
import pl.greywarden.tutorial.service.DatabaseConnectionProvider;
import pl.greywarden.tutorial.service.HashIdGenerator;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static pl.greywarden.tutorial.jooq.Tables.TAGS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS_LISTS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS_TAGS;
import static pl.greywarden.tutorial.jooq.Tables.TASKS_VIEW;

@Singleton
public class TasksDAO {
    @Inject
    private DatabaseConnectionProvider connectionProvider;

    @Inject
    private HashIdGenerator hashIdGenerator;

    public String save(CreateTaskRequest createTaskRequest) {
        final var id = generateId();
        final var dueDate = parseDueDate(createTaskRequest.dueDate());
        try (final var connection = connectionProvider.getConnection()) {
            final var context = DSL.using(connection, SQLDialect.POSTGRES);
            final var listId = getListId(createTaskRequest, context);
            final var tags = getTags(createTaskRequest, context);

            context.transaction((ctx) -> {
                ctx.dsl().insertInto(TASKS)
                        .set(TASKS.ID, id)
                        .set(TASKS.TITLE, createTaskRequest.title())
                        .set(TASKS.DESCRIPTION, createTaskRequest.description())
                        .set(TASKS.DUE_DATE, dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .set(TASKS.LIST_ID, listId)
                        .set(TASKS.FINISHED, false)
                        .execute();
                for (var tag : tags) {
                    ctx.dsl().insertInto(TASKS_TAGS)
                            .set(TASKS_TAGS.TASK_ID, id)
                            .set(TASKS_TAGS.TAG_ID, tag)
                            .execute();
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    private List<String> getTags(CreateTaskRequest createTaskRequest, DSLContext context) {
        if (CollectionUtils.isEmpty(createTaskRequest.tags())) {
            return Collections.emptyList();
        } else {
            return context.select(TAGS.ID).from(TAGS)
                    .where(TAGS.NAME.in(createTaskRequest.tags()))
                    .fetch()
                    .stream()
                    .map(Record1::value1)
                    .toList();
        }
    }

    private String getListId(CreateTaskRequest createTaskRequest, DSLContext context) {
        return Optional.ofNullable(createTaskRequest.list()).map(listName ->
                        Objects.requireNonNull(context.select(TASKS_LISTS.ID)
                                .from(TASKS_LISTS)
                                .where(TASKS_LISTS.NAME.eq(listName))
                                .fetchOne()).value1())
                .orElse(null);
    }

    public Collection<Task> findAll() {
        try (final var connection = connectionProvider.getConnection()) {
            final var context = DSL.using(connection, SQLDialect.POSTGRES);
            return context.select().from(TASKS_VIEW).fetch()
                    .stream()
                    .map(record -> {
                        final var tags = context.selectFrom(TAGS)
                                .where(TAGS.ID.in(record.getValue(TASKS_VIEW.TAGS)))
                                .fetchInto(Tag.class);
                        return new Task(
                                record.getValue(TASKS_VIEW.ID),
                                record.getValue(TASKS_VIEW.TITLE),
                                record.getValue(TASKS_VIEW.DESCRIPTION),
                                tags,
                                record.getValue(TASKS_VIEW.LISTNAME),
                                record.getValue(TASKS_VIEW.DUE_DATE),
                                record.getValue(TASKS_VIEW.FINISHED));
                    }).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateId() {
        return hashIdGenerator.generateHashId();
    }

    private Date parseDueDate(String dueDate) {
        final var format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dueDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse due date: " + dueDate);
        }
    }

}
