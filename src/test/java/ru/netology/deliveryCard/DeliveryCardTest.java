package ru.netology.deliveryCard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCardTest {

    @BeforeEach
    void openWeb() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidData() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Санкт-Петербург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Гермиона Грэйнджер");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79218885533");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $x("//*[@data-test-id='notification']").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate), Duration.ofSeconds(15));
    }


    @Test
    void shouldNotValidCity() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Хогвартс");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Рубеус Хагрид");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79218885533");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text);
    }

    @Test
    void shouldZeroCity() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Сириус Блэк");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79218885533");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidDataMeet() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Йошкар-Ола");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Парвати Патил");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79218885533");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").getText();
        assertEquals("Заказ на выбранную дату невозможен", text);
    }

    @Test
    void shouldNotValidName() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Москва");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Harry");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79218885533");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }


    @Test
    void shouldNotValidPhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Элиста");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Драко Малфой");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("Люциус");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldZeroPhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Грозный");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Авада Кедавра");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidCheckBox() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Владивосток");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Илья Лагутенко");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79210002323");
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"agreement\"]").getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", text);
    }

}
