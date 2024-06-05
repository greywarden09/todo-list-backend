package pl.greywarden.tutorial.domain.dao;

import jakarta.inject.Singleton;
import pl.greywarden.tutorial.domain.dto.CreateTaskRequest;
import pl.greywarden.tutorial.domain.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class TasksDAO {
    private static final Map<String, Task> tasks = new HashMap<>();

    public Task save(CreateTaskRequest createTaskRequest) {
        final var id = generateId();

        final var task = new Task(id,
                createTaskRequest.title(),
                createTaskRequest.description(),
                createTaskRequest.tags(),
                createTaskRequest.list(),
                parseDueDate(createTaskRequest.dueDate()),
                createTaskRequest.finished());

        tasks.put(id, task);

        return task;
    }

    public Collection<Task> findAll() {
        return tasks.values();
    }

    public Optional<Task> findById(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private Date parseDueDate(String dueDate) {
        final var format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dueDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public Set<String> getAllLists() {
        return tasks.values().stream().map(Task::list).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public Set<String> getAllTags() {
        return tasks.values().stream()
                .flatMap(task -> task.tags().stream())
                .collect(Collectors.toSet());
    }

    public List<Task> getTodayTasks() {
        return tasks.values()
                .stream()
                .filter(task -> isToday(task.dueDate()))
                .toList();
    }


    public boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    private boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }
}
