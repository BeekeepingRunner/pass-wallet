package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.entities.Password;
import com.beekeeper.passwallet.entities.audit.Audit;
import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.repositories.FunctionRunRepository;
import com.beekeeper.passwallet.repositories.PasswordRepository;
import com.beekeeper.passwallet.repositories.audit.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final FunctionRunRepository functionRunRepository;
    private final AuditRepository auditRepository;
    private final PasswordRepository passwordRepository;

    public Collection<FunctionRun> getUserActions(Long userId) {
        return functionRunRepository.findByLaunchingUserId(userId);
    }

    public List<String> getRecordChanges(Long recordId) {
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
    }

    private String getPropertyValue(String auditRecordValue, String propertyName) {
        int valuePrefixLength = 4;
        int valueIndex = auditRecordValue.indexOf(propertyName) + propertyName.length() + valuePrefixLength;
        String startingFromValue = auditRecordValue.substring(valueIndex);

        final String valueMarker = ";;;";
        int valueEndIdx = startingFromValue.indexOf(valueMarker) - 1;
        return startingFromValue.substring(valueIndex, valueEndIdx);
    }
}
