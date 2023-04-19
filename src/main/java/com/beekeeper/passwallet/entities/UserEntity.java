package com.beekeeper.passwallet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean isPasswordKeptAsHmac;

    @Column
    private boolean inModifyMode;

    @OneToMany
    @JoinColumn(name = "id_user")
    private Collection<Password> passwords;
}
