package junit.improved;

import io.github.bonigarcia.wdm.WebDriverManager;
import models.ProductDetails;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ProductDetailsTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

//    @BeforeEach
//    public void setUp() throws MalformedURLException {
//        String username = System.getenv("LT_USERNAME");
//        String authkey = System.getenv("LT_ACCESSKEY");
//        String hub = "@hub.lambdatest.com/wd/hub";
//
//        var capabilities = new DesiredCapabilities();
//        capabilities.setCapability("browserName", "Chrome");
//        capabilities.setCapability("browserVersion", "latest");
//        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
//        ltOptions.put("user", username);
//        ltOptions.put("accessKey", authkey);
//        ltOptions.put("build", "Selenium 4");
//        ltOptions.put("name",this.getClass().getName());
//        ltOptions.put("platformName", "Windows 10");
//        capabilities.setCapability("LT:Options", ltOptions);
//
//        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
//        actions = new Actions(driver);
//        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        driver.manage().window().maximize();
//    }

    @AfterEach
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

        var productLinkXpath = String.format("//a[contains(@id, '%d')]", expectedProduct1.getId());
        var productsLink = driver.findElement(By.xpath(productLinkXpath));
        actions.moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = driver.findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        // Assert
        var productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = driver.findElement(By.xpath("//h3[@data-update='price']"));

        Assertions.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assertions.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }

    @Test
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults1() throws InterruptedException {
    }
}