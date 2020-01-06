package com.liprogramming.dao;

import com.liprogramming.model.Payroll;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class PayrollDao {
    @PersistenceContext
    private EntityManager em;

    //get all payrolls from database
    public List<Payroll> getAll () {
        String sql = "SELECT p FROM Payroll p";
        Query query = em.createQuery(sql);
        return query.getResultList();
    }

    //get all entries for a specific report
    public List<Payroll> getByReportId (Integer reportId) {
        String sql = "SELECT p FROM Payroll p WHERE p.reportId = ?1";
        Query query = em.createQuery(sql);
        query.setParameter(1, reportId);
        return query.getResultList();
    }

    @Transactional
    public void save (Payroll payroll) {
        em.persist(payroll);
    }

}
