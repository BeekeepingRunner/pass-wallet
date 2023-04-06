package com.beekeeper.passwallet.repositories.audit;

import com.beekeeper.passwallet.entities.audit.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}
