package pl.greywarden.tutorial.domain.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Introspected
@Serdeable.Deserializable
public record CreateTaskRequest(String title,
                                String description,
                                String dueDate,
                                List<String> tags,
                                String list,
                                boolean finished) {
}
