package com.liprogramming.data;

//Job types and pay rates that do not change, able to extend to more types of jobs and associated pay rates.
public enum JobGroup {
    A("20.0"),
    B("30.0");

    private Double payRate;

    //enter job type with associated pay rate
    JobGroup(String payRate) {
        this.payRate = Double.parseDouble(payRate);
    }

    public Double getPayRate() {
        return payRate;
    }

}
