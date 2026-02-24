package fr.mycellius.web.mapper;

import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.web.dto.CreateWikiPageRequest;
import fr.mycellius.web.dto.TagRequest;
import fr.mycellius.web.dto.WikiPageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WikiPageDtoMapper {

    // DTO -> Domain
    @Mapping(target = "createdAt", ignore = true) // si WikiPage n'a pas createdAt, enlève cette ligne
    WikiPage toDomain(CreateWikiPageRequest request);

    // DTO -> Domain pour 1 tag
    default Tag toDomain(TagRequest request) {
        if (request == null) return null;
        return new Tag(request.name());
    }

    // Domain -> DTO
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tags", expression = "java(java.util.List.of())")
    WikiPageResponse toResponse(WikiPage page);
}
