package ru.netology.statistic.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.statistic.data.DataGenerator;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        // Планирование
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $("[data-test-id='button']").click();

        $("[data-test-id='success-notification']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована"));

        // Перепланирование
        $("[data-test-id='date'] input").doubleClick().sendKeys(secondMeetingDate);
        $("[data-test-id='button']").click();

        $("[data-test-id='replan-notification']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Перепланировать"));

        $("[data-test-id='replan-notification'] .button").click();

        $("[data-test-id='success-notification']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(secondMeetingDate));
    }
}
