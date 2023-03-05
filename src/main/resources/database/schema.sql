CREATE TABLE USER (
    id                          BIGINT NOT NULL AUTO_INCREMENT,
    login                       VARCHAR(30) NOT NULL,
    password_hash               VARCHAR(512) NOT NULL,
    salt                        VARCHAR(20) NOT NULL,
    is_password_kept_as_hash    BOOLEAN NULL,

    PRIMARY KEY (id)
);

CREATE TABLE PASSWORD (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    password    VARCHAR(256) NOT NULL,
    login       VARCHAR(30) NOT NULL,
    web_address VARCHAR(256) NOT NULL,
    description VARCHAR(256) NULL,
    id_user     BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES USER(id)
);