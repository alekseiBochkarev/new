package pages;

import guru.qa.demoqa.AutomationPracticeFormWithTestDataTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static config.BaseSetup.WAITING_TIMEOUT;

public class AutomationPracticeFormPage {
    //pages
    CalendarComponent calendarComponent = new CalendarComponent();
    //locators
    SelenideElement firstNameInput = $("input#firstName");
    SelenideElement lastNameInput = $("input#lastName");
    SelenideElement emailInput = $("input#userEmail");
    SelenideElement mobilePhoneInput = $("input[id=userNumber]");
    SelenideElement dateOfBirthInput = $("input[id=dateOfBirthInput]");
    SelenideElement subjectsInput = $("#subjectsInput");
    SelenideElement uploadPictureInput = $("#uploadPicture");
    SelenideElement currentAddressTextarea = $("#currentAddress");
    SelenideElement submitButton = $("#submit");
    SelenideElement resultsTitle = $("#example-modal-sizes-title-lg");
    SelenideElement resultsBody = $(".modal-body");

    //actions
    public AutomationPracticeFormPage openPage () {
        open("/automation-practice-form");
        //getWebDriver().manage().window().maximize();
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
        return this;
    }

    public void setGender (String gender) {
        $x("//input[@name='gender' and @value='" + gender + "']/following-sibling::label").click();
    }

    public void setDateOfBirth (String day, String month, String year) {
        dateOfBirthInput.click();
        calendarComponent.setDate(day, month, year);
    }

    public void setHobbies (String hobbies) {
        $("#hobbiesWrapper").$(byText(hobbies)).click();
    }

    public void setStateAndCity (String state, String city) {
        SelenideElement stateCity = $("#stateCity-wrapper");
        List<SelenideElement> elementsJustForClick = new ArrayList<>();
        elementsJustForClick.add(stateCity.$(byText("Select State")));
        elementsJustForClick.add(stateCity.$(byText(state)));
        elementsJustForClick.add(stateCity.$(byText("Select City")));
        elementsJustForClick.add(stateCity.$(byText(city)));
        for (SelenideElement elementJustForClick : elementsJustForClick) {
            elementJustForClick.shouldBe(Condition.visible, Duration.ofMillis(WAITING_TIMEOUT)).click();
        }
    }

    public void fillForm (AutomationPracticeFormWithTestDataTest.TestDataForAPForm testDataForAPForm) {
        firstNameInput.setValue(testDataForAPForm.getName());
        lastNameInput.setValue(testDataForAPForm.getLastName());
        emailInput.setValue(testDataForAPForm.getEmail());
        setGender(testDataForAPForm.getGender());
        mobilePhoneInput.setValue(testDataForAPForm.getMobilePhone());
        setDateOfBirth(testDataForAPForm.getDay(), testDataForAPForm.getMonth(), testDataForAPForm.getYear());
        subjectsInput.setValue(testDataForAPForm.getSubject()).pressEnter();
        setHobbies(testDataForAPForm.getHobby());
        uploadPictureInput.uploadFromClasspath(testDataForAPForm.getImgPath());
        currentAddressTextarea.setValue(testDataForAPForm.getAddress());
        setStateAndCity(testDataForAPForm.getState(), testDataForAPForm.getCity());
        submitButton.click();
    }

    public void checkFormOfResult (AutomationPracticeFormWithTestDataTest.TestDataForAPForm testDataForAPForm) {
        resultsTitle.shouldHave(text("Thanks for submitting the form"));
        resultsBody.shouldHave(
                text(testDataForAPForm.getExpectedFullName()),
                text(testDataForAPForm.getEmail()),
                text(testDataForAPForm.getGender()),
                text(testDataForAPForm.getMobilePhone()),
                text(testDataForAPForm.getDay() + " " + testDataForAPForm.getMonth() + "," + testDataForAPForm.getYear()),
                text(testDataForAPForm.getSubject()),
                text(testDataForAPForm.getHobby()),
                text(testDataForAPForm.getImgPath().substring(4)),
                text(testDataForAPForm.getAddress()),
                text(testDataForAPForm.getState() + " " + testDataForAPForm.getCity())
        );
    }
}
