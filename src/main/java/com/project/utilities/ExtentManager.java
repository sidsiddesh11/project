package com.project.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
	
	private static ExtentReports extent;
	static String projectpath = System.getProperty("user.dir");
	
	public static ExtentReports getinstance() {
		if (extent == null) {
			// Add timestamp to report name
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String reportPath = projectpath + "\\src\\test\\resources\\Reports\\ReportsProject_" + timestamp + ".html";
			
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(spark);
		}
		return extent;
	}
}
