package homepage;

import common.WebAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static homepage.HomePageWebElement.*;

public class HomePage  extends WebAPI {
    // Action Method class

    // Find By Annotation: First Approach
    @FindBy (how = How.ID, using =searchBoxLocator ) public WebElement searchBox;
    @FindBy (how = How.ID, using =searchButtonLocator ) public WebElement searchButton;
    @FindBy (how = How.LINK_TEXT, using =searchButtonLocator ) public WebElement searchButton1;

//    // Find By Annotation: Second Approach using public
//    @FindBy(id=searchBoxLocator) public WebElement searchBox1;
//    // Find By Annotation: Third Approach using private
//    @FindBy(id=searchBoxLocator) private WebElement searchBox2;
//
//    public WebElement getSearchBox2() {
//        return searchBox2;
//    }
//
//    public void setSearchBox2(WebElement searchBox2) {
//        this.searchBox2 = searchBox2;
//    }
//
//    public void demo(){
//        searchBox.sendKeys("Mask");
//        searchButton.click();
//
//    }

















}
