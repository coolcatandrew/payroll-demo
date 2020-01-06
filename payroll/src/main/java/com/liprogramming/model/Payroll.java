package com.liprogramming.model;

import javax.persistence.*;

// data taken from payroll csv, to be saved in database
@Entity
@Table(name="Payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private Integer reportId;
    @Column
    private Integer employeeId;
    @Column
    private Double hours;
    @Column
    private String job;
    @Column
    private String date;

    public Payroll() { }

    public Payroll (Integer reportId, Integer employeeId, Double hours, String job, String date) {
        this.reportId = reportId;
        this.employeeId = employeeId;
        this.hours = hours;
        this.job = job;
        this.date = date;
    }


    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
