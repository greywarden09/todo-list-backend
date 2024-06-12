package pl.greywarden.tutorial.domain.dto;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Serializable
public record TasksList(String id, String name, String color) {
}
