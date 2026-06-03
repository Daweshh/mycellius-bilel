package fr.mycellius.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_log")
public class AuditLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "action", nullable = false, length = 32)
    private String action;
    @Column(name = "page_id", length = 64)
    private String pageId;
    @Column(name = "actor", length = 80)
    private String actor;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public AuditLogEntity() {}

    public Long getId() { return id; }
    public String getAction() { return action; }
    public String getPageId() { return pageId; }
    public String getActor() { return actor; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setAction(String action) { this.action = action; }
    public void setPageId(String pageId) { this.pageId = pageId; }
    public void setActor(String actor) { this.actor = actor; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
