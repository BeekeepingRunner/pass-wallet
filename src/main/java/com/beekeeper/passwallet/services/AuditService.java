package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.entities.Password;
import com.beekeeper.passwallet.entities.audit.Audit;
import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.repositories.FunctionRunRepository;
import com.beekeeper.passwallet.repositories.PasswordRepository;
import com.beekeeper.passwallet.repositories.audit.AuditRepository;
import com.beekeeper.passwallet.repositories.audit.OperationTypeRepository;
import com.beekeeper.passwallet.repositories.audit.TableNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditService {

    private static final Long PASSWORD_TABLE_NAME_ID = 1L;
    private static final Long CREATE_OPERATION_ID = 1L;
    private static final Long UPDATE_OPERATION_ID = 2L;

    private final FunctionRunRepository functionRunRepository;
    private final AuditRepository auditRepository;
    private final PasswordRepository passwordRepository;
    private final TableNameRepository tableNameRepository;
    private final OperationTypeRepository operationTypeRepository;

    public Collection<FunctionRun> getUserActions(Long userId) {
        return functionRunRepository.findByLaunchingUserId(userId);
    }

    public Audit auditPasswordOnSave(final Password password) {
        final Audit audit = new Audit();
        audit.setModifiedAt(LocalDateTime.now());
        audit.setTableName(tableNameRepository.getReferenceById(PASSWORD_TABLE_NAME_ID));
        audit.setOperationType(operationTypeRepository.getReferenceById(CREATE_OPERATION_ID));
        audit.setPresentRecordValue(password.toString());
        audit.setModifiedRecordId(password.getId());
        return auditRepository.save(audit);
    }

    public Audit auditPasswordOnUpdate(final Password password) {
        final Audit audit = new Audit();
        audit.setModifiedAt(LocalDateTime.now());
        audit.setTableName(tableNameRepository.getReferenceById(PASSWORD_TABLE_NAME_ID));
        audit.setOperationType(operationTypeRepository.getReferenceById(UPDATE_OPERATION_ID));

        final Password previous = passwordRepository.getReferenceById(password.getId());
        audit.setPreviousRecordValue(previous.toString());
        audit.setPresentRecordValue(password.toString());
        audit.setModifiedRecordId(password.getId());

        return auditRepository.save(audit);
    }

    public List<String> getPasswordRecordChanges(Long recordId) {
        return auditRepository.findAllByModifiedRecordId(recordId).stream()
                .map(Audit::getPresentRecordValue)
                .collect(Collectors.toList());
    }

    public void recoverPasswordRecord(Long recordId, int stepsBack) {
        List<Audit> recordAudits = auditRepository.findAllByModifiedRecordId(recordId).stream()
                .sorted(Comparator.comparing(Audit::getModifiedAt))
                .collect(Collectors.toList());

        if (recordAudits.isEmpty()) {
            throw new RuntimeException("No record found");
        }
        if (recordAudits.size() <= stepsBack) {
            throw new RuntimeException("Too many steps back");
        }

        Audit recoveryAudit = recordAudits.get(recordAudits.size() - 1 - stepsBack);
        String pastValue = recoveryAudit.getPresentRecordValue();

        Password passwordRecord = passwordRepository.getReferenceById(recordId);
        passwordRecord.setLogin(getPropertyValue(pastValue, Password.LOGIN_STR));
        passwordRecord.setPassword(getPropertyValue(pastValue, Password.PASSWORD_STR));
        passwordRecord.setDescription(getPropertyValue(pastValue, Password.DESCRIPTION_STR));
        passwordRecord.setWebAddress(getPropertyValue(pastValue, Password.WEB_ADDRESS_STR));
        passwordRepository.save(passwordRecord);
        auditPasswordOnUpdate(passwordRecord);
    }

    private String getPropertyValue(String auditRecordValue, String propertyName) {
        int valuePrefixLength = 4;
        int valueIndex = auditRecordValue.indexOf(propertyName) + propertyName.length() + valuePrefixLength;
        String startingFromValue = auditRecordValue.substring(valueIndex);

        final String valueMarker = ";;;";
        int valueEndIdx = startingFromValue.indexOf(valueMarker) - 1;
        return startingFromValue.substring(0, valueEndIdx);
    }
}
