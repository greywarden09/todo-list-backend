package pl.greywarden.tutorial.resource;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import pl.greywarden.tutorial.domain.dto.CreateTagRequest;
import pl.greywarden.tutorial.domain.dto.Tag;
import pl.greywarden.tutorial.service.TagsService;

import java.util.List;

@Controller("/tags")
public class TagsResource {
    @Inject
    private TagsService tagsService;

    @Get
    List<Tag> getAllTags() {
        return tagsService.getAllTags();
    }

    @Get("/{id}")
    Tag getTagById(String id) {
        return tagsService.getTagById(id);
    }

    @Post
    HttpResponse<Tag> createTag(@Body CreateTagRequest createTagRequest) {
        return HttpResponse.created(tagsService.createTag(createTagRequest));
    }

    @Delete("/{id}")
    HttpResponse<Void> deleteTag(String id) {
        tagsService.deleteTag(id);
        return HttpResponse.noContent();
    }
}
