package com.beekeeper.passwallet.aspect;

import com.beekeeper.passwallet.dto.password.NewPasswordDto;
import com.beekeeper.passwallet.entities.UserEntity;
import com.beekeeper.passwallet.entities.audit.Function;
import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.repositories.FunctionRepository;
import com.beekeeper.passwallet.repositories.FunctionRunRepository;
import com.beekeeper.passwallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class FunctionRunAspect {

    private final FunctionRepository functionRepository;
    private final FunctionRunRepository functionRunRepository;
    private final UserRepository userRepository;

    @Before("execution(* com.beekeeper.passwallet.services.*.*(..))")
    public void registerFunctionExecution(JoinPoint joinPoint) {
        Optional<UserEntity> launchingUser = getLaunchingUser(joinPoint);

        String methodSignature = joinPoint.getSignature().toString();
        final Function function = functionRepository.findBySignature(methodSignature)
                .orElseGet(() -> {
                    Function f = new Function();
                    f.setSignature(methodSignature);
                    return functionRepository.save(f);
                });

        registerExecution(function, launchingUser);
    }

    private Optional<UserEntity> getLaunchingUser(JoinPoint joinPoint) {
        final String methodSignature = joinPoint.getSignature().toString();
        if (methodSignature.contains("PasswordService.saveNewPassword")) {
            NewPasswordDto dto = (NewPasswordDto) joinPoint.getArgs()[0];
            final Long userId = dto.getUserId();
            return userRepository.findById(userId);
        }
        if (methodSignature.contains("PasswordService.getUserPasswords")) {
            long userId = (long) joinPoint.getArgs()[0];
            return userRepository.findById(userId);
        }

        return Optional.empty();
    }

    private void registerExecution(Function function, Optional<UserEntity> launchingUser) {
        FunctionRun functionRun = new FunctionRun();
        functionRun.setExecutionTimestamp(LocalDateTime.now());
        functionRun.setFunction(function);
        launchingUser.ifPresent(functionRun::setLaunchingUser);
        functionRunRepository.save(functionRun);
    }
}
