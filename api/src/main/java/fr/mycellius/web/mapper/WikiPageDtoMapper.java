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
    // MapStruct mappe id/title/content automatiquement si les noms correspondent
    WikiPage toDomain(CreateWikiPageRequest request);
    // DTO -> Domain (Tag immuable : on construit un nouvel objet)
    default Tag toDomain(TagRequest request) {
        if (request == null) return null;
        return new Tag(request.name());
    }
    // Domain -> DTO
    // - createdAt ignoré tant que le domaine ne le porte pas
    // - tags renvoyés vides tant que WikiPage n'expose pas une liste de tags
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tags", expression = "java(java.util.List.of())")
    WikiPageResponse toResponse(WikiPage page);
}