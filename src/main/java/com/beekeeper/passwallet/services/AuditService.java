package com.beekeeper.passwallet.services;

import com.beekeeper.passwallet.repositories.FunctionRunRepository;
import com.beekeeper.passwallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final UserRepository userRepository;
    private final FunctionRunRepository functionRunRepository;


}
