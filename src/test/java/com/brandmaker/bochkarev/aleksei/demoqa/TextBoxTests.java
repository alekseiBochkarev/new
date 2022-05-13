package com.brandmaker.bochkarev.aleksei.demoqa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TextBoxTests {

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = false;
        Configuration.baseUrl = "https://demoqa.com";
    }

    @Test
    void fillFormTest () {
        String name = "Test Name";
        String email = "Test@Email.com";
        String currentAddress = "Test Address";
        String permanentAddress = "Test permanentAddress";

        open("/text-box");
        getWebDriver().manage().window().maximize();
        $("[id=userName]").setValue(name);
        $("[id=userEmail]").setValue(email);
        $("[id=currentAddress]").setValue(currentAddress);
        $("[id=permanentAddress]").setValue(permanentAddress);
        $("[id=submit]").click();

        // Assertions
        $("[id=output]").shouldHave(text(name), text(email),
                text(currentAddress), text(permanentAddress));
        $("[id=output] [id=name]").shouldHave(text(name));
        $("[id=output]").$("[id=name]").shouldHave(text(name));

        $("p[id=permanentAddress]").shouldHave(text(permanentAddress));
    }
}
