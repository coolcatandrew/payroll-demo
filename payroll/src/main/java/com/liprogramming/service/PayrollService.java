package com.liprogramming.service;

import com.google.gson.Gson;
import com.liprogramming.dao.PayrollDao;
import com.liprogramming.data.JobGroup;
import com.liprogramming.data.PayPeriod;
import com.liprogramming.data.PayStub;
import com.liprogramming.data.PayStubViewModel;
import com.liprogramming.model.Payroll;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
public class PayrollService {

    //set up dao to automatically be initialized
    @Autowired
    private PayrollDao payrollDao;

    /**
     * change CSV into list of Payroll objects and persist the data
     * @return List<Payrolls>
     */
    public List<Payroll> parseReportCSV (MultipartFile file) {
        try {
            List<Payroll> payrolls = new ArrayList<>();

            File tmp = new File("tmp.csv");
            file.transferTo(tmp);

            Reader reader = new FileReader(tmp);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase() //header order should not matter
                    .withTrim());

            List<CSVRecord> records = csvParser.getRecords();

            //csv should have properly formatted footer with report Id
            CSVRecord footer = records.size() > 1 ? records.get(records.size() - 1) : null;
            Integer reportId = null;
            if (footer != null && "report id".equals(footer.get(0))) {
                reportId = Integer.parseInt(footer.get(1));
            }
            if (reportId == null) {
                return null;
            }

            //get all records and save them to database
            for (CSVRecord csvRecord : records) {
                if (csvRecord.getRecordNumber() == records.size()) { break; } //ignore footer
                // Accessing values by Header names
                String employeeId = csvRecord.get("employee id");
                String hoursWorked = csvRecord.get("hours worked");
                String jobGroup = csvRecord.get("job group");
                String date = csvRecord.get("date");
                Payroll payroll = new Payroll(
                        reportId,
                        Integer.parseInt(employeeId),
                        Double.parseDouble(hoursWorked),
                        jobGroup,
                        date
                );
                payrolls.add(payroll);
                payrollDao.save(payroll);
            }
            return payrolls;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * convert payrolls from csv into PayStubs
     * @return List<PayStub>
     */
    public List<PayStub> payrollToPayStub (List<Payroll> payrolls) {
        List<PayStub> payStubs = new ArrayList<>();

        try {
            // if pay stub list contains something with the same employee id and pay period, use that. Otherwise init new one.
            for (Payroll payroll : payrolls) {
                PayPeriod payPeriod = new PayPeriod(payroll.getDate());
                Predicate<PayStub> payStubPredicate = o -> o.getEmployeeId().equals(payroll.getEmployeeId()) && payPeriod.toString().equals(o.getPayPeriod().toString());
                //check if exists in list
                if (payStubs.stream().anyMatch(payStubPredicate)) {
                    //add new pay to existing pay
                    payStubs.stream().filter(payStubPredicate)
                            .findFirst()
                            .get()
                            .recordPay(JobGroup.valueOf(payroll.getJob().toUpperCase()), payroll.getHours());
                } else {
                    //create paystub and add to set
                    payStubs.add(new PayStub(payroll.getReportId(), payroll.getEmployeeId(), new PayPeriod(payroll.getDate()), JobGroup.valueOf(payroll.getJob().toUpperCase()).getPayRate() * payroll.getHours()));
                }
            }

            //sort list of PayStubs by employeeId then pay period
            payStubs.sort(Comparator.comparing(PayStub::getEmployeeId)
                    .thenComparing(p -> p.getPayPeriod().getStartDate()));

            return payStubs;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * convert PayStubs into PayStubViewModel for front end
     * @return List<PayStubViewModel>
     */
    public List<PayStubViewModel> payStubToPayStubViewModel (List<PayStub> payStubs) {
        List<PayStubViewModel> payStubViewModels = new ArrayList<>();
        for (PayStub payStub : payStubs) {
            payStubViewModels.add(new PayStubViewModel(
                    String.valueOf(payStub.getReportId()),
                    String.valueOf(payStub.getEmployeeId()),
                    payStub.getPayPeriod().toString(),
                    payStub.getAmountPaid()));
        }
        return payStubViewModels;
    }

    /**
     * get all payroll objects from database
     * @return List<Payroll>
     */
    public List<Payroll> getAllPayrolls() {
        return payrollDao.getAll();

    }

    /**
     * convert a list of payrolls into JSON
     * @return List<String>
     */
    public List<String> getPayrollStrings(List<PayStubViewModel> payrolls) {
        List<String> jsonList = new ArrayList<>();
        for(PayStubViewModel payroll : payrolls) {
            Gson gson = new Gson();
            String json = gson.toJson(payroll);
            jsonList.add(json);
        }
        return jsonList;
    }
}
