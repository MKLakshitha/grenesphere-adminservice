package com.greensphereadminportalservice.repository;


import com.greensphereadminportalservice.model.SystemLogModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<SystemLogModel, Long> {

}
