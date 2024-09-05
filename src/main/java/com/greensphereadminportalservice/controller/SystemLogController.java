package com.greensphereadminportalservice.controller;


import com.greensphereadminportalservice.model.SystemLogModel;
import com.greensphereadminportalservice.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/systemlogs")
public class SystemLogController {

    @Autowired
    SystemLogService systemlogrepo;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<SystemLogModel>> fetchAll() {
        List<SystemLogModel> allLogs = systemlogrepo.fetchAllLogs();
        return new ResponseEntity<>(allLogs, HttpStatus.OK);
    }

}
