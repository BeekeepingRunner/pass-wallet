package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.repositories.FunctionRunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final FunctionRunRepository functionRunRepository;

    public Collection<FunctionRun> getUserActions(Long userId) {
        return functionRunRepository.findByLaunchingUserId(userId);
    }
}
