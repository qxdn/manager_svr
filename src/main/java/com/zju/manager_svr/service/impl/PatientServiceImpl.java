package com.zju.manager_svr.service.impl;

import com.zju.manager_svr.exception.DeleteUploadException;
import com.zju.manager_svr.model.entity.CTImg;
import com.zju.manager_svr.model.entity.Patient;
import com.zju.manager_svr.repository.CTImgRepository;
import com.zju.manager_svr.repository.PatientRepository;
import com.zju.manager_svr.service.PatientService;
import com.zju.manager_svr.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CTImgRepository ctImgRepository;

    @Override
    public Patient findByRecordId(String recordId) {
        List<Patient> list = patientRepository.findByRecordId(recordId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Patient saveOrUpdate(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatient(Integer patientID){
        Optional<Patient> optional = patientRepository.findById(patientID);
        if (!optional.isPresent()) {
            return null;
        }
        return optional.get();
    }

    @Override
    public Patient getPatient(Integer patientID, Integer doctorID) {
        Optional<Patient> optional = patientRepository.findById(patientID);
        if (!optional.isPresent()) {
            return null;
        }
        Patient patient = optional.get();
        if (patient.getDoctorId() == 1 || doctorID.equals(patient.getDoctorId())) {
            return patient;
        } else {
            return null;
        }
    }

    @Override
    public List<Patient> getAllPatients(Integer doctorId) {
        if (doctorId == 1) {
            return patientRepository.findAll();
        }
        return patientRepository.findByDoctorId(doctorId);
    }

    @Override
    public boolean deletePatient(Integer patientID) throws DeleteUploadException {
        Optional<Patient> optional = patientRepository.findById(patientID);
        if (!optional.isPresent()) {
            return false;
        }
        Patient patient = optional.get();
        List<CTImg> list = ctImgRepository.findByPatientId(patientID);
        for (CTImg img : list) {
            FileUtil.deleteUploadFile(img.getFilename());
        }
        ctImgRepository.deleteByPatientId(patientID);
        patientRepository.delete(patient);
        return true;
    }

    @Override
    public List<CTImg> getPatientImgs(Integer patientID) {
        return ctImgRepository.findByPatientIdOrderByTimestampDesc(patientID);
    }
}
