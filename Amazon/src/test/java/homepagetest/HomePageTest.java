package homepagetest;

import common.WebAPI;
import homepage.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class HomePageTest extends WebAPI {
    // Test class

    HomePage homePage;

    public void getInit(){
        homePage= PageFactory.initElements(driver,HomePage.class);
    }

    @Test
    public void demo(){

    }




}
