CREATE TABLE wiki_page_tag (
                               page_id VARCHAR(50) NOT NULL,
                               tag_id BIGINT NOT NULL,
                               PRIMARY KEY (page_id, tag_id),
                               CONSTRAINT fk_wiki_page
                                   FOREIGN KEY (page_id)
                                       REFERENCES wiki_page(id)
                                       ON DELETE CASCADE,
                               CONSTRAINT fk_tag
                                   FOREIGN KEY (tag_id)
                                       REFERENCES tag(id)
                                       ON DELETE CASCADE
);
