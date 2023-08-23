package junit.improved;

import core.Browser;
import core.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class WebTest {
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        DriverFactory.start(Browser.CHROME, testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quit();
    }

    public static WebDriver getDriver() {
        return DriverFactory.getWebDriver().get();
    }

    public static Actions getActions() {
        return new Actions(DriverFactory.getWebDriver().get());
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(30));
    }
}
