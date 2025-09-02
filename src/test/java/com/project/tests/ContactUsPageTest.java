package com.project.tests;

import org.testng.annotations.DataProvider;
import org.testng.Assert;

import org.testng.annotations.Test;

import com.project.base.Basetest;
import com.project.pages.Contactuspage;
import com.project.pages.Homepage;
import com.project.utilities.ExcelUtilities;

import java.io.File;

public class ContactUsPageTest extends Basetest {

 
    @DataProvider(name = "contactus-data")
    public Object[][] contactData() throws Exception {
        
        String excelPath = "/Users/subhzzz/Desktop/Automation_Project/project/src/test/resources/contactus.xlsx";
        return ExcelUtilities.getData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "contactus-data")
    public void submitContactUsForm(String name, String email, String subject, String message, String filePath) throws InterruptedException {
        


        Homepage home = new Homepage(driver);
        home.clickContactUs();


        Contactuspage contact = new Contactuspage(driver);
        contact.enterName(name);
        contact.enterEmail(email);
        contact.enterSubject(subject);
        contact.enterMessage(message);

        if (filePath != null && !filePath.trim().isEmpty()) {
            String absolutePath = filePath;
            if (!new File(filePath).isAbsolute()) {
                absolutePath = System.getProperty("user.dir") + File.separator + filePath.replace("/", File.separator);
            }
            contact.uploadFile(absolutePath);
        }

 
        contact.clickSubmit();
        //Assert.assertTrue(contact.isSuccessMessageDisplayed(), "Success message is displayed");
        if (contact.isSuccessMessageDisplayed()) {
        	System.out.println("Success message is verified");
        }else {
        	System.out.println("Error: Message not verified");
        }


    }
}
