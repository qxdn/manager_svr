package com.zju.manager_svr.repository;

import java.util.List;
import java.util.Optional;

import com.zju.manager_svr.model.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByRecordId(String recordId);

    Optional<Patient> findById(Integer id);

    List<Patient> findByDoctorId(Integer doctorId);

    void deleteById(Integer id);
}
