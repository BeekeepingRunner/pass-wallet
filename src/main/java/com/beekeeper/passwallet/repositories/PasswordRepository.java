package com.beekeeper.passwallet.repositories;

import com.beekeeper.passwallet.entities.Password;
import com.beekeeper.passwallet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}
