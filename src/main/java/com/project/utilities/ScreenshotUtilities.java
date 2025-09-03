package com.project.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtilities {
    static String projectpath = System.getProperty("user.dir");

    public static String Capture(WebDriver driver, String testName) throws IOException {
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dest = projectpath + "/src/test/resources/Screenshots/" + testName + "_" + timestamp + ".png";

        File destFile = new File(dest);
        FileUtils.copyFile(scr, destFile);
        return dest;
    }
}
