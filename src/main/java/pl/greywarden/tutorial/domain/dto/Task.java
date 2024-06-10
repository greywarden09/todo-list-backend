package pl.greywarden.tutorial.domain.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;
import java.util.List;

@Introspected
@Serdeable.Serializable
public record Task(
        String id,
        String title,
        String description,
        List<Tag> tags,
        String list,
        LocalDate dueDate,
        boolean finished) {
}
