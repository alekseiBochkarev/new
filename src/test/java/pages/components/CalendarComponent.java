package pages.components;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CalendarComponent {
    public void setDate(String day, String month, String year) {
        $("select.react-datepicker__month-select").selectOption(month);
        $("select.react-datepicker__year-select").selectOption(year);
        $x("//*[contains(@aria-label, '" + month + "')]" +
                "[contains(@aria-label, '" + day + "')]" +
                "[contains(@aria-label, '" + year + "')]").click();
    }
}
