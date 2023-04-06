
package com.beekeeper.passwallet.controllers;

import com.beekeeper.passwallet.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/user-actions/{userId}")
    public ResponseEntity<String> getUserActions(@PathVariable Long userId) {
        return ResponseEntity.ok("");
    }
}
