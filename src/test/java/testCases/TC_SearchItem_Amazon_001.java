package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.SearchPage;
import utils.XLUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TC_SearchItem_Amazon_001 extends BaseClass{

    @Test(dataProvider = "SearchData",priority = 0)
    public void seachItem(String textToSearch, String display, String brandMaterial, String brand, String discount) throws IOException, InterruptedException {

        logger.info("URL is opened");
        HomePage home = new HomePage(driver);
        logger.info("Opened the browser");

        if(driver.getTitle().toLowerCase().equals(title.toLowerCase())){
            Assert.assertTrue(true);
        }
        else{
            captureScreenshot(driver,"Amazon Home page");
            logger.error("Navigation to Amazon web page failed");
            Assert.assertTrue(false);
        }

        home.setTextSearchString(textToSearch);
        logger.info("Searched for "+ textToSearch);
        home.clickSearchButton();

        SearchPage search = new SearchPage(driver);

        search.waitForSearchResultsToLoad();

        //Select Display from category
        search.selectDisplay(display);

        search.waitForSearchResultsToLoad();

        //Select Brand material
        search.selectBrandMaterial(brandMaterial);
        search.waitForSearchResultsToLoad();

        //Select Brand
        search.clickSeeMoreBrand();
        search.waitForSearchResultsToLoad();
        search.selectBrand(brand);
        search.waitForSearchResultsToLoad();

        //Select Discounts
        search.selectDiscount(discount);
        search.waitForSearchResultsToLoad();


        }

        @Test(priority = 1)
        public void printProductDetails() throws IOException {

            SearchPage search = new SearchPage(driver);
            //Get list of productnames
            String [] productNames = search.getProductName();

            //Get product price
            String [] productPrices = search.getProductPrice();

            // Print product details and write to sheet
            System.out.println("Here are the product details");
            for(int i=0;i<productNames.length;i++){
                System.out.println(productNames[i]+" - "+productPrices[i]);
            }

            //Put it into sheet
            setData(productNames,productPrices);

            //Print nth product details
            System.out.println("The 10th product details are: ");
            System.out.println(productNames[9]+" - "+productPrices[9]);
        }

    @DataProvider(name="SearchData")
    Object[][] getData() throws IOException {
        String path=System.getProperty("user.dir")+"/src/test/java/testData/SearchData.xlsx";
        int rowNumber= XLUtils.getRowCount(path,"Sheet1");
        int columnCount=XLUtils.getCellCount(path,"Sheet1",1);

        String loginData[][]=new String[rowNumber][columnCount];
        for (int i=1;i<=rowNumber;i++){
            for(int j=0;j<columnCount;j++){
                loginData[i-1][j]=XLUtils.getCellData(path,"Sheet1",i,j);
            }
        }
        return loginData;
    }


    public void setData(String [] productName,String[] productPrice) throws IOException {
        String path=System.getProperty("user.dir")+"/src/test/java/testData/SearchData.xlsx";

        for (int i=1;i<=productName.length;i++){
            XLUtils.setCellData(path,"Sheet2",i,0,productName[i-1]);
            XLUtils.setCellData(path,"Sheet2",i,1,productPrice[i-1]);
        }
    }
}
