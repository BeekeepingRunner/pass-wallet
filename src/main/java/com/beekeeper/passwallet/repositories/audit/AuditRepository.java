package com.beekeeper.passwallet.repositories.audit;

import com.beekeeper.passwallet.entities.audit.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    Optional<Audit> findByModifiedRecordId(Long recordId);
    List<Audit> findAllByModifiedRecordId(Long recordId);
}
