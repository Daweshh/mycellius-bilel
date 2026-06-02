package fr.mycellius.web.controller;

import fr.mycellius.service.RevisionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pages")
public class RevisionController {
    private final RevisionService revisionService;

    public RevisionController(RevisionService revisionService) {
        this.revisionService = revisionService;
    }

    @GetMapping("/{id}/revisions")
    public List<RevisionService.RevisionSnapshot> listRevisions(@PathVariable String id) {
        return revisionService.list(id);
    }
}
