package com.beekeeper.passwallet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String login;

    @Column
    private String passwordHash;

    @Column
    private String masterPassword;

    @Column
    private String salt;

    @Column
    private Boolean isPasswordKeptAsHmac;

    @OneToMany
    @JoinColumn(name = "id_user")
    private Collection<Password> passwords;
}
