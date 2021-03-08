package common;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WebAPI {
    // Config class :

    //ExtentReport
    public static ExtentReports extent;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }
        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(driver, result.getName());
        }
        driver.quit();
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        DateFormat df = new SimpleDateFormat("(yyMMddHHmmssZ)");
        //DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        df.format(date);
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,
                    new File(System.getProperty("user.dir") + "/Screenshots/" + screenshotName + " " + df.format(date) + ".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }

    public static String convertToString(String st) {
        String splitString = "";
        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
        return splitString;
    }

    // Browser Setup
   public static WebDriver driver=null;


    @Parameters({"useCloudEnv","cloudEnvName","OS","os_version","browserName","browserVersion","url"})
    @BeforeMethod
    public void setUp(boolean useCloudEnv, String cloudEnvName, String OS, String os_version, String browserName, String browserVersion,String url){
        // Platform: Local Machine/ Cloud Machine
        if (useCloudEnv == true){

        } else {
            getLocalDriver(OS,browserName);
        }

    }

    public WebDriver getLocalDriver(String OS, String browserName){
        if (browserName.equalsIgnoreCase("chrome")){
            if (OS.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.chrome.driver","../Generic/BrowserDriver/mac/chromedriver");
            } else if (OS.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.chrome.driver","../Generic/BrowserDriver/windows/chromedriver.exe");
            }
            driver=new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("chrome-options")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            if (OS.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.chrome.driver","../Generic/BrowserDriver/mac/chromedriver");
            } else if (OS.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.chrome.driver","../Generic/BrowserDriver/windows/chromedriver.exe");
            }
            driver=new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")){
            if (OS.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.gecko.driver","../Generic/BrowserDriver/mac/geckodriver");
            } else if (OS.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.gecko.driver","../Generic/BrowserDriver/windows/geckodriver.exe");
            }
            driver=new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")){
            if (OS.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.ie.driver","../Generic/BrowserDriver/windows/IEDriverServer.exe");
            }
            driver=new InternetExplorerDriver();
        }

        return driver;
    }



    @AfterMethod(alwaysRun = true)
    public void cleanUp(){
        //driver.close();
        driver.quit();
    }








}
