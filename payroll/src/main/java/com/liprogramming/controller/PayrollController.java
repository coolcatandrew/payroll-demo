package com.liprogramming.controller;

import com.liprogramming.data.PayStubViewModel;
import com.liprogramming.model.Payroll;
import com.liprogramming.utils.ResponseBody;
import com.liprogramming.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class PayrollController {
	@Autowired
	private PayrollService payrollService;

	//localhost:8080/payroll/
	//get initial data from database to display in table
	@RequestMapping (value = "/", method = RequestMethod.GET)
	public String init(Model model) {
		List<Payroll> payrolls = payrollService.getAllPayrolls();
		model.addAttribute("payrolls", payrollService.getPayrollStrings(payrollService.payStubToPayStubViewModel(payrollService.payrollToPayStub(payrolls))));

		return "payroll";
	}

	//get the values based on reportId
	//TODO: implement for filtering table based on report Id
	@RequestMapping (value = "/report/{reportId}", method = RequestMethod.GET)
	public ResponseEntity getReport(@PathVariable String reportId) {
		ResponseBody<List<PayStubViewModel>> result = new ResponseBody<List<PayStubViewModel>>();

		return ResponseEntity.ok(result);
	}

	//save report type CSV
	@RequestMapping (value = "/reportCSV", method = RequestMethod.POST)
	public ResponseEntity saveReportCSV (@RequestBody MultipartFile file) {
		ResponseBody<PayStubViewModel> result = new ResponseBody<>();
		payrollService.parseReportCSV(file);

		//parsed payrolls are persisted in db
		List<Payroll> payrolls = payrollService.getAllPayrolls();

		if (payrolls == null || payrolls.size() == 0) {
			result.setMessage("Error: improper file format, please enter a CSV file and try again.");
			return ResponseEntity.ok(result);
		}

		result.setMessage("Successfully uploaded CSV file.");
		result.setData(payrollService.payStubToPayStubViewModel(payrollService.payrollToPayStub(payrolls)));

		return ResponseEntity.ok(result);
	}
}