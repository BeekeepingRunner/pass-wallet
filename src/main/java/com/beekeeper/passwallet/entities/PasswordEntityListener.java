package com.beekeeper.passwallet.entities;

import com.beekeeper.passwallet.entities.audit.Audit;
import com.beekeeper.passwallet.repositories.PasswordRepository;
import com.beekeeper.passwallet.repositories.audit.AuditRepository;
import com.beekeeper.passwallet.repositories.audit.OperationTypeRepository;
import com.beekeeper.passwallet.repositories.audit.TableNameRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PreUpdate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class PasswordEntityListener {

    private static final Long PASSWORD_TABLE_NAME_ID = 1L;
    private static final Long CREATE_OPERATION_ID = 1L;
    private static final Long UPDATE_OPERATION_ID = 2L;

    static private AuditRepository auditRepository;
    static private PasswordRepository passwordRepository;
    static private TableNameRepository tableNameRepository;
    static private OperationTypeRepository operationTypeRepository;

    @Autowired
    public void init(AuditRepository auditRepository,
                     PasswordRepository passwordRepository,
                     TableNameRepository tableNameRepository,
                     OperationTypeRepository operationTypeRepository)
    {
        PasswordEntityListener.auditRepository = auditRepository;
        PasswordEntityListener.passwordRepository = passwordRepository;
        PasswordEntityListener.tableNameRepository = tableNameRepository;
        PasswordEntityListener.operationTypeRepository = operationTypeRepository;
    }

    @PostPersist
    @Transactional
    public void afterFirstSave(Password password) {
        Optional<Audit> recordAudit = auditRepository.findByModifiedRecordId(password.getId());
        if (recordAudit.isEmpty()) {
            final Audit audit = new Audit();
            audit.setModifiedAt(LocalDateTime.now());
            audit.setTableName(tableNameRepository.getReferenceById(PASSWORD_TABLE_NAME_ID));
            audit.setOperationType(operationTypeRepository.getReferenceById(CREATE_OPERATION_ID));
            audit.setPresentRecordValue(password.toString());
            audit.setModifiedRecordId(password.getId());
            auditRepository.save(audit);
        }
    }

    @PreUpdate
    @Transactional
    public void beforeUpdate(Password password) {
        final Audit audit = new Audit();
        audit.setModifiedAt(LocalDateTime.now());
        audit.setTableName(tableNameRepository.getReferenceById(PASSWORD_TABLE_NAME_ID));
        audit.setOperationType(operationTypeRepository.getReferenceById(UPDATE_OPERATION_ID));

        final Password previous = passwordRepository.getReferenceById(password.getId());
        audit.setPreviousRecordValue(previous.toString());
        audit.setPresentRecordValue(password.toString());

        auditRepository.save(audit);
    }
}
