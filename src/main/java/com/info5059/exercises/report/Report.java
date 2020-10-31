package com.info5059.exercises.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // FK
    private long employeeid;
    @OneToMany(mappedBy = "reportid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportItem> items = new ArrayList<ReportItem>();
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datecreated;

}
