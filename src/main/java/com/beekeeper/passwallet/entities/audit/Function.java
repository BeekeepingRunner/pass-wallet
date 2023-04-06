package com.beekeeper.passwallet.entities.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Function {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "functionSequenceGenerator")
    @SequenceGenerator(name = "functionSequenceGenerator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;
}
