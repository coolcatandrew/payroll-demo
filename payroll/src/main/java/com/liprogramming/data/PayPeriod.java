package com.liprogramming.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PayPeriod {
    private Date startDate;
    private Date endDate;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PayPeriod (String date) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate workDate = LocalDate.parse(date, formatter);

        int day = workDate.getDayOfMonth();
        int month = workDate.getMonthValue();
        int year = workDate.getYear();

        SimpleDateFormat fromLocalDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        if (day <= 15) {
            startDate = fromLocalDateFormatter.parse(LocalDate.of(year, month, 1).toString());
            endDate = fromLocalDateFormatter.parse(LocalDate.of(year, month, 15).toString());
        } else {
            startDate = fromLocalDateFormatter.parse(LocalDate.of(year, month, 16).toString());
            endDate = fromLocalDateFormatter.parse(LocalDate.of(year, month, workDate.lengthOfMonth()).toString());
        }
    }

    @Override
    public String toString() {
        return simpleDateFormat.format(startDate) + " - " + simpleDateFormat.format(endDate);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
