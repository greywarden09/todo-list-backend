package pl.greywarden.tutorial.repository;

import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import pl.greywarden.tutorial.domain.dto.CreateTagRequest;
import pl.greywarden.tutorial.domain.dto.Tag;
import pl.greywarden.tutorial.service.HashIdGenerator;

import java.util.List;

import static pl.greywarden.tutorial.jooq.Tables.TAGS;

@Singleton
public class TagsRepository {
    @Inject
    private HashIdGenerator hashIdGenerator;

    @Inject
    private DSLContext dsl;

    @Connectable
    public List<Tag> getTagsByNames(List<String> names) {
        return dsl.selectFrom(TAGS)
                .where(TAGS.NAME.in(names))
                .fetchInto(Tag.class);
    }

    @Connectable
    public List<Tag> getAllTags() {
        return dsl.selectFrom(TAGS)
                .fetchInto(Tag.class);
    }

    @Connectable
    public Tag getTagById(String id) {
        return dsl.selectFrom(TAGS)
                .where(TAGS.ID.eq(id))
                .fetchOneInto(Tag.class);
    }

    @Connectable
    public Tag createTag(CreateTagRequest createTagRequest) {
        return dsl.insertInto(TAGS)
                .set(TAGS.ID, hashIdGenerator.generateHashId())
                .set(TAGS.NAME, createTagRequest.name())
                .set(TAGS.COLOR, createTagRequest.color())
                .returning()
                .fetchOneInto(Tag.class);
    }
}
