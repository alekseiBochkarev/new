package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseSetup {
    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    public static final long WAITING_TIMEOUT = 10000;

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.holdBrowserOpen = false;
        Configuration.baseUrl = System.getProperty("baseUrl","https://demoqa.com");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");;
        Configuration.remote = "https://" + config.login() + ":" + config.password() + "@" + System.getProperty("selenoidPath","selenoid.autotests.cloud") + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

}
