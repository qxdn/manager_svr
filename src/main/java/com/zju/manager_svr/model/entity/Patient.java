package com.zju.manager_svr.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zju.manager_svr.model.dto.UpdateInfoForm;

import javax.validation.constraints.Max;

import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "病人名不能为空")
    @Column(name = "username", unique = true, length = 255)
    @JsonProperty("name")
    private String username;

    @Min(value = 0, message = "年龄不能小于0")
    @Column(name = "age")
    private Integer age;

    @Min(value = 0, message = "性别只能为0或1")
    @Max(value = 1, message = "性别只能为0或1")
    @Column(name = "sex")
    private Integer sex;

    @NotBlank(message = "记录id不能为空")
    @Column(name = "record_id", length = 255)
    @JsonProperty("recordID")
    private String recordId;

    @Column(name = "info", length = 255)
    private String info;

    @Column(name = "result", length = 255)
    private String result;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "update_time")
    private Date updateTime = createTime;

    @JsonIgnore
    @Column(name = "doctor_id")
    private Integer doctorId;

    @JsonGetter("sex")
    public String getConvertedSex() {
        return this.sex == 0 ? "男" : "女";
    }

    public Patient update(Patient patient) {
        this.info = patient.getInfo();
        this.result = patient.getResult();
        this.state = patient.getState();
        this.username = patient.getUsername();
        this.sex = patient.getSex();
        this.age = patient.getAge();
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
