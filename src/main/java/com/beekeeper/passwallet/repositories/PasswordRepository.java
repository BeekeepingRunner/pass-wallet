package com.beekeeper.passwallet.repositories;

import com.beekeeper.passwallet.entities.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}
