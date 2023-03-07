CREATE TABLE USER_ENTITY (
    id                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    login                       VARCHAR(30) NOT NULL,
    password_hash               VARCHAR(512) NOT NULL,
    salt                        VARCHAR(20) NOT NULL,
    is_password_kept_as_hmac    BOOLEAN NULL
);

CREATE TABLE PASSWORD (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    password    VARCHAR(256) NOT NULL,
    login       VARCHAR(30) NOT NULL,
    web_address VARCHAR(256) NOT NULL,
    description VARCHAR(256) NULL,
    id_user     BIGINT NOT NULL,

    FOREIGN KEY (id_user) REFERENCES USER_ENTITY(id)
);