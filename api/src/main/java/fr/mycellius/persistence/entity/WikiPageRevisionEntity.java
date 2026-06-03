package fr.mycellius.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "wiki_page_revision")
public class WikiPageRevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "page_id", nullable = false, length = 64)
    private String pageId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "created_by", length = 80)
    private String createdBy;

    public WikiPageRevisionEntity() {}

    public Long getId() { return id; }
    public String getPageId() { return pageId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }
    public String getCreatedBy() { return createdBy; }

    public void setId(Long id) { this.id = id; }
    public void setPageId(String pageId) { this.pageId = pageId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
