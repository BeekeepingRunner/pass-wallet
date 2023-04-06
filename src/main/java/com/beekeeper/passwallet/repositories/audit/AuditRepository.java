package com.beekeeper.passwallet.repositories.audit;

import com.beekeeper.passwallet.entities.audit.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
