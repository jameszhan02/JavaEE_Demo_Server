package com.info5059.exercises.pdfexample;

import com.info5059.exercises.employee.EmployeeRepository;
import com.info5059.exercises.expense.ExpenseRepository;
import com.info5059.exercises.report.ReportDAO;
import com.itextpdf.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
@CrossOrigin
@RestController
public class ReportPDFController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ReportDAO reportDAO;
    @RequestMapping(value = "/ReportPDF", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> streamPDF(HttpServletRequest request) throws IOException, java.io.IOException {
        // get formatted pdf as a stream

//        String reportId = Long.toString(repid);
        System.out.println("In report api");
        System.out.println( request.getParameter("repid"));
//        System.out.println(reportId);
        ByteArrayInputStream bis = ReportPDFGenerator.generateReport(request.getParameter("repid"), reportDAO, employeeRepository, expenseRepository);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=examplereport.pdf");
        // dump stream to browser
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
