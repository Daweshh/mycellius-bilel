package fr.mycellius.service;

import fr.mycellius.persistence.entity.AuditLogEntity;
import fr.mycellius.persistence.jpa.AuditLogJpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {
    private final AuditLogJpaRepository auditJpa;

    public AuditService(AuditLogJpaRepository auditJpa) {
        this.auditJpa = auditJpa;
    }

    public void log(String action, String pageId, String actor) {
        AuditLogEntity e = new AuditLogEntity();
        e.setAction(action);
        e.setPageId(pageId);
        e.setActor(actor);
        e.setCreatedAt(Instant.now());
        auditJpa.save(e);
    }

    public List<AuditEntry> listByPageId(String pageId) {
        return auditJpa.findByPageIdOrderByCreatedAtDesc(pageId).stream()
                .map(a -> new AuditEntry(a.getId(), a.getAction(), a.getPageId(), a.getActor(), a.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public record AuditEntry(Long id, String action, String pageId, String actor, Instant createdAt) {}
}
