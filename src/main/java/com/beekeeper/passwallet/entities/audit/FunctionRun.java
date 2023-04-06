package com.beekeeper.passwallet.entities.audit;

import com.beekeeper.passwallet.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "function_run")
public class FunctionRun {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "functionRunSequenceGenerator")
    @SequenceGenerator(name = "functionRunSequenceGenerator")
    private Long id;

    @Column(name = "execution_timestamp", nullable = false)
    private LocalDateTime executionTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_function", nullable = false)
    private Function function;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_launching_user", nullable = false)
    private UserEntity launchingUser;
}
