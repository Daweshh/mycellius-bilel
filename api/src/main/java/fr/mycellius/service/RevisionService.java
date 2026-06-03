package fr.mycellius.service;

import fr.mycellius.domain.WikiPage;
import fr.mycellius.persistence.entity.WikiPageRevisionEntity;
import fr.mycellius.persistence.jpa.WikiPageRevisionJpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevisionService {
    private final WikiPageRevisionJpaRepository revisionJpa;

    public RevisionService(WikiPageRevisionJpaRepository revisionJpa) {
        this.revisionJpa = revisionJpa;
    }

    public void createSnapshot(WikiPage page, String username) {
        if (page == null) return;
        WikiPageRevisionEntity rev = new WikiPageRevisionEntity();
        rev.setPageId(page.getId());
        rev.setTitle(page.getTitle());
        rev.setContent(page.getContent());
        rev.setCreatedAt(Instant.now());
        rev.setCreatedBy(username);
        revisionJpa.save(rev);
    }

    public List<RevisionSnapshot> list(String pageId) {
        return revisionJpa.findByPageIdOrderByCreatedAtDesc(pageId).stream()
                .map(e -> new RevisionSnapshot(
                        e.getId(),
                        e.getPageId(),
                        e.getTitle(),
                        e.getContent(),
                        e.getCreatedAt(),
                        e.getCreatedBy()))
                .collect(Collectors.toList());
    }

    public record RevisionSnapshot(Long id, String pageId, String title, String content, Instant createdAt, String createdBy) {}
}
