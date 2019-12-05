package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.print.DocFlavor;
import java.util.List;

public class SearchPage {
    WebDriver driver;
    public SearchPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(className = "s-image")
    WebElement searchResults;

    @FindBy(xpath = "//span[text()='See more']")
    @CacheLookup
    WebElement seeMoreBrands;

    @FindBys({@FindBy(xpath="//h2/a[@class='a-link-normal a-text-normal']/span[@class='a-size-base-plus a-color-base a-text-normal']")})
    @CacheLookup
    List<WebElement> productName;

    @FindBys({@FindBy(xpath="//span[@class='a-price-whole']")})
    @CacheLookup
    List<WebElement> productPrice;


    public void waitForSearchResultsToLoad(){
        new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(searchResults));
    }

    public void selectDisplay(String displayToSearch) throws InterruptedException {
        WebElement display = driver.findElement(By.xpath("//li[contains(@id,'p_n_feature')]//span[contains(text(),'"+displayToSearch+"')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(display);
        actions.perform();
        display.click();
    }

    public void selectBrandMaterial(String brandMaterialToSearch) throws InterruptedException {
        WebElement brandMaterial = driver.findElement(By.xpath("//li[contains(@id,'p_n_material_browse')]//span[contains(text(),'"+brandMaterialToSearch+"')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(brandMaterial);
        actions.perform();
        brandMaterial.click();
    }

    public void selectBrand(String brandToSearch) throws InterruptedException {
        WebElement brand = driver.findElement(By.xpath("//span[text()='"+brandToSearch+"']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(brand);
        actions.perform();
        brand.click();
    }

    public void selectDiscount(String discountToSearch) throws InterruptedException {
        WebElement discount = driver.findElement(By.xpath("//li[contains(@id,'p_n_pct-off-with-tax')]//span[contains(text(),'"+discountToSearch+"')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(discount);
        actions.perform();
        discount.click();
    }

    public void clickSeeMoreBrand() throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(seeMoreBrands);
        actions.perform();
        seeMoreBrands.click();
    }

    public String[] getProductName(){
        String [] productNames = new String[productName.size()];
        for(int i=0;i<productName.size();i++){
            productNames[i]= productName.get(i).getText();
        }

        return productNames;

    }

    public String[] getProductPrice(){
        String [] productPrices = new String[productPrice.size()];
        for(int i=0;i<productPrice.size();i++){
            productPrices[i]= productPrice.get(i).getText();
        }

        return productPrices;

    }
}
