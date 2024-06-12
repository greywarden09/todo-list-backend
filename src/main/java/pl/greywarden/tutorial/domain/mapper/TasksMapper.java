package pl.greywarden.tutorial.domain.mapper;

import jakarta.inject.Singleton;
import org.jetbrains.annotations.Nullable;
import org.jooq.RecordMapper;
import pl.greywarden.tutorial.domain.dto.Task;
import pl.greywarden.tutorial.jooq.tables.records.TasksRecord;

@Singleton
public class TasksMapper implements RecordMapper<TasksRecord, Task> {
    @Override
    public @Nullable Task map(TasksRecord tasksRecord) {
        return new Task(
                tasksRecord.getId(),
                tasksRecord.getTitle(),
                tasksRecord.getDescription(),
                tasksRecord.getDueDate(),
                tasksRecord.getFinished());
    }
}
