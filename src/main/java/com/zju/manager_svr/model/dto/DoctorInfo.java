package com.zju.manager_svr.model.dto;

import java.util.List;

import com.zju.manager_svr.model.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfo {

    private Integer id;

    private String name;

    private List<Patient> patients;

    private String role;
}
