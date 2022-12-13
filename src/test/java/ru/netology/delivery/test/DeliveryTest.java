package ru.netology.delivery.test;

import ru.netology.delivery.data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(By.className("button")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15000));
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(By.className("button")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15000));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(secondMeetingDate);
        $(By.className("button")).click();
        $("[data-test-id='replan-notification']").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15000));
    }
}

