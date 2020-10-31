package com.info5059.exercises.report;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Date;
@Component
public class ReportDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public Long create(Report clientrep) {
        Report realReport = new Report();
        realReport.setDatecreated(new Date());
        realReport.setEmployeeid(clientrep.getEmployeeid());
        entityManager.persist(realReport);
        for(ReportItem item :clientrep.getItems()) {
            ReportItem realItem = new ReportItem();
            realItem.setReportid(realReport.getId());
            realItem.setExpenseid(item.getExpenseid());
            entityManager.persist(realItem);
        }
        return realReport.getId();
    }

    public Report findOne(Long id) {
        Report report = entityManager.find(Report.class, id);
        if (report == null) {
            throw new EntityNotFoundException("Can't find report for ID "
                    + id);
        }
        return report;
    }

}
