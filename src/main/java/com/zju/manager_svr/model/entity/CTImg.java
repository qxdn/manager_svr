package com.zju.manager_svr.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "ctimgs")
@Data
public class CTImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @JsonIgnore
    private Integer id;

    @Column(name = "filename", unique = true, length = 255)
    private String filename;

    @Column(name = "uploadname", length = 255)
    private String uploadname;

    @Column(name = "timestamp")
    private Date timestamp = new Date();

    @Column(name = "type", length = 255)
    private String type;

    @Column(name = "patient_id")
    @JsonIgnore
    private Integer patientId;

    @Column(name = "doctor_id")
    @JsonIgnore
    private Integer doctorId;

    @Transient
    private boolean diabled = false;
}
