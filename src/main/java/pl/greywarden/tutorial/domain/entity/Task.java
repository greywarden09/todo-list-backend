package pl.greywarden.tutorial.domain.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Date;
import java.util.List;

@Introspected
@Serdeable.Serializable
public record Task(
        String id,
        String title,
        String description,
        List<String> tags,
        String list,
        Date dueDate,
        boolean finished) {
}
