package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class DriverFactory {
    private static final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    @Getter
    private static ThreadLocal<WebDriver> webDriver;
    @Getter
    private static ThreadLocal<WebDriverWait> webDriverWait;
    @Getter
    private static ThreadLocal<Actions> actions;

    public static void start(Browser browser, String testName) {
        webDriver = new ThreadLocal<>();
        webDriverWait = new ThreadLocal<>();
        actions = new ThreadLocal<>();

        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> ltOptions = new HashMap<>();
        String username = System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESSKEY");
        ltOptions.put("user",  username);
        ltOptions.put("accessKey", authkey);

        ltOptions.put("build", "your build name");

        ltOptions.put("name", testName);

        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("browserName", "Chrome");
        ltOptions.put("console","true");
        ltOptions.put("network",true);
        ltOptions.put("visual",true);
        ltOptions.put("timezone","UTC+02:00");
        capabilities.setCapability("LT:Options", ltOptions);
        capabilities.setCapability("browserName", browser.getName());

        try {
            String url = String.format("https://%s:%s@hub.lambdatest.com/wd/hub", username, authkey);
            WebDriver remoteDriver = new RemoteWebDriver(new URL(url), capabilities);
            webDriver.set(remoteDriver);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        actions.set(new Actions(webDriver.get()));
        webDriverWait.set(new WebDriverWait(webDriver.get(), Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT)));
    }

    public static void start(Browser browser) {
        webDriver = new ThreadLocal<>();
        webDriverWait = new ThreadLocal<>();
        actions = new ThreadLocal<>();

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver.set(new ChromeDriver());
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webDriver.set(new FirefoxDriver());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver.set(new EdgeDriver());
                break;
            case SAFARI:
                webDriver.set(new SafariDriver());
                break;
            default:
                throw new IllegalArgumentException(browser.name());
        }

        actions.set(new Actions(webDriver.get()));
        webDriverWait.set(new WebDriverWait(webDriver.get(), Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT)));
    }

    public static void quit() {
        webDriver.get().quit();
    }
}
