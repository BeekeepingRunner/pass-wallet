package com.beekeeper.passwallet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;
}
