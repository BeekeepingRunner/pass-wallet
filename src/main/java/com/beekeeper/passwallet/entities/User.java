package com.beekeeper.passwallet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String login;

    @Column
    private String passwordHash;

    @Column
    private String salt;

    @Column
    private Boolean isPasswordKeptAsHash;
}
