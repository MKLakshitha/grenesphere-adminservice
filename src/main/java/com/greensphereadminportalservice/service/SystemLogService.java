package com.greensphereadminportalservice.service;

import com.greensphereadminportalservice.model.SystemLogModel;
import com.greensphereadminportalservice.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemLogService {

    @Autowired
    private SystemLogRepository systemLogRepository;

    public void logAction(String userId, String action, String details) {
        SystemLogModel log = new SystemLogModel();
        log.setUserId(userId);
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        systemLogRepository.save(log);
    }

    public List<SystemLogModel> fetchAllLogs() {
        return systemLogRepository.findAll();
    }
}
