package main;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {

    public static AppiumDriver<MobileElement> driver;

    // Метод запуска указанного Android приложения через Appium на указанном порту
    public static void setUpAndroid (String deviceName, String platformVersion, String port) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", deviceName);
        desiredCapabilities.setCapability("platformVersion", platformVersion);
        desiredCapabilities.setCapability("automationName", "Appium");
        desiredCapabilities.setCapability("appPackage", "com.savetime");
        desiredCapabilities.setCapability("appActivity", "com.savetime.MainActivity");
        desiredCapabilities.setCapability("app", "C:\\Automation\\apks\\client-12-apr.apk");
        desiredCapabilities.setCapability("autoGrantPermissions", true);
        //desiredCapabilities.setCapability("setWebContentsDebuggingEnabled", true);

        try {
            driver = new AndroidDriver<>(new URL("http://127.0.0.1:" + port + "/wd/hub"), desiredCapabilities);
            driver.rotate(ScreenOrientation.PORTRAIT);
        } catch (MalformedURLException e) { e.printStackTrace(); }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        switchContext("WEBVIEW");
    }

    // Метод для смены контекста приложения
    private static void switchContext (String context) {
        Set<String> con = driver.getContextHandles();
        for (String c: con) {
            if (c.contains(context)) {
                driver.context(c);
                break;
            }
        }
        System.out.println("Текущий контекст: " + driver.getContext());
    }


    // Метод для вертикального свайпа
    @Step("Свайпаем экран вниз")
    static void verticalSwipe() {
        switchContext("NATIVE_APP");
        Dimension dim = driver.manage().window().getSize();
        driver.swipe(dim.getWidth()/2, (int)(dim.getHeight()*0.8), dim.getWidth()/2,
                (int)(dim.getHeight()*0.2), 500);
        switchContext("WEBVIEW");
    }

    private static void wait300mills() {
        try { Thread.sleep(300); } catch (Exception e) {System.out.println("Wait на 1 секунду - не сработал!");}
    }

    private static void wait1sec() {
        try { Thread.sleep(1000); } catch (Exception e) {System.out.println("Wait на 1 секунду - не сработал!");}
    }

    private static void wait3sec() {
        try { Thread.sleep(3000); } catch (Exception e) {System.out.println("Wait на 3 секунды - не сработал!");}
    }

    static void wait5sec() {
        try { Thread.sleep(5000); } catch (Exception e) {System.out.println("Wait на 5 секунд - не сработал!");}
    }

    static void wait10sec() {
        try { Thread.sleep(10000);} catch (Exception e) {System.out.println("Wait на 10 секунд - не сработал!");}
    }

    // Метод получения псевдослучайного целого числа от min до max (включая max)
    static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    // Ожидание появления элемента
    private static WebElement wfePresent (By by, String errMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(errMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    // Ожидание появления элемента и клик по нему
    private static void wfeClick (By by, String errMessage, long timeout) {
        WebElement element = wfePresent(by, errMessage, timeout);
        element.click();
    }

    // Ожидание появления элемента и ввод заданной строки в него
    private static void wfeSendKeys (By by, String value, String errMessage, long timeout) {
        WebElement element = wfePresent(by, errMessage, timeout);
        element.sendKeys(value);
    }

    // Ожидание появления элемента, очищение и ввод заданной строки в него
    private static void wfeClearSendKeys (By by, String value, String errMessage, long timeout) {
        WebElement element = wfePresent(by, errMessage, timeout);
        element.clear();
        element.click();
        element.sendKeys(value);
    }

    // Метод получения локатора XPATH элемента по его названию в виде строки
    static String getElementLocator(String element) {
        HashMap<String, String> elements = new HashMap<>();
        elements.put("Авторизация - поле ввода телефона", "//input[@type='tel']");
        elements.put("Карта - кнопка ПРИСТУПИТЬ К ПОКУПКАМ", "//div[@class='button button-big button-fill']");
        elements.put("Карта - кнопка Изменить адрес", "//span[@class='change-address']/span");
        elements.put("Карта - изменение адреса - поле поиска адреса", "//input[@id='searchAddress']");
        elements.put("Карта - изменение адреса - первый адрес в списке", "(//div[@class='item-title-row'])[1]");
        elements.put("Бургер-меню", "//i[@class='ic ic-menu']");
        elements.put("Авторизация - кнопка ПРОДОЛЖИТЬ", "//button[@type='submit']");
        elements.put("Авторизация - поле ввода смс", "//input[@type='number']");
        elements.put("Профиль - заголовок экрана", "//div[contains(text(), 'Профиль')]");
        elements.put("Профиль - поле Сейвкоины", "//div[contains(text(), 'Сейвкоины')]");
        elements.put("Профиль - поле Мои заказы", "//div[contains(text(), 'Мои заказы')]");
        elements.put("Сейвкоины - поле СЕЙВКОИНОВ", "//span[@class='coin-title']");
        elements.put("Сейвкоины - поле с количеством сейвкоинов", "//span[@class='coin-count']");
        elements.put("Кнопка НАЗАД", "//i[@class='icon icon-back']");
        elements.put("Список брендов - Перекресток", "//*[@id='framework7-root']/div[2]/div/div[2]/div[2]/a[2]/img");
        elements.put("Список брендов - поле с адресом", "//div[@class='title']");
        elements.put("Список брендов - поле с адресом - Изменить адрес", "//div[@class='item-title']/b");
        elements.put("Подсказка о любимых товарах - крестик", "//i[@class='ic ic-close-learn']");
        elements.put("Перекресток - Замороженные продукты - Мороженое", "//div[text()='Мороженое']");
        elements.put("Подкатегория - кнопка корзины", "//*[@id='framework7-root']/div[2]/div[3]/div[1]/" +
                "div/div[3]/a[2]/i");
        elements.put("Подкатегория - кнопка плюс", "//div[@class='cart-add icon icon-plus']");
        elements.put("Корзина - кнопка ОФОРМИТЬ ЗАКАЗ", "//div[@class='button button-large button-fill " +
                "button-darkgray']");
        elements.put("Чекаут - экран Доставка - поле ввода подъезда", "//input[@name='porch']");
        elements.put("Чекаут - экран Доставка - поле ввода этажа", "//input[@name='floor']");
        elements.put("Чекаут - экран Доставка - поле ввода квартиры/офиса", "//input[@name='flat']");
        elements.put("Чекаут - экран Доставка - поле ввода кода домофона", "//input[@name='code']");
        elements.put("Чекаут - экран Доставка - кнопка ДАЛЕЕ", "//button[text()='Далее']");
        elements.put("Чекаут - экран Время доставки - кнопка ДАЛЕЕ", "//*[@id='framework7-root']/div[2]/div[6]/" +
                "div[2]/div[2]/button");
        elements.put("Чекаут - поле ввода комментария", "//textarea[@class='input-with-value']");
        elements.put("Чекаут - кнопка с картой клиента", "//*[@id='framework7-root']/div[2]/div[7]/div[2]/div[3]/" +
                "div[4]/ul/li/a/div[2]");
        elements.put("Чекаут - поле с выбранной картой клиента", "(//a[@class='item-content item-link']/" +
                "div[@class='item-media']/../div/div)[1]");
        elements.put("Чекаут - всплывашка Способ оплаты - кнопка Добавить карту", "(//div[contains(text(), " +
                "'Добавить карту')])[2]");
        elements.put("Чекаут - всплывашка Способ оплаты - кнопка Добавить карту (их мало)", "//div[contains(text(), " +
                "'Добавить карту')]");
        elements.put("Алерт Добавление карты - кнопка Хорошо", "android:id/button2");
        elements.put("Добавление карты - поле ввода номера карты", "ccnumber");
        elements.put("Добавление карты - поле ввода срока", "expired");
        elements.put("Добавление карты - поле ввода CVC", "iCVC");
        elements.put("Добавление карты - кнопка ОПЛАТИТЬ", "buttonPayment");
        elements.put("Добавление карты - поле ввода пароля", "//android.view.View[@content-desc=\"Payment " +
                "confirmation\"]/android.view.View[2]/android.view.View[14]/android.widget.EditText");
        elements.put("Добавление карты - кнопка Submit", "//android.widget.Button[@content-desc=\"Submit\"]");
        elements.put("Чекаут - кнопка ОПЛАТИТЬ", "//button[text()='Оплатить']");
        elements.put("Алерт Новый заказ - кнопка ОК", "/hierarchy/android.widget.FrameLayout/android.widget." +
                "FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/" +
                "android.widget.LinearLayout/android.widget.Button");
        elements.put("Алерт Новый заказ - кнопка ОК - Samsung J1", "/hierarchy/android.widget.FrameLayout/" +
                "android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/" +
                "android.widget.LinearLayout[3]/android.widget.Button");
        elements.put("Окно заказа - поле с номером заказа", "//div[contains(text(), 'Заказ №')]");
        elements.put("Окно заказа - поле со статусом заказа", "//div[@class='order-status']");
        elements.put("Окно оценки заказа - Х звезд", "(//div[@class='rating-star'])");
        elements.put("Окно оценки заказа - кнопка ОТПРАВИТЬ", "//button[text()='Отправить']");
        elements.put("Чекаут - кнопка Активировать промокод", "//div[contains(text(), 'Активировать промокод')]");
        elements.put("Чекаут - поле Промокод", "//div[@class='item-content swipeout-content']/div/" +
                "div[@class='item-title']");
        elements.put("Чекаут - поле Промокод - скидка", "//div[@class='item-content swipeout-content']/div/" +
                "div[@class='item-after']");
        elements.put("Чекаут - поле Всего к оплате - сумма", "(//div[@class='total-block-row total-block-final']" +
                "/span)[2]");
        elements.put("Чекаут - поле Сумма заказа - сумма", "(//div[@class='total-block-row'])[1]/span[2]");
        elements.put("Ввод промокода - поле ввода промокода", "/hierarchy/android.widget.FrameLayout/" +
                "android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/" +
                "android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.EditText");
        elements.put("Ввод промокода - поле ввода промокода - Samsung J1", "/hierarchy/android.widget.FrameLayout/" +
                "android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/" +
                "android.widget.FrameLayout/android.widget.FrameLayout/android.widget.EditText");
        elements.put("Чекаут - ползунок Сейвкоинов", "//div[@class='range-knob-wrap']");
        elements.put("Чекаут - поле Списать сейвкоины", "//li[@class='coin-list-item']/div/div[1]/div");
        elements.put("Попап 'Повторить заказ' - кнопка 'Понятно'", "//button[contains(text(), 'Понятно')]");


        return elements.get(element);
    }

    // Метод для клика по случайнму бренду на титульной странице приложения
    @Step("Кликаем на случайный бренд")
    static void clickRandomBrand() {
        int random = rnd(2, 3); // Генерация случайного числа от "a" до "b"
        wfeClick(By.xpath("(//div[@class='shop-list-brands']/a)[" + random + "]"),
                "Не могу найти кнопку бренда", 20);
        wait3sec();
    }

    // Метод для клика по конкретному бренду на титульной странице приложения
    @Step("Кликаем на конкретный бренд")
    static void clickBrand(int index) {
        wfeClick(By.xpath("(//div[@class='shop-list-brands']/a)[" + index + "]"),
                "Не могу найти кнопку бренда", 20);
        wait3sec();
    }

    // Метод для клика по случайной категории в каталоге магазина
    @Step("Кликаем на случайную категорию")
    static void clickRandomCategory() {
        int count = driver.findElementsByXPath("//div[@class='thumb']").size(); // Кол-во категорий
        int a = 2; // Начальное значение диапазона - "от"
        int b = count - 1; // Конечное значение диапазона - "до"
        int random = rnd(a, b); // Генерация случайного числа от "a" до "b"
        wfeClick(By.xpath("(//div[@class='thumb'])[" + random + "]"),
                "Не могу найти кнопку категории", 20);
        wait5sec();
    }

    // Метод для клика по случайной подкатегории в выбранной категории в каталоге магазина
    @Step("Кликаем на случайную подкатегорию")
    static void clickRandomSubCategory() {
        int a = 1; // Начальное значение диапазона - "от"
        int b = driver.findElementsByXPath("//div[@class='item-inner']").size(); // Кол-во подкатегорий
        if (b > 8) b = 8;
        try {
            for (int i = 0; i < 10; i++) {
                int random = rnd(a, b); // Генерация случайного числа от "a" до "b"
                if (driver.findElementByXPath("(//div[@class='item-inner'])[" + random + "]/div").isDisplayed()){
                    wfeClick(By.xpath("(//div[@class='item-inner'])[" + random + "]/div"),
                            "Не могу найти кнопку подкатегории", 10);
                    break;
                }
            }
            wait1sec();
        } catch (ElementNotVisibleException e) {
            System.out.println("Кнопки подкатегорий not visible");
        }
        wait1sec();
        try {
            wfePresent(By.xpath("//div[@class='sliding title']"), "Не вижу заголовка подкатегории",
                    10);
        } catch (WebDriverException e) {
            wait1sec();
            int x = 1;
            int y = driver.findElementsByXPath("//li[@class='accordion-item accordion-item-opened']/div/" +
                    "div/ul/li/div/div/div").size();
            if (y > 5) y = 5;
            wfeClick(By.xpath("(//li[@class='accordion-item accordion-item-opened']/div/div/ul/li/div/div/div)[" +
                            rnd(x, y) + "]"), "Не вижу выпадающей подкатегории", 5);
        }
    }

    // Метод добавления указнного количества товаров в корзину (когда мы находимся в списке товаров)
    @Step("Накидываем товары в корзину")
    static void addGoodsOnCart(int count) {
        wfePresent(By.xpath("//div[contains (@class, 'cart-add icon icon-plus')]"),
                "Не грузится список товаров", 60);
        int a = 1; // Начальное значение диапазона - "от"
        int b = driver.findElementsByXPath("//div[contains (@class, 'cart-add icon icon-plus')]").size();
        if (b > 12) b = 12;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int random = rnd(a, b);
            list.add(random);
        }
        Collections.sort(list);
        for (Integer in: list) {
            try {
                if (driver.findElementsByXPath("(//a[contains (@class, 'product')])[" + in +
                        "]/div[@class='cart-add icon icon-plus-white icon-plus-dsisabled']").size() > 0)
                    System.out.println("Товара больше нет в наличии");
                else wfeClick(By.xpath("(//div[contains (@class, 'cart-add icon icon-plus')])[" + in + "]"),
                        "Не могу найти кнопку +", 10);
                wait300mills();
            } catch (WebDriverException e) { break; }
        }
        wait1sec();
    }

    @Step("Набираем в корзину товаров на сумму менее 500 рублей")
    static void addGoodsOnCartLessThan500() {
        wfePresent(By.xpath("//div[contains (@class, 'cart-add icon icon-plus')]"),
                "Не грузится список товаров", 40);
        int b = driver.findElementsByXPath("//div[contains (@class, 'cart-add icon icon-plus')]").size();
        if (b > 4) b = 4;
        int sum;
        for (int i = 0; i < 20; i++) {
            int random = rnd(1, b);
            wfeClick(By.xpath("(//div[contains (@class, 'cart-add icon icon-plus')])[" + random + "]"),
                    "Не вижу плюсиков", 10);
            sum = getCartSum();
            if (sum > 300 && sum < 500) break;
            if (sum > 500) {
                wfeClick(By.xpath("(//div[@class='cart-remove icon icon-minus-white'])[" + random + "]"),
                        "Не вижу минусов", 10);
            }
        }
        wait1sec();
    }

    @Step("Набираем в корзину товаров на сумму более 500 рублей")
    static void addGoodsOnCartMoreThan500() {
        wfePresent(By.xpath("//div[contains (@class, 'cart-add icon icon-plus')]"),
                "Не грузится список товаров", 40);
        int b = driver.findElementsByXPath("//div[contains (@class, 'cart-add icon icon-plus')]").size();
        if (b > 4) b = 4;
        int sum;
        for (int i = 0; i < 20; i++) {
            int random = rnd(1, b);
            wfeClick(By.xpath("(//div[contains (@class, 'cart-add icon icon-plus')])[" + random + "]"),
                    "Не вижу плюсиков", 10);
            sum = getCartSum();
            if (sum > 500) break;
        }
        wait1sec();
    }

    // Метод для парсинга суммы корзины
    private static int getCartSum() {
        String sums = driver.findElementByXPath("(//i[@data-cart])[1]")
                .getAttribute("data-cart");
        int sum;
        try {
            sum = Integer.parseInt(sums);
        } catch (NumberFormatException e) {
            sum = Integer.parseInt(sums.substring(0, sums.indexOf(",")));
        }
        return sum;
    }


    // Метод добавления карты оплаты (когда мы находимся в чекауте)
    @Step("Добавляем тестовую карту оплаты, если она ещё не привязана")
    static void addCard(String deviceName) {
        String chosenCard = driver.findElementByXPath(getElementLocator("Чекаут - поле с выбранной " +
                "картой клиента")).getText();
        if ("5100 •••• •••• 0008".equals(chosenCard)) {
            System.out.println("Тестовая карта уже привязана: " + chosenCard);
        }
        else {
            // Кликаем по кнопке с картой клиента
            wfeClick(By.xpath(getElementLocator("Чекаут - кнопка с картой клиента")),
                    "Не могу найти кнопку с картой клиента", 15);
            wait3sec();

            // Во всплывающем окне "Способ оплаты" кликаем кнопку "Добавить карту"
            try {
                wfeClick(By.xpath(getElementLocator("Чекаут - всплывашка Способ оплаты - кнопка Добавить карту")),
                        "Не могу нажать кнопку Добавить карту", 5);
                wait1sec();
            } catch (Exception e) {
                wfeClick(By.xpath(getElementLocator("Чекаут - всплывашка Способ оплаты - кнопка Добавить карту " +
                        "(их мало)")),"Не могу нажать кнопку Добавить карту", 5);
                wait1sec();
            }
            switchContext("NATIVE_APP");
            wait3sec();

            // Нажимаем в алерте кнопку "Хорошо"
            wfeClick(By.id(getElementLocator("Алерт Добавление карты - кнопка Хорошо")),
                    "Не могу найти кнопку алерта Хорошо", 15);
            wait5sec();

            // На экране Добавление карты заполняем все поля в порядке Номер карты, мм/гг, CVC
            wait3sec();
            try {
                wfeClick(By.id(getElementLocator("Добавление карты - поле ввода номера карты")),
                        "Не могу найти поле ввода номера карты", 15);
            } catch (TimeoutException e) {
                wfeClick(By.id(getElementLocator("Добавление карты - поле ввода номера карты")),
                        "Не могу найти поле ввода номера карты", 15);
            }
            tapPhoneNumber(deviceName, "5100000000000008");
            wfeClick(By.id(getElementLocator("Добавление карты - поле ввода срока")),
                    "Не могу найти поле ввода срока карты", 15);
            tapPhoneNumber(deviceName, "1219");
            wfeClick(By.id(getElementLocator("Добавление карты - поле ввода CVC")),
                    "Не могу найти поле ввода CVC карты", 15);
            tapPhoneNumber(deviceName, "123");
            wait1sec();

            // Нажимаем кнопку "ОПЛАТИТЬ 1 Р"
            if (deviceName.equals("32010f78a810444a")) {
                wait3sec();
                TouchAction touch = new TouchAction(driver);
                touch.tap(250,570).perform();
                wait5sec();
            }
            else {
                try {
                    wfeClick(By.id(getElementLocator("Добавление карты - кнопка ОПЛАТИТЬ")),
                            "Не могу найти кнопку ОПЛАТИТЬ 1 Р", 5);
                } catch (Exception e) {
                    wfeClick(By.xpath("//android.widget.Button[@content-desc=\"Оплатить\"]"),
                            "Не могу найти кнопку ОПЛАТИТЬ 1 Р", 5);
                }
                wait5sec();
            }

            // Вводим пароль для привязки карты и жмем Submit
            try {
                wfeSendKeys(By.xpath(getElementLocator("Добавление карты - поле ввода пароля")), "12345678",
                        "Не могу найти поле ввода пароля для привязки карты", 10);
                wait1sec();
            } catch (Exception e) {
                wfeSendKeys(By.xpath(getElementLocator("Добавление карты - поле ввода пароля")), "12345678",
                        "Не могу найти поле ввода пароля для привязки карты", 10);
                wait1sec();
            }
            if (deviceName.equals("32010f78a810444a")) {
                TouchAction touch = new TouchAction(driver);
                touch.tap(250,250).perform();
                wait3sec();
            }
            wfeClick(By.xpath(getElementLocator("Добавление карты - кнопка Submit")),
                    "Не могу найти кнопку Submit", 13);
            switchContext("WEBVIEW");
            wait5sec();
        }
    }

    // Кликнуть ОК на алерте при успешном создании заказа
    @Step("Нажимаем кнопку ОК на алерте \"Новый заказ\"")
    static void clickOkOnAlertNewOrder(String deviceName) {
        switchContext("NATIVE_APP");
        wait5sec();
        if (deviceName.equals("32010f78a810444a")) {
            wait3sec();
            wfeClick(By.xpath(getElementLocator("Алерт Новый заказ - кнопка ОК - Samsung J1")),
                    "Не могу найти кнопку ОК на алерте Новый заказ", 15);
        }
        else {
            wfeClick(By.xpath(getElementLocator("Алерт Новый заказ - кнопка ОК")),
                    "Не могу найти кнопку ОК на алерте Новый заказ", 15);
        }
        wait5sec();
        switchContext("WEBVIEW");
    }

    // Смена адреса клиента на случайный (находясь на экране карты)
    @Step("Смена адреса клиента на случайный")
    static void chooseRandomAddress() {
        // Кликаем по кнопке на карте "Изменить адрес"
        wfeClick(By.xpath(getElementLocator("Карта - кнопка Изменить адрес")),
                "Не могу найти кнопку на карте 'Изменить адрес'", 14);
        wait3sec();

        // Вводим адрес в строке поиска
        String address = STsql.getRandomAddress();
        wfeClearSendKeys(By.xpath(getElementLocator("Карта - изменение адреса - поле поиска адреса")),
                address + ", 1", "Не могу найти поле поиска адреса", 10);
        wait5sec();

        // Кликаем по первому адресу в списке
        wfeClick(By.xpath(getElementLocator("Карта - изменение адреса - первый адрес в списке")),
                "Не могу найти первый адрес в списке", 14);
        wait3sec();

        try {
            // Кликаем на кнопку "ПРИСТУПИТЬ К ПОКУПКАМ"
            wfeClick(By.xpath(getElementLocator("Карта - кнопка ПРИСТУПИТЬ К ПОКУПКАМ")),
                    "Не могу найти кнопку 'ПРИСТУПИТЬ К ПОКУПКАМ'",20);
        } catch (WebDriverException e) {
            HashMap<Integer, String> addresses = new HashMap<>();
            addresses.put(1, "ул. Маросейка, 15");
            addresses.put(2, "Внутренний пр-д, д. 8, стр. 1");
            addresses.put(3, "ул. Новаторов, д. 20, к. 3");
            addresses.put(4, "ул. Молодогвардейская, д. 22, к. 3");
            addresses.put(5, "ул. Пулковская, д. 4А");
            addresses.put(6, "ул. Искры, д. 9А");
            addresses.put(7, "ул. Амурская, д. 2, стр. 9");
            addresses.put(8, "ул. Снайперская, д. 3");
            addresses.put(9, "ул. Люблинская, д. 151");

            int random = rnd(1, 9); // Генерация случайного числа от "1" до "9"

            // Вводим случайный адрес из хэшмапы addresses в строке поиска
            wfeClearSendKeys(By.xpath(getElementLocator("Карта - изменение адреса - поле поиска адреса")),
                    addresses.get(random), "Не могу найти поле поиска адреса", 10);
            wait3sec();

            // Кликаем по первому адресу в списке
            if (driver.findElementsByXPath(getElementLocator("Карта - изменение адреса - первый адрес в списке"))
                    .size() == 0) wait5sec();
            wfeClick(By.xpath(getElementLocator("Карта - изменение адреса - первый адрес в списке")),
                    "Не могу найти первый адрес в списке", 14);
            wait3sec();

            // Кликаем на кнопку "ПРИСТУПИТЬ К ПОКУПКАМ"
            wfeClick(By.xpath(getElementLocator("Карта - кнопка ПРИСТУПИТЬ К ПОКУПКАМ")),
                    "Не могу найти кнопку 'ПРИСТУПИТЬ К ПОКУПКАМ'",20);
        }
        wait1sec();
    }

    // Метод для клика по последнему заказу в списке завершенных заказов
    @Step("Кликаем по первому заказу в списке заказов")
    static void clickLastOrder() {
        wfeClick(By.xpath("(//div[@class='item-subtitle color-gray'])[1]"),
                "Не могу найти кнопку подкатегории", 20);
        wait1sec();
    }

    @Step("Ищем заказ в списке заказов и проверяем наличие плашки 'Заказ отменен'")
    static void assertCancelLastOrder() {
        Assert.assertEquals("Заказ отменен", driver.findElementByXPath(
                "((//div[@class='item-title-row'])[3]/../div/div[2]").getText());
        wait1sec();
    }

    // Метод для ввода заданного номера на разных девайсах, используя нативную клавиатуру
    private static void tapPhoneNumber (String deviceName, String number) {
        char[] chars = number.toCharArray();
        driver.getKeyboard();
        wait3sec();

        // Для Xiaomi Redmi 4A (бело-золотой или темно-синий)
        if (deviceName.equals("111ad0487d44") | deviceName.equals("2fcaae347d74")) {
            for (char ch: chars) {
                TouchAction touch = new TouchAction(driver);
                if (ch == 49) touch.tap(80,930).perform();    // "1"
                if (ch == 50) touch.tap(280,930).perform();   // "2"
                if (ch == 51) touch.tap(460,930).perform();   // "3"
                if (ch == 52) touch.tap(80,1030).perform();   // "4"
                if (ch == 53) touch.tap(280,1030).perform();  // "5"
                if (ch == 54) touch.tap(460,1030).perform();  // "6"
                if (ch == 55) touch.tap(80,1130).perform();   // "7"
                if (ch == 56) touch.tap(280,1130).perform();  // "8"
                if (ch == 57) touch.tap(460,1130).perform();  // "9"
                if (ch == 48) touch.tap(280,1230).perform();  // "0"
            }
        }

        // Для Samsung Galaxy J1 Mini Prime
        if (deviceName.equals("32010f78a810444a")) {
            for (char ch: chars) {
                TouchAction touch = new TouchAction(driver);
                if (ch == 49) touch.tap(70,540).perform();    // "1"
                if (ch == 50) touch.tap(180,540).perform();   // "2"
                if (ch == 51) touch.tap(300,540).perform();   // "3"
                if (ch == 52) touch.tap(70,610).perform();    // "4"
                if (ch == 53) touch.tap(180,610).perform();   // "5"
                if (ch == 54) touch.tap(300,610).perform();   // "6"
                if (ch == 55) touch.tap(70,680).perform();    // "7"
                if (ch == 56) touch.tap(180,680).perform();   // "8"
                if (ch == 57) touch.tap(300,680).perform();   // "9"
                if (ch == 48) touch.tap(180,750).perform();   // "0"
                wait300mills();
            }
        }

        // Для Huawei P20 Lite
        if (deviceName.equals("9WV4C18813005662")) {
            for (char ch: chars) {
                TouchAction touch = new TouchAction(driver);
                if (ch == 49) touch.tap(110,1630).perform();   // "1"
                if (ch == 50) touch.tap(330,1630).perform();   // "2"
                if (ch == 51) touch.tap(540,1630).perform();   // "3"
                if (ch == 52) touch.tap(110,1780).perform();   // "4"
                if (ch == 53) touch.tap(330,1780).perform();   // "5"
                if (ch == 54) touch.tap(540,1780).perform();   // "6"
                if (ch == 55) touch.tap(110,1920).perform();   // "7"
                if (ch == 56) touch.tap(330,1920).perform();   // "8"
                if (ch == 57) touch.tap(540,1920).perform();   // "9"
                if (ch == 48) touch.tap(330,2070).perform();   // "0"
            }
        }

        // Для Xiaomi MiMix2
        if (deviceName.equals("e4986fbc")) {
            for (char ch: chars) {
                TouchAction touch = new TouchAction(driver);
                if (ch == 49) touch.tap(120,1555).perform();   // "1"
                if (ch == 50) touch.tap(400,1555).perform();   // "2"
                if (ch == 51) touch.tap(660,1555).perform();   // "3"
                if (ch == 52) touch.tap(120,1690).perform();   // "4"
                if (ch == 53) touch.tap(400,1690).perform();   // "5"
                if (ch == 54) touch.tap(660,1690).perform();   // "6"
                if (ch == 55) touch.tap(120,1830).perform();   // "7"
                if (ch == 56) touch.tap(400,1830).perform();   // "8"
                if (ch == 57) touch.tap(660,1830).perform();   // "9"
                if (ch == 48) touch.tap(400,1960).perform();   // "0"
            }
        }
    }

    /////////////////////////////////////////////// STEPS ////////////////////////////////////////////////////////////

    @Step("Ждем пока прогрузится приложение")
    static void waitForAppIsLoaded(String deviceName) {
        wait5sec();
        switchContext("WEBVIEW");
        if (deviceName.equals("32010f78a810444a")) { wait5sec(); }
        if (deviceName.equals("e4986fbc")) {
            switchContext("NATIVE_APP");
            wfeClick(By.id("com.android.packageinstaller:id/permission_allow_button"),
                    "Не вижу кнопку РАЗРЕШИТЬ", 10);
            switchContext("WEBVIEW");
            wait5sec();
        }
    }

    @Step("Кликаем на кнопку \"ПРИСТУПИТЬ К ПОКУПКАМ\"")
    static void clickStartShoppingButton() {
        switchContext("WEBVIEW");
        if (driver.findElementsByXPath(getElementLocator("Карта - кнопка ПРИСТУПИТЬ К ПОКУПКАМ")).size() == 0) {
            wait5sec();
            driver.navigate().refresh();
            wait5sec();
        }
        wfeClick(By.xpath(getElementLocator("Карта - кнопка ПРИСТУПИТЬ К ПОКУПКАМ")),
                "Не могу найти кнопку 'ПРИСТУПИТЬ К ПОКУПКАМ'",10);
        wait1sec();
    }

    @Step("Кликаем по Бургер-меню")
    static void clickBurgerMenu() {
        wfeClick(By.xpath(getElementLocator("Бургер-меню")),"Не могу найти Бургер-меню",5);
        wait1sec();
    }

    @Step("Вводим номер телефона")
    static void enterPhone(String deviceName, Client client) {
        switchContext("NATIVE_APP");
        tapPhoneNumber(deviceName, client.getPhone().substring(1));
        switchContext("WEBVIEW");
        wait1sec();
    }

    @Step("Жмем кнопку \"ПРОДОЛЖИТЬ\"")
    static void clickContinueButton() {
        wfeClick(By.xpath(getElementLocator("Авторизация - кнопка ПРОДОЛЖИТЬ")),
                "Не могу найти кнопку 'ПРОДОЛЖИТЬ'",5);
        wait1sec();
    }

    @Step("Вводим код из смс")
    static void enterSmsCode() {
        wfeSendKeys(By.xpath(getElementLocator("Авторизация - поле ввода смс")),"9999",
                "Не могу найти поле для ввода смс-кода",5);
        wait5sec();
    }

    @Step("Переходим на экран Сейвкоины")
    static void clickSavecoinButton() {
        wfeClick(By.xpath(getElementLocator("Профиль - поле Сейвкоины")),
                "Не могу найти поле 'Сейвкоины'", 4);
        wait1sec();
    }

    @Step("Сверяем количество сейвкоинов клиента со значением в БД")
    static void assertSavecoins(Client client) {
        Assert.assertEquals("" + (int)client.getSavecoin(), driver.findElementByXPath(
                getElementLocator("Сейвкоины - поле с количеством сейвкоинов")).getText());
        System.out.println("Количество сейвкоинов клиента соответствует значению в БД");
    }

    @Step("Запоминаем количество сейвкоинов")
    static String rememberSavecoinAmount() {
        byte[] screenshot = Main.saveAllureScreenshot();
        System.out.println(screenshot.length);
        return driver.findElementByXPath(getElementLocator("Сейвкоины - поле с количеством сейвкоинов")).getText();
    }

    @Step("Переходим назад к списку брендов")
    static void clickBackButton() {
        wfeClick(By.xpath(getElementLocator("Кнопка НАЗАД")),"Не могу найти кнопку НАЗАД", 14);
        wait1sec();
    }

    @Step("Кликаем на поле с адресом доставки")
    static void clickDeliveryAddressField() {
        wfeClick(By.xpath(getElementLocator("Список брендов - поле с адресом")),
                "Не могу найти поле с адресом", 14);
        wait3sec();
    }

    @Step("Кликаем по кнопке \"Изменить адрес\"")
    static void clickChangeDeliveryAddressButton() {
        wfeClick(By.xpath(getElementLocator("Список брендов - поле с адресом - Изменить адрес")),
                "Не могу найти кнопку 'Изменить адрес'", 14);
        wait3sec();
    }

    @Step("Избавляемся от поп-апа 'Новая функция \"Повторить заказ\"'")
    static void closePopupRepeatOrder(String deviceName) {
        driver.navigate().refresh();
        wait5sec();
        if (deviceName.equals("32010f78a810444a")) { wait5sec(); }
        if (driver.findElementsByXPath("//h2[contains(text(), 'функция')]").size() != 0) {
            wfeClick(By.xpath(getElementLocator("Попап 'Повторить заказ' - кнопка 'Понятно'")),
                    "Не могу найти кнопку 'Понятно'", 14);
            wait3sec();
        }
        if (driver.findElementsByXPath(getElementLocator("Окно оценки заказа - Х звезд")).size() != 0) {
            selectRating("4");
            clickSendRatingButton();
            wait3sec();
        }
    }
/*
    @Step("Кликаем по крестику, чтобы закрыть информацию о Любимых Товарах")
    static void clickFavoriteGoodsCloseButton() {
        wfeClick(By.xpath(getElementLocator("Подсказка о любимых товарах - крестик")),
                "Не могу найти крестик в подсказке", 20);
        wait1sec();
    }
*/
    @Step("Кликаем по значку Корзины")
    static void clickCartButton() {
        wfeClick(By.xpath(getElementLocator("Подкатегория - кнопка корзины")),
                "Не могу найти Корзину", 15);
        wait3sec();
    }

    @Step("Кликаем по кнопке Оформить заказ")
    static void clickCheckoutButton() {
        wfeClick(By.xpath(getElementLocator("Корзина - кнопка ОФОРМИТЬ ЗАКАЗ")),
                "Не могу найти кнопку ОФОРМИТЬ ЗАКАЗ", 15);
        wait1sec();
    }

    @Step("Заполняем поля Подъезд, Этаж, Кв./Офис, Код домофона")
    static void fillPorchFloorFlatIntercom(String porch, String floor, String flat, String intercom) {
        wfeSendKeys(By.xpath(getElementLocator("Чекаут - экран Доставка - поле ввода подъезда")), porch,
                "Не могу найти поле Подъезд", 15);
        wfeSendKeys(By.xpath(getElementLocator("Чекаут - экран Доставка - поле ввода этажа")), floor,
                "Не могу найти поле Этаж", 15);
        wfeSendKeys(By.xpath(getElementLocator("Чекаут - экран Доставка - поле ввода квартиры/офиса")), flat,
                "Не могу найти поле Кв./Офис", 15);
        wfeClearSendKeys(By.xpath(getElementLocator("Чекаут - экран Доставка - поле ввода кода домофона")),
                intercom, "Не могу найти поле Код домофона", 15);
    }

    @Step("Кликаем по кнопке Далее на экране Доставка")
    static void clickNextButtonOnDeliveryScreen() {
        wfeClick(By.xpath(getElementLocator("Чекаут - экран Доставка - кнопка ДАЛЕЕ")),
                "Не могу найти кнопку ДАЛЕЕ на экране Доставка", 15);
        wait3sec();
    }

    @Step("Кликаем по кнопке Далее на экране Время доставки")
    static void clickNextButtonOnDeliveryTimeScreen() {
        wfeClick(By.xpath(getElementLocator("Чекаут - экран Время доставки - кнопка ДАЛЕЕ")),
                "Не могу найти кнопку ДАЛЕЕ на экране Время доставки", 15);
        wait3sec();
    }

    @Step("Вводим комментарий клиента")
    static void fillCommentField(String comment) {
        wfeSendKeys(By.xpath(getElementLocator("Чекаут - поле ввода комментария")), comment,
                "Не могу найти поле ввода комментария", 15);
    }

    @Step("Нажимаем кнопку ОПЛАТИТЬ")
    static void clickPayButton() {
        wfeClick(By.xpath(getElementLocator("Чекаут - кнопка ОПЛАТИТЬ")),
                "Не могу найти кнопку ОПЛАТИТЬ", 19);
    }

    @Step("Проверяем, что статус заказа \"Ваш заказ принят\"")
    static void assertOrderStatusHold() {
        Assert.assertEquals("Ваш заказ принят", driver.findElementByXPath(getElementLocator("Окно заказа - " +
                "поле со статусом заказа")).getText());
        wait1sec();
    }

    @Step("Проверяем, что статус заказа \"Ваш заказ собирается\"")
    static void assertOrderStatusPacking() {
        Assert.assertEquals("Ваш заказ собирается", driver.findElementByXPath(getElementLocator("Окно " +
                "заказа - поле со статусом заказа")).getText());
        wait1sec();
    }

    @Step("Проверяем, что статус заказа \"Ваш заказ собран\"")
    static void assertOrderStatusCompiled() {
        Assert.assertEquals("Ваш заказ собран", driver.findElementByXPath(getElementLocator("Окно заказа" +
                " - поле со статусом заказа")).getText());
        wait1sec();
    }

    @Step("Проверяем, что статус заказа \"Ваш заказ в пути\"")
    static void assertOrderStatusShip() {
        Assert.assertEquals("Ваш заказ в пути", driver.findElementByXPath(getElementLocator("Окно заказа" +
                " - поле со статусом заказа")).getText());
        wait1sec();
    }

    @Step("Проверяем, что статус заказа \"Заказ доставлен\"")
    static void assertOrderStatusDone() {
        Assert.assertEquals("Заказ доставлен", driver.findElementByXPath(getElementLocator("Окно заказа" +
                " - поле со статусом заказа")).getText());
        wait1sec();
    }

    @Step("Проверяем, что статус заказа \"Заказ отменен\"")
    static void assertOrderStatusCancel() {
        Assert.assertEquals("Заказ отменен", driver.findElementByXPath(getElementLocator("Окно заказа" +
                " - поле со статусом заказа")).getText());
        wait1sec();
    }

    @Step("Выставляем оценку заказа")
    static void selectRating(String rating) {
        wfeClick(By.xpath(getElementLocator("Окно оценки заказа - Х звезд") + "[" + rating + "]"),
                "Не могу найти кнопку " + rating + " звезды на экране оценки заказа", 15);
        wait1sec();
    }

    @Step("Жмем кнопку \"ОТПРАВИТЬ\", чтобы отправить оценку")
    static void clickSendRatingButton() {
        wfeClick(By.xpath(getElementLocator("Окно оценки заказа - кнопка ОТПРАВИТЬ")),
                "Не могу найти кнопку ОТПРАВИТЬ на экране оценки заказа", 15);
        wait5sec();
    }

    @Step("Переходим на экран \"Заказы\"")
    static void clickOrdersButton() {
        wfeClick(By.xpath(getElementLocator("Профиль - поле Мои заказы")),
                "Не могу найти кнопку Мои заказы",15);
        wait1sec();
    }

    @Step("Кликаем по кнопке \"Активировать промокод\"")
    static void clickPromocodeButton() {
        wfeClick(By.xpath(getElementLocator("Чекаут - кнопка Активировать промокод")),
                "Не могу найти кнопку \"Активировать промокод\"",15);
        wait3sec();
    }

    @Step("Вводим промокод и нажимаем ОК")
    static void enterPromocode(String promocode, String deviceName) {
        switchContext("NATIVE_APP");
        if (deviceName.equals("32010f78a810444a")) {
            wfeClearSendKeys(By.xpath(getElementLocator("Ввод промокода - поле ввода промокода - Samsung J1")),
                    promocode, "Не могу найти поле ввода промокода", 5);
            wait1sec();
            wfeClick(By.xpath(getElementLocator("Ввод промокода - поле ввода промокода - Samsung J1")),
                    "Не могу найти поле ввода промокода", 5);
        }
        else {
            wfeClearSendKeys(By.xpath(getElementLocator("Ввод промокода - поле ввода промокода")), promocode,
                    "Не могу найти поле ввода промокода", 5);
            wait1sec();
            wfeClick(By.xpath(getElementLocator("Ввод промокода - поле ввода промокода")),
                    "Не могу найти поле ввода промокода", 5);
        }
        wait1sec();
        wfeClick(By.id("android:id/button2"), "Не могу найти кнопку ОК", 10);
        wait3sec();
        switchContext("WEBVIEW");
    }

    @Step("Проверяем наличие алерта \"Промокод не может быть применен...\" и кликаем ОК")
    static void assertLowCartPromocodeAlertAndClickOK() {
        switchContext("NATIVE_APP");
        wfePresent(By.id("android:id/message"), "Не появляется текст алерта", 10);
        Assert.assertEquals("Промокод не может быть использован, так как сумма заказа меньше 500 руб.",
                driver.findElementById("android:id/message").getText());
        wait1sec();
        wfeClick(By.id("android:id/button1"), "Не могу найти Кнопку ОК", 5);
        wait1sec();
        switchContext("WEBVIEW");
    }

    @Step("Проверяем наличие алерта \"Промокод активирован\" и кликаем ОК")
    static void assertValidCartPromocodeAlertAndClickOK() {
        switchContext("NATIVE_APP");
        wfePresent(By.id("android:id/message"), "Не появляется текст алерта", 10);
        Assert.assertEquals("Промокод активирован",
                driver.findElementById("android:id/message").getText());
        wait1sec();
        wfeClick(By.id("android:id/button1"), "Не могу найти Кнопку ОК", 5);
        wait3sec();
        switchContext("WEBVIEW");
    }

    @Step("Проверяем корректность отображения введенного промокода")
    static void assertValidPromocodeDisplay(String promocode) {
        wfePresent(By.xpath(getElementLocator("Чекаут - поле Промокод")), "Не вижу поле с промокодом",
                10);
        Assert.assertEquals("Промокод «" + promocode + "»",
                driver.findElementByXPath(getElementLocator("Чекаут - поле Промокод")).getText());
        wait1sec();
    }

    @Step("Проверяем корректность отображения скидки по промокоду")
    static void assertValidPromocodeDiscountDisplay(float discount, String discountType) {
        wfePresent(By.xpath(getElementLocator("Чекаут - поле Промокод - скидка")),
                "Не вижу поле со скидкой по промокоду", 10);
        if (discountType.equals("percent")) Assert.assertEquals("- " + (int)discount + " %",
                driver.findElementByXPath(getElementLocator("Чекаут - поле Промокод - скидка")).getText());
        else Assert.assertEquals("- " + (int)discount + " \u20BD",
                driver.findElementByXPath(getElementLocator("Чекаут - поле Промокод - скидка")).getText());
        wait1sec();
    }

    @Step("Запоминаем значение суммы к оплате")
    static double paymentAmount() {
        wfePresent(By.xpath(getElementLocator("Чекаут - поле Всего к оплате - сумма")),
                "Не могу найти поле с суммой к оплате", 10);
        String sum = driver.findElementByXPath(getElementLocator("Чекаут - поле Всего к оплате - сумма")).getText();
        double payment = 0.0;
        try {
            payment += Double.parseDouble(sum.substring(0, sum.indexOf(",")));
            payment += (double) Integer.parseInt(sum.substring(sum.indexOf(",") + 1, sum.indexOf(" "))) / 100;
        } catch (StringIndexOutOfBoundsException e) {
            payment += Double.parseDouble(sum.substring(0, sum.indexOf(" ")));
        }
        return payment;
    }

    @Step("Проверяем, что сумма \"Всего к оплате\" уменьшилась на сумму скидки")
    static void assertPaymentAmountIsReduced(double before, double after, float discount, String discountType) {
        if (discountType.equals("percent")) {
            wfePresent(By.xpath(getElementLocator("Чекаут - поле Сумма заказа - сумма")),
                    "Не могу найти поле с суммой заказа", 10);
            String sums = driver.findElementByXPath(getElementLocator("Чекаут - поле Сумма заказа - сумма")).getText();
            double sum = 0.0;
            try {
                sum += Double.parseDouble(sums.substring(0, sums.indexOf(",")));
                sum += (double) Integer.parseInt(sums.substring(sums.indexOf(",") + 1, sums.indexOf(" "))) / 100;
            } catch (StringIndexOutOfBoundsException e) {
                sum += Double.parseDouble(sums.substring(0, sums.indexOf(" ")));
            }
            double discountPercent = discount * 0.01 * sum;
            Assert.assertEquals(before - discountPercent, after, 0.001);
        }
        else Assert.assertEquals(before - (double)discount, after, 0.001);
    }

    @Step("Возвращаемся в список категорий из чекаута")
    static void backToCategoryListFromCheckout() {
        driver.navigate().back();
        driver.navigate().refresh();
        wait5sec();
    }

    @Step("Перетаскиваем ползунок с сейвкоинами в нужную точку, чтобы списывалось указанное количество")
    static void dragAndDropSavecoinSlider(int amount, String deviceName) {
        wait1sec();
        wfeClick(By.xpath("//div[@class='block-title' and contains(text(), 'Способ оплаты')]"),
                "Не могу найти поле с заголовком Способ оплаты", 10);
        wait5sec();
        WebElement scTotal = driver.findElementByXPath("//li[@class='coin-list-item']/div/div[2]/div[3]");
        int savecoinTotal = (int)Double.parseDouble(scTotal.getText());
        switchContext("NATIVE_APP");
        TouchAction touch = new TouchAction(driver);
        if (deviceName.equals("9WV4C18813005662")) {
            int offsetX = 722 * amount / savecoinTotal;
            touch.tap(147 + offsetX,1806).perform();
        }
        if (deviceName.equals("32010f78a810444a")) {
            int offsetX = 314 * amount / savecoinTotal;
            touch.tap(73 + offsetX,625).perform();
        }
        if (deviceName.equals("2fcaae347d74")) {
            int offsetX = 499 * amount / savecoinTotal;
            touch.tap(98 + offsetX,1036).perform();
        }
        if (deviceName.equals("e4986fbc")) {
            int offsetX = 778 * amount / savecoinTotal;
            touch.tap(134 + offsetX,1716).perform();
        }
        switchContext("WEBVIEW");
        wait1sec();
    }

    @Step("Проверяем, что сумма \"Всего к оплате\" уменьшилась на количество сейвкоинов к списанию")
    static void assertPaymentAmountIsReducedBySavecoins(double before, double after, int savecoinsToSubstract) {
        Assert.assertEquals(before - (double)savecoinsToSubstract, after, 0.001);
    }

    @Step("Проверяем, что количество сейвкоинов уменьшилось на количество сейвкоинов к списанию")
    static void assertSavecoinsIsReduced(int savecoinsToSubstract, Client client) {
        int before = (int) client.getSavecoin();
        int after = Integer.parseInt(driver.findElementByXPath(
                getElementLocator("Сейвкоины - поле с количеством сейвкоинов")).getText());
        Assert.assertEquals(before - savecoinsToSubstract, after);
    }

    @Step("Проверяем, что начислилось корректное количество сейвкоинов")
    static void assertSavecoinsAccured(Order order, Client client, String before, String after) {
        double beforeD = client.getSavecoin();
        double afterD = beforeD + Math.round(order.getSum()) / 100.0;
        Assert.assertEquals("" + (int)afterD, after);
        System.out.println("Было сейвкоинов: " + before);
        System.out.println("Стало сейвкоинов: " + after);
    }

    @Step("Запоминаем состав корзины")
    static HashMap<String, String> rememberCart() {
        // Вычисляем длину списка
        byte[] screenshot = Main.saveAllureScreenshot();
        System.out.println(screenshot.length);
        int lenght = driver.findElementsByXPath("//ul[@class='cart-products']/li").size();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 1; i < lenght; i++) {
            map.put(driver.findElementByXPath("(//ul[@class='cart-products']/li/div[2]/div[@class='title'])[" +
                    i + "]").getText(), driver.findElementByXPath("(//ul[@class='cart-products']/li/div[2]/div" +
                    "[@class='cart-qty'])[" + i + "]").getText());
        }
        return map;
    }

    @Step("Жмем кнопку \"Повторить заказ\"")
    static void clickRepeatOrder() {
        wfeClick(By.xpath("//a[contains(text(), 'Повторить заказ')]"),
                "Не могу найти кнопку \"Повторить заказ\"",15);
        wait3sec();
    }

    @Step("Жмем кнопку на алерте \"Перейти в корзину\"")
    static void clickGoToCartAlert() {
        wfeClick(By.xpath("//button[contains(text(), 'Перейти')]"),
                "Не могу найти кнопку 'перейти в корзину'",7);
        wait5sec();
    }

    @Step("Жмем кнопку ДА на алерте \"Отменить заказ?\"")
    static void clickYesCancelOrderAlert() {
        switchContext("NATIVE_APP");
        wfeClick(By.id("android:id/button2"), "Не могу найти кнопку 'ДА'",7);
        switchContext("WEBVIEW");
        wait5sec();
    }

    @Step("Сравниваем корзину первого заказа и второго")
    static void assertCarts(HashMap<String, String> first, HashMap<String, String> second) {
        Assert.assertEquals(first, second);
        wait1sec();
    }

    @Step("Нажимаем кнопку \"Отменить\"")
    static void clickCancelOrder() {
        wfeClick(By.xpath("//a[contains(text(), 'Отменить')]"),
                "Не могу найти кнопку \"Отменить\"",15);
        wait3sec();
    }

    @Step("Проверяем наличие алерта \"Если вам нравится приложение, оцените его!\"")
    static void assertRatingAlert() {
        switchContext("NATIVE_APP");
        Assert.assertEquals("Если вам нравится приложение, оцените его!",
                driver.findElementById("android:id/message").getText());
        switchContext("WEBVIEW");
        wait1sec();
    }

    @Step("Жмем кнопку \"ОЦЕНИТЬ\" на алерте")
    static void clickEstimateButton() {
        switchContext("NATIVE_APP");
        wfeClick(By.id("android:id/button1"), "Не могу найти кнопку \"ОЦЕНИТЬ\"",15);
        switchContext("WEBVIEW");
        wait3sec();
    }

    @Step("Убеждаемся, что мы перешли в Google Play Market на страницу приложения")
    static void assertGooglePlayMarket() {
        switchContext("NATIVE_APP");
        Assert.assertEquals("SaveTime доставка продуктов и товаров из магазинов",
                driver.findElementById("com.android.vending:id/title_title").getText());
        wait1sec();
    }

    @Step("Если у пользователя есть активные заказы - переводим их в DONE") // ДОРАБОТАТЬ, НУЖЕН РЕФРЕШ
    static void setAllActiveOrdersDone(Client client, String deviceName) {
        if (driver.findElements(By.xpath("//span[@class='badge color-red']")).size() > 0) {
            int activeOrders = Integer.parseInt(driver.findElement(By.xpath("//span[@class='badge color-red']"))
                    .getText());
            wfeClick(By.xpath(getElementLocator("Профиль - поле Мои заказы")),
                    "Не могу найти кнопку 'Мои заказы'", 5);
            wait3sec();
            for (int i = 0; i < activeOrders; i++) {
                wfeClick(By.xpath("(//div[contains(text(), 'Ваш заказ')])[1]"),
                        "Не могу найти кнопку с активным заказом", 7);
                wait1sec();
                Order order = STsql.getOrderData(Integer.parseInt(driver.findElement(By
                        .xpath(getElementLocator("Окно заказа - поле с номером заказа"))).getText().substring(7)));
                wait1sec();
                STsql.setOrderStatus("done", order, client);
                wait1sec();
                closePopupRepeatOrder(deviceName);
                if (driver.findElementsByXPath(getElementLocator("Окно оценки заказа - Х звезд")).size() != 0) {
                    selectRating("4");
                    clickSendRatingButton();
                    wait1sec();
                }
                if (driver.findElementsByXPath(getElementLocator("Окно заказа - поле с номером заказа")).size() != 0) {
                    switchContext("NATIVE_APP");
                    TouchAction touch = new TouchAction(driver);
                    touch.tap(10,92).perform();
                    switchContext("WEBVIEW");
                    wait1sec();
                }
                clickBurgerMenu();
                if (i < activeOrders - 1) wfeClick(By.xpath(getElementLocator("Профиль - поле Мои заказы")),
                        "Не могу найти кнопку 'Мои заказы'", 5);
                wait3sec();
            }

        }
        else System.out.println("Активных заказов нет!");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveAllureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}