package testng;

import factories.ProductDetailsFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import models.ProductDetails;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ProductDetailsTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeClass
    public void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        String username = System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESSKEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("user", username);
        ltOptions.put("accessKey", authkey);
        ltOptions.put("build", "Selenium 4");
        ltOptions.put("name",this.getClass().getName());
        ltOptions.put("platformName", "Windows 10");
//        ltOptions.put("seCdp", true);
//        ltOptions.put("selenium_version", "4.0.0");
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void testCleanup() throws InterruptedException {
        driver.quit();
    }

    @Test
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults() throws InterruptedException {
        var expectedProduct1 = new ProductDetails();
        expectedProduct1.setName("iPod Touch");
        expectedProduct1.setId(32);
        expectedProduct1.setPrice("$194.00");
        expectedProduct1.setModel("Product 5");
        expectedProduct1.setBrand("Apple");
        expectedProduct1.setWeight("5.00kg");

        driver.navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = driver.findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct1.getName());

        var searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct1.getId());
        var productsLink = driver.findElement(By.xpath(productLinkXpath));
        actions.moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = driver.findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = driver.findElement(By.xpath("//h3[@data-update='price']"));

        Assert.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assert.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }

    @Test(dataProvider = "productNames")
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults(String productName){
        var expectedProduct = ProductDetailsFactory.createDefault().stream().filter(x -> x.getName().equals(productName)).findFirst().get();

        driver.navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = driver.findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct.getName());

        var searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct.getId());
        var productsLink = driver.findElement(By.xpath(productLinkXpath));
        actions.moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct.getId());
        var productsQuickViewButton = driver.findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = driver.findElement(By.xpath("//h3[@data-update='price']"));

        Assert.assertEquals(expectedProduct.getName(), productTitle.getText());
        Assert.assertEquals(expectedProduct.getPrice(), productPrice.getText());
    }

    @DataProvider(name = "productNames")
    public Object[][] createProductNames() {
        return new Object[][] {
                {"iPod Touch"},
                {"iPod Shuffle"},
                {"iPod Nano"}
        };
    }

    // CSV Source without file
    @Test(dataProvider = "csvProductDetails", groups = "nightlyRun")
//    @Tag("nightlyRun")
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults(String name, Integer id, String price, String model, String brand, String weight){
        var expectedProduct1 = new ProductDetails();
        expectedProduct1.setName(name);
        expectedProduct1.setId(id);
        expectedProduct1.setPrice(price);
        expectedProduct1.setModel(model);
        expectedProduct1.setBrand(brand);
        expectedProduct1.setWeight(weight);

        driver.navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = driver.findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct1.getName());

        var searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct1.getId());
        var productsLink = driver.findElement(By.xpath(productLinkXpath));
        actions.moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = driver.findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = driver.findElement(By.xpath("//h3[@data-update='price']"));

        Assert.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assert.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }

    @DataProvider(name = "csvProductDetails")
    public Object[][] createCSVProductDetails() {
        return new Object[][] {
                {"iPod Touch", 32, "$194.00", "Product 5", "Apple", "5.00kg"},
                {"iPod Shuffle", 34, "$182.00", "Product 7", "Apple", "5.00kg"},
                {"iPod Nano", 36, "$122.00", "Product 9", "Apple", "5.00kg"}
        };
    }

    // TODO: Explain how to run tests from CLI

    private String getCompareProductDetailsCellXPath(String cellName, int productCompareIndex) {
        return getCompareProductDetailsCellXPath(cellName, productCompareIndex, "");
    }

    private String getCompareProductDetailsCellXPath(String cellName, int productCompareIndex, String xpathSuffix) {
        String cellXpath = String.format("//table/tbody/tr/td[text()='%s']/following-sibling::td[%d]", cellName, productCompareIndex);

        if (!StringUtils.isEmpty(xpathSuffix)) {
            cellXpath = cellXpath + xpathSuffix;
        }

        return cellXpath;
    }
}