package com.beekeeper.passwallet.aspect;

import com.beekeeper.passwallet.entities.audit.Function;
import com.beekeeper.passwallet.entities.audit.FunctionRun;
import com.beekeeper.passwallet.repositories.FunctionRepository;
import com.beekeeper.passwallet.repositories.FunctionRunRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class FunctionRunAspect {

    private final FunctionRepository functionRepository;
    private final FunctionRunRepository functionRunRepository;

    @Before("execution(* com.beekeeper.passwallet.services.*.*(..))")
    public void registerFunctionExecution(JoinPoint joinPoint) {

        String methodSignature = joinPoint.getSignature().toString();

        final Function function = functionRepository.findBySignature(methodSignature)
                .orElseGet(() -> {
                    Function f = new Function();
                    f.setSignature(methodSignature);
                    return functionRepository.save(f);
                });

        registerExecution(function);
    }

    private void registerExecution(Function function) {
        FunctionRun functionRun = new FunctionRun();
        functionRun.setExecutionTimestamp(LocalDateTime.now());
        functionRun.setFunction(function);
        functionRunRepository.save(functionRun);
    }
}
