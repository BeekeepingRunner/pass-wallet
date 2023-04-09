package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/user-actions/{userId}")
    public ResponseEntity<Collection<FunctionRun>> getUserActions(@PathVariable Long userId) {
        Collection<FunctionRun> userActions = auditService.getUserActions(userId);
        return ResponseEntity.ok(userActions);
    }

    @GetMapping("/password-record-changes/{recordId}")
    public ResponseEntity<?> getPasswordRecordChanges(@PathVariable Long recordId) {
        List<String> recordValues = auditService.getRecordChanges(recordId);
        if (recordValues.size() == 0) {
            return ResponseEntity.ok("Record not found (id = " + recordId + ")");
        }

        return ResponseEntity.ok(recordValues);
    }

    @PutMapping("/password-record-recovery")
    public ResponseEntity<?> recoverPasswordRecord(@RequestParam("recordId") Long recordId,
                                                   @RequestParam("stepsBack") int stepsBack) {
        auditService.recoverPasswordRecord(recordId, stepsBack);
        return ResponseEntity.noContent().build();
    }
}
