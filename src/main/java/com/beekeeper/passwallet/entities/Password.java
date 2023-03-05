package com.beekeeper.passwallet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String webAddress;

    @Column
    private String description;
}
