CREATE TABLE IF NOT EXISTS wiki_page_revision (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    page_id VARCHAR(64) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(80),
    CONSTRAINT fk_revision_page FOREIGN KEY (page_id) REFERENCES wiki_page(id) ON DELETE CASCADE
);
CREATE INDEX idx_wiki_page_revision_page_id ON wiki_page_revision(page_id);
