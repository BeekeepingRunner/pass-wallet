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

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audSequenceGenerator")
    @SequenceGenerator(name = "audSequenceGenerator")
    private Long id;

    @Column(name = "id_modified_record", nullable = false)
    private Long modifiedRecordId;

    @Column(name = "present_record_value", nullable = false)
    private String presentRecordValue;

    @Column(name = "previous_record_value", nullable = true)
    private String previousRecordValue;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modifying_user")
    private UserEntity modifyingUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_table_name", nullable = false)
    private TableName tableName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_operation_type", nullable = false)
    private OperationType operationType;
}