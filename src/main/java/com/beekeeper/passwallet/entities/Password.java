package com.beekeeper.passwallet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public static final String LOGIN_STR = "login";
    public static final String PASSWORD_STR = "password";
    public static final String WEB_ADDRESS_STR = "web_address";
    public static final String DESCRIPTION_STR = "description";

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", " + LOGIN_STR + "=;;;" + login + ";;;" +
                ", " + PASSWORD_STR + "=;;;" + password + "=;;;" +
                ", " + WEB_ADDRESS_STR + "=;;;" + webAddress + "=;;;" +
                ", " + DESCRIPTION_STR + "=;;;" + description + "=;;;" +
                ", user_id=" + user.getId() +
                '}';
    }
}
