package pl.greywarden.tutorial.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import pl.greywarden.tutorial.domain.dto.CreateTagRequest;
import pl.greywarden.tutorial.domain.dto.Tag;
import pl.greywarden.tutorial.repository.TagsRepository;

import java.util.List;

@Singleton
public class TagsService {
    @Inject
    private TagsRepository tagsRepository;

    public List<Tag> getAllTags() {
        return tagsRepository.getAllTags();
    }

    public Tag getTagById(String id) {
        return tagsRepository.getTagById(id);
    }

    public Tag createTag(CreateTagRequest createTagRequest) {
        return tagsRepository.createTag(createTagRequest);
    }
}
