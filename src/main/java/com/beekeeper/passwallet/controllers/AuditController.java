
package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
}
