package com.beekeeper.passwallet.repositories;

import com.beekeeper.passwallet.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
