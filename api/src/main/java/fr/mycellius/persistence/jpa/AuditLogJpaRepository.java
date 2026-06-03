package fr.mycellius.persistence.jpa;

import fr.mycellius.persistence.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogEntity, Long> {
    List<AuditLogEntity> findByPageIdOrderByCreatedAtDesc(String pageId);
}
