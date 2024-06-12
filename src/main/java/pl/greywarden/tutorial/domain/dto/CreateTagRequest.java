package pl.greywarden.tutorial.domain.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Deserializable
public record CreateTagRequest(String name, String color) {
}
