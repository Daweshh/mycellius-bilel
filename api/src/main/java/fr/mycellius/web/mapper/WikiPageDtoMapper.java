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
    @Mapping(target = "createdAt", ignore = true)
    WikiPage toDomain(CreateWikiPageRequest request);

    // DTO -> Domain pour 1 tag
    default Tag toDomain(TagRequest request) {
        if (request == null) return null;
        return new Tag(request.name());
    }

    // Domain -> DTO
    WikiPageResponse toResponse(WikiPage page);

    // Conversion Tag -> String pour les tags en sortie
    default String map(Tag tag) {
        return tag == null ? null : tag.getValue();
    }
}
