package com.liprogramming.data;

//raw version of front end data with appropriate data types
public class PayStub {
    private Integer reportId;
    private Integer employeeId;
    private PayPeriod payPeriod;
    private Double amountPaid;

    public PayStub(Integer reportId, Integer employeeId, PayPeriod payPeriod, Double amountPaid) {
        this.reportId = reportId;
        this.employeeId = employeeId;
        this.payPeriod = payPeriod;
        this.amountPaid = amountPaid;
    }

    //calculate new pay by multiplying pay rate and hours worked then add to current pay
    public void recordPay (JobGroup jobGroup, Double hoursWorked) {
        this.amountPaid += jobGroup.getPayRate() * hoursWorked;
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

    public PayPeriod getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(PayPeriod payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}