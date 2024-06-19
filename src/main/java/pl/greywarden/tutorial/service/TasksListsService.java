package pl.greywarden.tutorial.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import pl.greywarden.tutorial.domain.dto.TasksList;
import pl.greywarden.tutorial.repository.TasksListsRepository;

import java.util.List;

@Singleton
public class TasksListsService {
    @Inject
    private TasksListsRepository repository;

    public List<TasksList> getAllTasksLists() {
        return repository.getAllTasksLists();
    }
}
