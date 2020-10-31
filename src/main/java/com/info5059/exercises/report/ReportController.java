package com.info5059.exercises.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
public class ReportController {
    @Autowired
    private ReportDAO reportDAO;
    @PostMapping("/api/reports")
    public ResponseEntity<Long> addOne(@RequestBody Report clientrep) { // use RequestBody here
        Long reportId = reportDAO.create(clientrep);
        return new ResponseEntity<Long>(reportId, HttpStatus.OK);
    }
}
