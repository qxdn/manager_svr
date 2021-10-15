package com.zju.manager_svr.repository;

import com.zju.manager_svr.model.entity.CTImg;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTImgRepository extends JpaRepository<CTImg, Integer> {
    List<CTImg> findByPatientId(Integer patientId);

    Long deleteByPatientId(Integer patientId);

    List<CTImg> findByPatientIdOrderByTimestampDesc(Integer patientId);
}
