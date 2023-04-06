package com.beekeeper.passwallet.repositories;

import com.beekeeper.passwallet.entities.audit.FunctionRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionRunRepository extends JpaRepository<FunctionRun, Long> {
}
