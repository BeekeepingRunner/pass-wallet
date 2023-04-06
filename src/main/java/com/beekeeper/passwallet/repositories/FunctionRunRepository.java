package com.beekeeper.passwallet.repositories;

import com.beekeeper.passwallet.entities.audit.FunctionRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FunctionRunRepository extends JpaRepository<FunctionRun, Long> {

    List<FunctionRun> findByLaunchingUserId(Long userId);
}
