package com.liprogramming.data;

import java.text.DecimalFormat;

//View Model to be displayed in front end
public class PayStubViewModel {
    private String reportId;
    private String employeeId;
    private String payPeriod;
    private String amountPaid;

    public PayStubViewModel(String reportId, String employeeId, String payPeriod, Double amountPaid) {
        this.reportId = reportId;
        this.employeeId = employeeId;
        this.payPeriod = payPeriod;
        this.amountPaid = "$" + new DecimalFormat("0.00").format(amountPaid); //format for table
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }
}
