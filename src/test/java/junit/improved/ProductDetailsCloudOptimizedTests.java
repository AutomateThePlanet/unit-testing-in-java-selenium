package junit.improved;

import factories.ProductDetailsFactory;
import models.ProductDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import providers.ProductDetailsCustomArgumentsProvider;

public class ProductDetailsCloudOptimizedTests extends WebTest {
    @Test
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults() throws InterruptedException {
        var expectedProduct1 = new ProductDetails();
        expectedProduct1.setName("iPod Touch");
        expectedProduct1.setId(32);
        expectedProduct1.setPrice("$194.00");
        expectedProduct1.setModel("Product 5");
        expectedProduct1.setBrand("Apple");
        expectedProduct1.setWeight("5.00kg");

        getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = getDriver().findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct1.getName());

        var searchButton = getDriver().findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct1.getId());
        var productsLink = getDriver().findElement(By.xpath(productLinkXpath));
        getActions().moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = getDriver().findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = getDriver().findElement(By.xpath("//h3[@data-update='price']"));

        Assertions.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assertions.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }

    @ParameterizedTest(name = "{index}. correct information displayed in quick view when open product = '{0}' from search results")
    @ValueSource(strings = {
            "iPod Touch",
            "iPod Shuffle",
            "iPod Nano"
    })
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults(String productName){
        var expectedProduct = ProductDetailsFactory.createDefault().stream().filter(x -> x.getName().equals(productName)).findFirst().get();

        getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = getDriver().findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct.getName());

        var searchButton = getDriver().findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct.getId());
        var productsLink = getDriver().findElement(By.xpath(productLinkXpath));
        getActions().moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct.getId());
        var productsQuickViewButton = getDriver().findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = getDriver().findElement(By.xpath("//h3[@data-update='price']"));

        Assertions.assertEquals(expectedProduct.getName(), productTitle.getText());
        Assertions.assertEquals(expectedProduct.getPrice(), productPrice.getText());
    }

    // CSV Source without file
    @ParameterizedTest
    @CsvSource(value = {
            "iPod Touch,32,$194.00,Product 5,Apple,5.00kg",
            "iPod Shuffle,34,$182.00,Product 7,Apple,5.00kg",
            "iPod Nano,36,$122.00,Product 9,Apple,5.00kg",},
            delimiter = ',')
    @Tag("nightlyRun")
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults(String name, Integer id, String price, String model, String brand, String weight){
        var expectedProduct1 = new ProductDetails();
        expectedProduct1.setName(name);
        expectedProduct1.setId(id);
        expectedProduct1.setPrice(price);
        expectedProduct1.setModel(model);
        expectedProduct1.setBrand(brand);
        expectedProduct1.setWeight(weight);

        getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = getDriver().findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct1.getName());

        var searchButton = getDriver().findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct1.getId());
        var productsLink = getDriver().findElement(By.xpath(productLinkXpath));
        getActions().moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = getDriver().findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = getDriver().findElement(By.xpath("//h3[@data-update='price']"));

        Assertions.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assertions.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }

    @ParameterizedTest
    @ArgumentsSource(ProductDetailsCustomArgumentsProvider.class)
    @Tag("nightlyRun")
    public void correctInformationDisplayedInQuickView_whenOpenProductFromSearchResults1(String name, Integer id, String price, String model, String brand, String weight){
        var expectedProduct1 = new ProductDetails();
        expectedProduct1.setName(name);
        expectedProduct1.setId(id);
        expectedProduct1.setPrice(price);
        expectedProduct1.setModel(model);
        expectedProduct1.setBrand(brand);
        expectedProduct1.setWeight(weight);

        getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/");
        var searchInput = getDriver().findElement(By.xpath("//input[@name='search']"));


        searchInput.sendKeys(expectedProduct1.getName());

        var searchButton = getDriver().findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        // to be reused
        var productLinkXpath = String.format("//a[contains(@id, 'mz-product-grid-image-%d')]", expectedProduct1.getId());
        var productsLink = getDriver().findElement(By.xpath(productLinkXpath));
        getActions().moveToElement(productsLink).perform();
        var productsQuickViewButtonXpath = String.format("//button[contains(@class, 'quick-view-%d')]", expectedProduct1.getId());
        var productsQuickViewButton = getDriver().findElement(By.xpath(productsQuickViewButtonXpath));
        productsQuickViewButton.click();

        var productTitle = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='product-quick-view']/div/div/div/div/h1")));
        var productPrice = getDriver().findElement(By.xpath("//h3[@data-update='price']"));

        Assertions.assertEquals(expectedProduct1.getName(), productTitle.getText());
        Assertions.assertEquals(expectedProduct1.getPrice(), productPrice.getText());
    }
}