package com.beekeeper.passwallet.entities.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "table_name")
public class TableName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tableNameSequenceGenerator")
    @SequenceGenerator(name = "tableNameSequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column
    private String description;

    @OneToMany(mappedBy = "tableName")
    private Collection<Audit> audits;
}