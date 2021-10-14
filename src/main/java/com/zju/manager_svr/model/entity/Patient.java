package com.zju.manager_svr.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, length = 255)
    private String username;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "record_id", length = 255)
    private String record_id;

    @Column(name = "info", length = 255)
    private String info;

    @Column(name = "result", length = 255)
    private String result;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "create_time")
    private Date create_time = new Date();

    @Column(name = "update_time")
    private Date update_time;

    @Column(name = "doctor_id")
    private Integer doctor_id;
}
