package ru.yandex.practikum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    // локатор кнопки "Заказать" (верхняя)
    private final By buttonOrderTop = By.xpath(".//div[starts-with(@class,'Header_Nav')]//button[text()='Заказать']");
    // локатор кнопки "Заказать" (нижняя)
    private final By buttonOrderBottom = By.xpath(".//div[contains(@class,'FinishButton')]//button[text()='Заказать']");
    // раздел "Вопросы о важном"
    private final By sectionFaq = By.xpath(".//div[starts-with(@class,'Home_FAQ')]");
    // элемент раздела
    private final By accordionItem = By.className("accordion__item");
    // кнопка (стрелочка) с вопросом
    private final By accordionButton = By.className("accordion__button");
    // панель с ответом
    private final By accordionPanel = By.className("accordion__panel");
    private final By imageScooter = By.xpath(".//img[@alt = 'Scooter blueprint']");

    // конструктор класса
    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    // дождитесь когда прогрузятся "Вопросы о важном"
    public void waitForLoadFaq() {
        WebElement faqElement = driver.findElement(sectionFaq);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(sectionFaq));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", faqElement);
    }
    // дождитесь загрузки страницы
    public void waitForLoadPage() {
        WebElement imageElement = driver.findElement(imageScooter);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(imageScooter));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", imageElement);
    }
    // существует ли элемент
    public boolean isElementExist(By locatorBy) {
        try {
            driver.findElement(locatorBy);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    // возвращает список вопрос-ответов
    public List<WebElement> getFaqItems(){
        return driver.findElements(accordionItem);
    }

    public boolean isButtonClickable(WebElement faqElement) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(faqElement.findElement(accordionButton)));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    // получаем вопросы
    public String getQuestion(WebElement faqElement) {
        return faqElement.findElement(accordionButton).getText();
    }
    // получаем ответы
    public String getAnswer(WebElement faqElement) {
        return faqElement.findElement(accordionPanel).getText();
    }
    // нажмите на кнопку "Заказать"
    public void clickOrder(int indexButton) {
        switch (indexButton) {
            case 0:
                driver.findElement(buttonOrderTop).click();
                break;
            case 1:
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                WebElement buttonOrder = driver.findElement(buttonOrderBottom);
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (buttonOrder.isDisplayed()));
                buttonOrder.click();
                break;
        }
    }
}