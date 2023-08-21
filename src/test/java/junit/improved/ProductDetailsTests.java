package junit.improved;

import io.github.bonigarcia.wdm.WebDriverManager;
import models.ProductDetails;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        driver.manage().window().maximize();
    }

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

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct1.getId());
        var productsLink = driver.findElement(By.xpath(productLinkXpath));
        actions.moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = driver.findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = driver.findElement(By.xpath("//h3[@data-update='price']"));

        Assertions.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assertions.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }
}