package fr.mycellius.web.controller;

import fr.mycellius.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/pages/{id}")
    public List<AuditService.AuditEntry> listByPage(@PathVariable String id) {
        return auditService.listByPageId(id);
    }
}
