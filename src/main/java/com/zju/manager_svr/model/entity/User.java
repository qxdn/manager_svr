package com.zju.manager_svr.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, length = 255)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "realname", length = 255)
    private String realname;

    @Column(name = "usertype")
    private Integer userType;

    public User(String username, String password, String realname) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.userType = 3;
    }

}
