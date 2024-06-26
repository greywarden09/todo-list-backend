package pl.greywarden.tutorial.domain.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;

@Introspected
@Serdeable.Serializable
public record Task(
        String id,
        String title,
        String description,
        LocalDate dueDate,
        boolean finished) {
}
