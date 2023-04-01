package com.beekeeper.passwallet.entities.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audSequenceGenerator")
    @SequenceGenerator(name = "audSequenceGenerator")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_table_name", nullable = false)
    private TableName tableName;

    @NotNull
    @Column(name = "operation", nullable = false)
    private String operation;

    @NotNull
    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @NotNull
    @Column(name = "payload")
    private String payload;
}