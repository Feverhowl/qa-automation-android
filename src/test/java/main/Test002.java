// Тест №002
// End to End тест - от авторизации до завершения заказа.

package main;

import org.openqa.selenium.By;

public class Test002 extends Main {

    public static final String NAME = "Тест №002 - End to End - От авторизации до завершения заказа";

    public static void test002(String deviceName) {

        // Выбираем случайного клиента с сейвкоинами
        Client client = STsql.pickRandomClientWithSavecoins();
        Client.printAllInfo(client);

        // Ждем пока прогрузится приложение
        waitForAppIsLoaded(deviceName);

        // Кликаем на кнопку "ПРИСТУПИТЬ К ПОКУПКАМ"
        clickStartShoppingButton();

        // Кликаем по Бургер-меню
        clickBurgerMenu();

        // Вводим номер телефона
        enterPhone(deviceName, client);

        // Жмем кнопку "ПРОДОЛЖИТЬ"
        clickContinueButton();

        // Вводим код из смс
        enterSmsCode();

        // Если у пользователя есть активные заказы - переводим их в DONE
        setAllActiveOrdersDone(client, deviceName);

        // Переходим назад к списку брендов
        clickBackButton();

        // Избавляемся от поп-апа 'Новая функция "Повторить заказ"'
        closePopupRepeatOrder(deviceName);

        // Кликаем на поле с адресом доставки
        clickDeliveryAddressField();

        // Кликаем по кнопке "Изменить адрес"
        clickChangeDeliveryAddressButton();

        // Смена адреса клиента на случайный
        chooseRandomAddress();

        // Кликаем на случайный бренд
        clickRandomBrand();

        // Кликаем на случайную категорию
        clickRandomCategory();

        // Кликаем на случайную подкатегорию
        clickRandomSubCategory();

        // Накидываем товары в корзину
        addGoodsOnCart(5);

        // Кликаем по значку Корзины
        clickCartButton();

        // Кликаем по кнопке Оформить заказ
        clickCheckoutButton();

        // Заполняем поля Подъезд, Этаж, Кв./Офис, Код домофона
        fillPorchFloorFlatIntercom("1", "2", "3", "123");

        // Кликаем по кнопке Далее на экране Доставка
        clickNextButtonOnDeliveryScreen();

        // Кликаем по кнопке Далее на экране Время доставки
        clickNextButtonOnDeliveryTimeScreen();

        // Вводим комментарий клиента
        fillCommentField("Yo, yo yo, 148-3 to the 3 to the 6 to the 9, representing the ABQ, what up biatch?!");

        // Добавляем тестовую карту оплаты, если она ещё не привязана
        addCard(deviceName);

        // Нажимаем кнопку ОПЛАТИТЬ
        clickPayButton();

        // Нажимаем кнопку ОК на алерте "Новый заказ"
        clickOkOnAlertNewOrder(deviceName);

        // Подтаскиваем всю инфу о созданном заказе
        Order order = STsql.getOrderData(Integer.parseInt(driver.findElement(By.xpath(getElementLocator("Окно заказа" +
                " - поле с номером заказа"))).getText().substring(7)));
        Order.printAllInfo(order);

        // Проверяем, что статус заказа "Ваш заказ принят"
        assertOrderStatusHold();

        // Меняем статус на "Собирается" и рефрешим страницу заказа
        STsql.setOrderStatus("packing", order, client);
        driver.navigate().refresh();
        wait10sec();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Проверяем, что статус заказа изменился на "Ваш заказ собирается"
        assertOrderStatusPacking();

        // Меняем статус на "Собран" и рефрешим страницу заказа
        STsql.setOrderStatus("compiled", order, client);
        driver.navigate().refresh();
        wait10sec();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Проверяем, что статус заказа изменился на "Ваш заказ собран"
        assertOrderStatusCompiled();

        // Меняем статус на "В пути" и рефрешим страницу заказа
        STsql.setOrderStatus("ship", order, client);
        driver.navigate().refresh();
        wait10sec();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Проверяем, что статус заказа изменился на "Ваш заказ в пути"
        assertOrderStatusShip();

        // Меняем статус на "Доставлен" и рефрешим страницу
        STsql.setOrderStatus("done", order, client);
        driver.navigate().refresh();
        wait5sec();

        // Проставляем заказу оценку 4 звезды
        selectRating("4");

        // Жмем кнопку "ОТПРАВИТЬ", чтобы отправить оценку
        clickSendRatingButton();

        // Кликаем по Бургер-меню
        clickBurgerMenu();

        // Переходим на экран "Заказы"
        clickOrdersButton();

        // Ищем заказ в списке заказов и кликаем на него
        clickLastOrder();

        // Проверяем, что статус заказа изменился на "Заказ доставлен"
        assertOrderStatusDone();
    }
}