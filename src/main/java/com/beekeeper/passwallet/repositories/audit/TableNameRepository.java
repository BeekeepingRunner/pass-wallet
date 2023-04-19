package com.beekeeper.passwallet.repositories.audit;

import com.beekeeper.passwallet.entities.audit.TableName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableNameRepository extends JpaRepository<TableName, Long> {
}
