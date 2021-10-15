package com.zju.manager_svr.service;

import java.util.List;

import com.zju.manager_svr.exception.DeleteUploadException;
import com.zju.manager_svr.model.entity.CTImg;
import com.zju.manager_svr.model.entity.Patient;

public interface PatientService {
    public Patient findByRecordId(String recordId);

    public Patient saveOrUpdate(Patient patient);

    public Patient getPatient(Integer patientID);

    public Patient getPatient(Integer patientID, Integer doctorID);

    public List<Patient> getAllPatients(Integer doctorId);

    public boolean deletePatient(Integer patientID) throws DeleteUploadException;

    public List<CTImg> getPatientImgs(Integer patientID);
}
