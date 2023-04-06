package com.beekeeper.passwallet.repositories;

import com.beekeeper.passwallet.entities.audit.Function;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FunctionRepository extends JpaRepository<Function, Long> {

    Optional<Function> findBySignature(String signature);
}
