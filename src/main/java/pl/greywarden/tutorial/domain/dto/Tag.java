package pl.greywarden.tutorial.domain.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import pl.greywarden.tutorial.jooq.tables.pojos.Tags;

@Introspected
@Serdeable.Serializable
public record Tag(String id, String name, String color) {

}
