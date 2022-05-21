package guru.qa.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import config.BaseSetup;
import org.junit.jupiter.api.Disabled;
import utils.ToolsForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AutomationPracticeFormTest extends BaseSetup {
    enum Gender {
        Male,
        Female,
        Other
    }

    @BeforeEach
    void openPage() {
        open("/automation-practice-form");
        getWebDriver().manage().window().maximize();
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
    }

    @Test
    @Disabled ("we have new improved test")
    void homeFormsTest() {
        /** Test Data **/
        String name = ToolsForTests.getRandomString(8, true, false);
        String lastName = ToolsForTests.getRandomString(9, true, false);
        String email = ToolsForTests.getRandomString(7, true, true) +
                "@" +
                ToolsForTests.getRandomString(6, true, false) +
                ".com";
        String gender = Gender.values()[ToolsForTests.randomIntWithInterval(0, 2)].toString();
        //System.out.println(gender);
        String mobilePhone = ToolsForTests.getRandomString(10, false, true);
        LocalDate birth = LocalDate.of(ToolsForTests.randomIntWithInterval(1950, 2000),
                ToolsForTests.randomIntWithInterval(1, 12),
                ToolsForTests.randomIntWithInterval(1, 28));
        String month = birth.getMonth().toString().charAt(0)                 //название месяца
                + birth.getMonth().toString().substring(1).toLowerCase();
        String subject = "Accounting";
        String hobby = "Sports";
        String imgPath = "img/screenNinja.png";
        String address = "96, Bolshevistskaya, Novosibirsk, 630083";
        String state = "Haryana";
        String city = "Panipat";
        SelenideElement stateCity = $("#stateCity-wrapper");
        List<SelenideElement> elementsJustForClick = new ArrayList<>();
        elementsJustForClick.add(stateCity.$(byText("Select State")));
        elementsJustForClick.add(stateCity.$(byText(state)));
        elementsJustForClick.add(stateCity.$(byText("Select City")));
        elementsJustForClick.add(stateCity.$(byText(city)));

        /** Test Actions **/
        $("input#firstName").setValue(name);
        $("input#lastName").setValue(lastName);
        $("input#userEmail").setValue(email);
        $x("//input[@name='gender' and @value='" + gender + "']/following-sibling::label").click();
        $("input[id=userNumber]").setValue(mobilePhone);
        //set date of birth
        $("input[id=dateOfBirthInput]").click();
        $("select.react-datepicker__month-select").selectOption(month);
        $("select.react-datepicker__year-select").selectOption(String.valueOf(birth.getYear()));
        $x("//*[contains(@aria-label, '" + month + "')]" +
                "[contains(@aria-label, '" + birth.getDayOfMonth() + "')]" +
                "[contains(@aria-label, '" + birth.getYear() + "')]").click();
        $("#subjectsInput").setValue(subject).pressEnter();
        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFromClasspath(imgPath);
        $("#currentAddress").setValue(address);
        for (SelenideElement elementJustForClick : elementsJustForClick) {
            elementJustForClick.shouldBe(Condition.visible, Duration.ofMillis(WAITING_TIMEOUT)).click();
        }
        $("#submit").click();
        /** Asserts ***/
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".modal-body").shouldHave(
                text(name + " " + lastName),
                text(email),
                text(gender),
                text(mobilePhone),
                text(birth.getDayOfMonth() + " " + month + "," + birth.getYear()),
                text(subject),
                text(hobby),
                text(imgPath.substring(4)),
                text(address),
                text(state + " " + city)
        );
    }
}
