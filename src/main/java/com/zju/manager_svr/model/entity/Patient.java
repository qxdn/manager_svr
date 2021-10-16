package com.zju.manager_svr.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zju.manager_svr.model.dto.AddPatientForm;
import com.zju.manager_svr.model.dto.UpdateInfoForm;

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
    @JsonProperty("name")
    private String username;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "record_id", length = 255)
    @JsonProperty("recordID")
    private String recordId;

    @Column(name = "info", length = 255)
    private String info;

    @Column(name = "result", length = 255)
    private String result;

    @Column(name = "state", length = 255)
    private String state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime = createTime;

    @JsonIgnore
    @Column(name = "doctor_id")
    private Integer doctorId;

    @JsonGetter("sex")
    public String getConvertedSex() {
        return this.sex == 0 ? "男" : "女";
    }

    public Patient update(AddPatientForm form) {
        this.info = form.getInfo();
        this.result = form.getResult();
        this.state = form.getState();
        this.username = form.getUsername();
        this.sex = form.getSex();
        this.age = form.getAge();
        this.updateTime = new Date();
        return this;
    }

    public Patient update(UpdateInfoForm form) {
        this.info = form.getInfo();
        this.state = form.getState();
        this.result = form.getResult();
        return this;
    }
}
